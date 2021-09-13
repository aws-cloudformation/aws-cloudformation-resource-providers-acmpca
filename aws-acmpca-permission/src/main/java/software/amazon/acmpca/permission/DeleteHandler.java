package software.amazon.acmpca.permission;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.HandlerErrorCode;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import com.amazonaws.services.acmpca.model.ResourceNotFoundException;

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

        log.log("Delete handler being invoked for Certificate Authority Arn: " + model.getCertificateAuthorityArn() + " and for Principal: " + model.getPrincipal());

        try {
            acmPcaClient.deletePermission(model);
        } catch (ResourceNotFoundException ex) {
            log.log("Permission was likely already deleted. " + ex);
            return ProgressEvent.defaultFailureHandler(ex, HandlerErrorCode.NotFound);
        }

        return ProgressEvent.defaultSuccessHandler(null);
    }
}
