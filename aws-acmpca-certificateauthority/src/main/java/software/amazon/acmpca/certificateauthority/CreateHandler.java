package software.amazon.acmpca.certificateauthority;

import java.util.Objects;
import java.util.Optional;

import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.HandlerErrorCode;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

public final class CreateHandler extends BaseHandler<CallbackContext> {

    private static final int SECONDS_TO_NEXT_INVOCATION = 1;

    private AcmPcaClient acmPcaClient;
    private Logger log;

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final Logger logger) {

        this.acmPcaClient = new AcmPcaClient(proxy);
        this.log = logger;

        val model = request.getDesiredResourceState();

        log.log("Create handler being invoked for Arn: " + model.getArn());

        return Optional.ofNullable(model.getArn())
            .map(__ -> getCreatedCertificateAuthority(model, callbackContext))
            .orElseGet(() -> createCertificateAuthority(model, callbackContext));
    }

    private ProgressEvent<ResourceModel, CallbackContext> createCertificateAuthority(
        final ResourceModel model,
        final CallbackContext context) {

        log.log("Calling CreateCertificateAuthority()");

        val certificateAuthorityArn = acmPcaClient.createCertificateAuthority(model);
        model.setArn(certificateAuthorityArn);

        log.log("Certificate Authority Arn received: " + certificateAuthorityArn);

        return getCreatedCertificateAuthority(model, context);
    }

    private ProgressEvent<ResourceModel, CallbackContext> getCreatedCertificateAuthority(
        final ResourceModel model,
        final CallbackContext context) {

        val certificateAuthorityStatus = acmPcaClient.describeCertificateAuthority(model).getStatus();

        if (Objects.equals(certificateAuthorityStatus, CertificateAuthorityStatus.CREATING.name())) {
            return ProgressEvent.defaultInProgressHandler(context, SECONDS_TO_NEXT_INVOCATION, model);
        }

        if (Objects.equals(certificateAuthorityStatus, CertificateAuthorityStatus.FAILED.name())) {
            return ProgressEvent.defaultFailureHandler(
                new InternalError("Certificate Authority " + model.getArn() + " creation failed. Please try again."),
                HandlerErrorCode.ServiceInternalError);
        }

        val csr = acmPcaClient.getCertificateAuthorityCsr(model);
        model.setCertificateSigningRequest(csr);

        return ProgressEvent.defaultSuccessHandler(model);
    }
}
