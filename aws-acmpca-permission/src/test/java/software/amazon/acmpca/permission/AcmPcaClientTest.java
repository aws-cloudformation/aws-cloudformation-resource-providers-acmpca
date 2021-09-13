package software.amazon.acmpca.permission;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class AcmPcaClientTest extends TestBase{

    @Mock private AmazonWebServicesClientProxy proxy;

    @Test
    public void testCreatePermissionRequest() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .actions(actionList)
            .sourceAccount(sourceAccount)
            .build();

        val createPermissionRequest = acmPcaClient.createPermissionRequest(model);

        assertThat(createPermissionRequest.getCertificateAuthorityArn()).isEqualTo(model.getCertificateAuthorityArn());
        assertThat(createPermissionRequest.getPrincipal()).isEqualTo(model.getPrincipal());
        assertThat(createPermissionRequest.getSourceAccount()).isEqualTo(model.getSourceAccount());
        assertThat(createPermissionRequest.getActions()).isEqualTo(model.getActions());
    }

    @Test
    public void testCreatePermissionRequest__NoSourceAccount() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .actions(actionList)
            .build();

        val createPermissionRequest = acmPcaClient.createPermissionRequest(model);

        assertThat(createPermissionRequest.getCertificateAuthorityArn()).isEqualTo(model.getCertificateAuthorityArn());
        assertThat(createPermissionRequest.getPrincipal()).isEqualTo(model.getPrincipal());
        assertThat(createPermissionRequest.getSourceAccount()).isNull();
        assertThat(createPermissionRequest.getActions()).isEqualTo(model.getActions());
    }

    @Test
    public void testDeletePermissionRequest__SourceAccount() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .sourceAccount(sourceAccount)
            .build();

        val deletePermissionRequest = acmPcaClient.deletePermissionRequest(model);

        assertThat(deletePermissionRequest.getCertificateAuthorityArn()).isEqualTo(model.getCertificateAuthorityArn());
        assertThat(deletePermissionRequest.getPrincipal()).isEqualTo(model.getPrincipal());
        assertThat(deletePermissionRequest.getSourceAccount()).isEqualTo(model.getSourceAccount());
    }

    @Test
    public void testDeletePermissionRequest__NoSourceAccount() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .principal(principal)
            .build();

        val deletePermissionRequest = acmPcaClient.deletePermissionRequest(model);

        assertThat(deletePermissionRequest.getCertificateAuthorityArn()).isEqualTo(model.getCertificateAuthorityArn());
        assertThat(deletePermissionRequest.getPrincipal()).isEqualTo(model.getPrincipal());
        assertThat(deletePermissionRequest.getSourceAccount()).isNull();
    }
}
