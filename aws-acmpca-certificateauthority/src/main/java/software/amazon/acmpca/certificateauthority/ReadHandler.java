package software.amazon.acmpca.certificateauthority;

import java.util.Objects;

import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;

import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import software.amazon.cloudformation.proxy.HandlerErrorCode;

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

        log.log("Read handler being invoked for Arn: " + model.getArn());

        val certificateAuthority = acmPcaClient.describeCertificateAuthority(model);
        val status = certificateAuthority.getStatus();
        if (Objects.equals(status, CertificateAuthorityStatus.DELETED.name())) {
            val emptyResponseException = AwsServiceException.builder()
                .awsErrorDetails(AwsErrorDetails.builder()
                    .errorCode("NotFound")
                    .errorMessage("Not Found")
                    .build())
                .build();
            return ProgressEvent.defaultFailureHandler(emptyResponseException, HandlerErrorCode.NotFound);
        }
        val populateResourceModel = acmPcaClient.populateResourceModel(model);

        return ProgressEvent.defaultSuccessHandler(populateResourceModel);
    }
}
