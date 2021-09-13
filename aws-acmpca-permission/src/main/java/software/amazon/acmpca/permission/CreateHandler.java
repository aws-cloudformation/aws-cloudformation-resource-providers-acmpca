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

public final class CreateHandler extends BaseHandler<CallbackContext> {

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

        log.log("Create handler being invoked for Certificate Authority Arn: " + model.getCertificateAuthorityArn() + " and for Principal: " + model.getPrincipal());

        try {
            acmPcaClient.getPermission(model);

            val alreadyExistsException = AwsServiceException.builder()
                .awsErrorDetails(AwsErrorDetails.builder()
                        .errorCode("AlreadyExists")
                        .errorMessage("PermissionAlreadyExists")
                        .build())
                .build();

            return ProgressEvent.defaultFailureHandler(alreadyExistsException, HandlerErrorCode.AlreadyExists);
        } catch (NoSuchElementException ex) {
            acmPcaClient.createPermission(model);
            return ProgressEvent.defaultSuccessHandler(model);
        }
    }
}
