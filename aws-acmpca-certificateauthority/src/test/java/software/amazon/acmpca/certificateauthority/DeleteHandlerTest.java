package software.amazon.acmpca.certificateauthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.ASN1Subject;
import com.amazonaws.services.acmpca.model.CertificateAuthority;
import com.amazonaws.services.acmpca.model.CertificateAuthorityConfiguration;
import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;
import com.amazonaws.services.acmpca.model.CertificateAuthorityType;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityResult;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class DeleteHandlerTest extends TestBase {

    @Test
    @SuppressWarnings("unchecked")
    public void handleRequest__Success() {
        val handler = new DeleteHandler();

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        val certificateAuthority = new CertificateAuthority()
            .withArn(certificateAuthorityArn)
            .withType(CertificateAuthorityType.ROOT)
            .withCertificateAuthorityConfiguration(new CertificateAuthorityConfiguration()
                .withSubject(new ASN1Subject())
                .withKeyAlgorithm(keyAlgorithm)
                .withSigningAlgorithm(signingAlgorithm))
            .withStatus(CertificateAuthorityStatus.PENDING_CERTIFICATE);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any(Function.class));

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
    public void handleRequest__Success__DeletedCA() {
        val handler = new DeleteHandler();

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        val certificateAuthority = new CertificateAuthority()
            .withArn(certificateAuthorityArn)
            .withType(CertificateAuthorityType.ROOT)
            .withCertificateAuthorityConfiguration(new CertificateAuthorityConfiguration()
                .withSubject(new ASN1Subject())
                .withKeyAlgorithm(keyAlgorithm)
                .withSigningAlgorithm(signingAlgorithm))
            .withStatus(CertificateAuthorityStatus.DELETED);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any(Function.class));

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
