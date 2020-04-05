package software.amazon.acmpca.certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.GetCertificateRequest;
import com.amazonaws.services.acmpca.model.GetCertificateResult;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class ReadHandlerTest extends TestBase {

    @Test
    public void testHandleRequest__SuccessfulRead() {
        val handler = new ReadHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .templateArn(defaultTemplateArn)
            .validity(validity)
            .certificate(certificate)
            .arn(certificateArn)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(new GetCertificateResult().withCertificate(certificate)
            .withCertificateChain(certificateChain))
            .when(proxy).injectCredentialsAndInvoke(any(GetCertificateRequest.class), any());

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();

        assertThat(response.getResourceModel().getCertificate().equals(certificate));
        assertThat(response.getResourceModel().getArn().equals(certificateArn));
    }

    @Test
    public void testHandleRequest__ReadingUpdatedCertificateChain() {
        val handler = new ReadHandler();
        val newChain = "new-chain";

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .templateArn(defaultTemplateArn).validity(validity)
            .certificate(certificate)
            .arn(certificateArn)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(new GetCertificateResult().withCertificate(certificate).withCertificateChain(newChain))
            .when(proxy).injectCredentialsAndInvoke(any(GetCertificateRequest.class), any());

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();

        assertThat(response.getResourceModel().getCertificate().equals(certificate));
        assertThat(response.getResourceModel().getArn().equals(certificateArn));
    }
}
