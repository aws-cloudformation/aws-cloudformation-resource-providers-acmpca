package software.amazon.acmpca.certificate;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

public final class ReadHandler extends BaseHandler<CallbackContext> {

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

        log.log("Read handler being invoked for Certificate Authority Arn: " + model.getCertificateAuthorityArn());
        log.log("Read handler being invoked for Certificate Arn: " + model.getArn());

        readCertificate(model);
        model.setCertificateSigningRequest(null);

        return ProgressEvent.defaultSuccessHandler(model);
    }

    private void readCertificate(final ResourceModel model) {
        val getCertificateResult = acmPcaClient.getCertificate(model);
        val certificate = getCertificateResult.getCertificate();
        model.setCertificate(certificate);
    }
}
