package software.amazon.acmpca.certificateauthorityactivation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;
import com.amazonaws.services.acmpca.model.InvalidStateException;
import com.amazonaws.services.acmpca.model.UpdateCertificateAuthorityRequest;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class DeleteHandlerTest extends TestBase {

    @Test
    public void testHandleRequest__Success() {
        val handler = new DeleteHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificate(certificate)
            .certificateChain(certificateChain)
            .status(CertificateAuthorityStatus.ACTIVE.name())
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHandleRequest__Success__InvalidStateException() {
        val handler = new DeleteHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificate(certificate)
            .certificateChain(certificateChain)
            .status(CertificateAuthorityStatus.ACTIVE.name())
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doThrow(InvalidStateException.class)
            .when(proxy).injectCredentialsAndInvoke(any(UpdateCertificateAuthorityRequest.class), any(Function.class));

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }
}
