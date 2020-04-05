package software.amazon.acmpca.certificateauthority;

import com.amazonaws.services.acmpca.model.ResourceNotFoundException;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

public final class DeleteHandler extends BaseHandler<CallbackContext> {

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

        log.log("Delete handler being invoked for Arn: " + model.getArn());

        try {
            acmPcaClient.deleteCertificateAuthority(model);
        } catch (ResourceNotFoundException ex) {
            log.log("Certificate authority was likely already deleted. Ignoring: " + ex);
        }

        return ProgressEvent.defaultSuccessHandler(null);
    }
}
