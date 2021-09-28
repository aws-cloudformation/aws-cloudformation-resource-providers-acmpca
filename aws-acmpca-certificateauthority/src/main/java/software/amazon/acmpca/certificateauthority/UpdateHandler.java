package software.amazon.acmpca.certificateauthority;

import java.util.HashSet;

import com.amazonaws.services.acmpca.model.Tag;

import com.amazonaws.services.acmpca.model.InvalidStateException;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import software.amazon.cloudformation.proxy.HandlerErrorCode;

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

        log.log("Update handler being invoked for Arn: " + model.getArn());

        try {
            acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
            updateTags(model);
            return ProgressEvent.defaultSuccessHandler(model);
        } catch (InvalidStateException e) {
            return ProgressEvent.defaultFailureHandler(e, HandlerErrorCode.NotFound);
        }
    }

    private void updateTags(final ResourceModel model) {
        val previous = new HashSet<Tag>(acmPcaClient.listCertificateAuthorityTags(model));
        val current = acmPcaClient.getTags(model);

        acmPcaClient.tagCertificateAuthority(model, current);
        previous.removeAll(current);
        acmPcaClient.untagCertificateAuthority(model, previous);
    }
}
