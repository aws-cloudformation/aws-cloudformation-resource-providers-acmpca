package software.amazon.acmpca.certificateauthority;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.stream.Collectors;

import lombok.val;

public final class ListHandler extends BaseHandler<CallbackContext> {

    private AcmPcaClient acmPcaClient;

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final Logger logger) {

        this.acmPcaClient = new AcmPcaClient(proxy);

        val models = acmPcaClient.listCertificateAuthorities().stream()
            .map(certificateAuthority -> ResourceModel.builder()
                .arn(certificateAuthority.getArn())
                .build())
            .map(model -> acmPcaClient.populateResourceModel(model))
            .collect(Collectors.toList());

        return ProgressEvent.<ResourceModel, CallbackContext>builder()
            .resourceModels(models)
            .status(OperationStatus.SUCCESS)
            .build();
    }
}
