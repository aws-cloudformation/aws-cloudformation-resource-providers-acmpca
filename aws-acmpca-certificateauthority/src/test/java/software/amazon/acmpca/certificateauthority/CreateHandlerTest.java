package software.amazon.acmpca.certificateauthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.ASN1Subject;
import com.amazonaws.services.acmpca.model.CertificateAuthority;
import com.amazonaws.services.acmpca.model.CertificateAuthorityConfiguration;
import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;
import com.amazonaws.services.acmpca.model.CertificateAuthorityType;
import com.amazonaws.services.acmpca.model.CreateCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.CreateCertificateAuthorityResult;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityResult;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCsrResult;
import com.google.common.collect.ImmutableList;

import software.amazon.cloudformation.proxy.HandlerErrorCode;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class CreateHandlerTest extends TestBase {

    @Test
    public void handleRequest__CreateCertificateAuthority() {
        val handler = new CreateHandler();

        val tags = ImmutableList.of(Tag.builder()
            .key("key")
            .value("value")
            .build());

        val model = ResourceModel.builder()
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .tags(tags)
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
            .withStatus(CertificateAuthorityStatus.CREATING);

        doReturn(new CreateCertificateAuthorityResult()
            .withCertificateAuthorityArn(certificateAuthorityArn))
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any());

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.IN_PROGRESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(1);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();

        verify(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any());
        verify(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());
    }

    @Test
    public void handleRequest__GetCreatedCertificateAuthority() {
        val handler = new CreateHandler();

        val tags = ImmutableList.of(Tag.builder()
            .key("key")
            .value("value")
            .build());

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .tags(tags)
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
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());

        doReturn(new GetCertificateAuthorityCsrResult()
            .withCsr(csr))
            .when(proxy).injectCredentialsAndInvoke(any(GetCertificateAuthorityCsrRequest.class), any());

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();

        verify(proxy, never()).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any());
        verify(proxy).injectCredentialsAndInvoke(any(GetCertificateAuthorityCsrRequest.class), any());
        verify(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());
    }

    @Test
    public void getCreatedCertificateAuthority__FAILED() {
        val handler = new CreateHandler();

        val tags = ImmutableList.of(Tag.builder()
            .key("key")
            .value("value")
            .build());

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .tags(tags)
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
            .withStatus(CertificateAuthorityStatus.FAILED);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.FAILED);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isNull();
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getErrorCode()).isEqualTo(HandlerErrorCode.ServiceInternalError);

        verify(proxy, never()).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any());
        verify(proxy, never()).injectCredentialsAndInvoke(any(GetCertificateAuthorityCsrRequest.class), any());
        verify(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());
    }
}
