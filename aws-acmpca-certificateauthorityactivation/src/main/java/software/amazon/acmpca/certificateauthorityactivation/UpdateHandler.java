package software.amazon.acmpca.certificateauthorityactivation;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

public final class UpdateHandler extends BaseHandler<CallbackContext> {

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

        updateCertificateAuthorityChain(model);
        acmPcaClient.setCompleteCertificateChain(model);

        return ProgressEvent.defaultSuccessHandler(model);
    }

    private void updateCertificateAuthorityChain(final ResourceModel model) {
        val certificateAuthorityArn = model.getCertificateAuthorityArn();

        log.log("Updating certificate and certificate chain for Certificate Authority arn: " + certificateAuthorityArn);
        acmPcaClient.importCertificateAuthorityCertificate(model);
        acmPcaClient.updateCertificateAuthorityStatus(model);
    }
}
