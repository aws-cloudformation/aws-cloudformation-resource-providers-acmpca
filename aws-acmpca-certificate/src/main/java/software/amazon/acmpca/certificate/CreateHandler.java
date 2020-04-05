package software.amazon.acmpca.certificate;

import java.util.Optional;

import com.amazonaws.services.acmpca.model.RequestInProgressException;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
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
            .map(__ -> getCreatedCertificate(model, callbackContext))
            .orElseGet(() -> createCertificate(model, callbackContext));
    }

    private ProgressEvent<ResourceModel, CallbackContext> createCertificate(final ResourceModel model, final CallbackContext context) {
        log.log("Calling IssueCertificate()");

        val certificateArn = acmPcaClient.issueCertificate(model);
        model.setArn(certificateArn);
        model.setCertificateSigningRequest(null);

        log.log("Certificate Authority Arn received: " + certificateArn);

        return getCreatedCertificate(model, context);
    }

    private ProgressEvent<ResourceModel, CallbackContext> getCreatedCertificate(final ResourceModel model, final CallbackContext context) {
        try {
            val getCertificateResult = acmPcaClient.getCertificate(model);
            val certificate = getCertificateResult.getCertificate();
            model.setCertificate(certificate);
        } catch (RequestInProgressException e) {
            return ProgressEvent.defaultInProgressHandler(context, SECONDS_TO_NEXT_INVOCATION, model);
        }

        return ProgressEvent.defaultSuccessHandler(model);
    }
}
