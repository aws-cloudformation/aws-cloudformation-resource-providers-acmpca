package software.amazon.acmpca.permission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.ListPermissionsRequest;
import com.amazonaws.services.acmpca.model.ListPermissionsResult;
import com.amazonaws.services.acmpca.model.Permission;
import com.google.common.collect.ImmutableList;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class ReadHandlerTest extends TestBase {

    @Test
    @SuppressWarnings("unchecked")
    public void handleRequest__SimpleSuccess() {
        val handler = new ReadHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .build();

        val permission = new Permission()
            .withActions(actionList)
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withPrincipal(principal)
            .withPolicy(policy)
            .withSourceAccount(sourceAccount);

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(new ListPermissionsResult().withPermissions(permission)).when(proxy)
            .injectCredentialsAndInvoke(any(ListPermissionsRequest.class), any(Function.class));

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNotNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
        assertThat(response.getResourceModel().getSourceAccount()).isEqualTo(sourceAccount);
        assertThat(response.getResourceModel().getCertificateAuthorityArn()).isEqualTo(certificateAuthorityArn);
        assertThat(response.getResourceModel().getActions()).isEqualTo(actionList);
        assertThat(response.getResourceModel().getPrincipal()).isEqualTo(principal);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void handleRequest__NoPermission() {
        val handler = new ReadHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(new ListPermissionsResult().withPermissions(ImmutableList.of())).when(proxy)
            .injectCredentialsAndInvoke(any(ListPermissionsRequest.class), any(Function.class));

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.FAILED);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getErrorCode()).isNotNull();
    }
}
