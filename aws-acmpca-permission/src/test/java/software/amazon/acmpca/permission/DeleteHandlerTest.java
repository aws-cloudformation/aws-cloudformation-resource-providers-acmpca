package software.amazon.acmpca.permission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.DeletePermissionRequest;
import com.amazonaws.services.acmpca.model.ResourceNotFoundException;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class DeleteHandlerTest extends TestBase {

    @Test
    public void handleRequest_SimpleSuccess() {
        val handler = new DeleteHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .sourceAccount(sourceAccount)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHandleRequest__Success__ResourceNotFoundException() {
        val handler = new DeleteHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .sourceAccount(sourceAccount).build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doThrow(ResourceNotFoundException.class).when(proxy)
            .injectCredentialsAndInvoke(any(DeletePermissionRequest.class), any(Function.class));

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
