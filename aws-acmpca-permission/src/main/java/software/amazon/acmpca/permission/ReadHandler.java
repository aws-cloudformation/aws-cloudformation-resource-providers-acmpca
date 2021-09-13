package software.amazon.acmpca.permission;

import java.util.NoSuchElementException;

import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.HandlerErrorCode;
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

        log.log("Read handler being invoked for Certificate Authority Arn: " + model.getCertificateAuthorityArn() + " and for Principal: " + model.getPrincipal());

        try {
            val permission = acmPcaClient.getPermission(model);
            model.setActions(permission.getActions());
            model.setSourceAccount(permission.getSourceAccount());

            return ProgressEvent.defaultSuccessHandler(model);
        } catch (NoSuchElementException ex) {
            val emptyResponseException = AwsServiceException.builder()
                .awsErrorDetails(AwsErrorDetails.builder()
                        .errorCode("NotFound")
                        .errorMessage("Not Found")
                        .build())
                .build();

            return ProgressEvent.defaultFailureHandler(emptyResponseException, HandlerErrorCode.NotFound);
        }
    }
}
