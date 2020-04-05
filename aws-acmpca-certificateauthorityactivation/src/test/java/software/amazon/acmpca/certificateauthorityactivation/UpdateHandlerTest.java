package software.amazon.acmpca.certificateauthorityactivation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class UpdateHandlerTest extends TestBase {

    @Test
    public void testHandleRequest__Success() {
        val handler = new UpdateHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificate(certificate)
            .certificateChain(certificateChain)
            .status(CertificateAuthorityStatus.DISABLED.name())
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel().getStatus()).isEqualTo(CertificateAuthorityStatus.DISABLED.name());
        assertThat(response.getResourceModel().getCertificateAuthorityArn()).isEqualTo(certificateAuthorityArn);
        assertThat(response.getResourceModel().getCertificateChain()).isNull();
        assertThat(response.getResourceModel().getCertificate()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }
}
