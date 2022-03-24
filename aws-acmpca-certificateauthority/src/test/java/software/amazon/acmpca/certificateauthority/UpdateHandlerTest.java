package software.amazon.acmpca.certificateauthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.InvalidStateException;
import com.amazonaws.services.acmpca.model.ListTagsRequest;
import com.amazonaws.services.acmpca.model.ListTagsResult;
import com.amazonaws.services.acmpca.model.UpdateCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.UpdateCertificateAuthorityResult;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class UpdateHandlerTest extends TestBase {

    @Test
    @SuppressWarnings("unchecked")
    public void handleRequest__Success() {
        val handler = new UpdateHandler();

        val model = ResourceModel.builder()
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(new ListTagsResult()
            .withTags(new com.amazonaws.services.acmpca.model.Tag()
                .withKey("key")
                .withValue("value")))
            .when(proxy).injectCredentialsAndInvoke(any(ListTagsRequest.class), any(Function.class));

        doReturn(new UpdateCertificateAuthorityResult())
            .when(proxy).injectCredentialsAndInvoke(any(UpdateCertificateAuthorityRequest.class), any(Function.class));

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void handleRequest__InvalidStateException() {
        val handler = new UpdateHandler();

        val model = ResourceModel.builder()
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doThrow(new InvalidStateException("ex")).when(proxy)
            .injectCredentialsAndInvoke(any(ListTagsRequest.class), any(Function.class));

        doReturn(new UpdateCertificateAuthorityResult())
            .when(proxy).injectCredentialsAndInvoke(any(UpdateCertificateAuthorityRequest.class), any(Function.class));

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.FAILED);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertNotNull(response.getMessage());
        assertNotNull(response.getErrorCode());
    }
}
