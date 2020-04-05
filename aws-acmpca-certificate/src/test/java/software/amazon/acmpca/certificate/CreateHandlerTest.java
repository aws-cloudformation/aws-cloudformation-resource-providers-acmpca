package software.amazon.acmpca.certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.GetCertificateRequest;
import com.amazonaws.services.acmpca.model.GetCertificateResult;
import com.amazonaws.services.acmpca.model.IssueCertificateRequest;
import com.amazonaws.services.acmpca.model.IssueCertificateResult;
import com.amazonaws.services.acmpca.model.RequestInProgressException;

import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class CreateHandlerTest extends TestBase{

    @Test
    public void testHandleRequest__SuccessfulCreate() {
        val handler = new CreateHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .templateArn(defaultTemplateArn)
            .validity(validity)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doReturn(new IssueCertificateResult().withCertificateArn(certificateArn))
            .when(proxy).injectCredentialsAndInvoke(any(IssueCertificateRequest.class), any());

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

        assertThat(model.getCertificate().equals(certificate));
        assertThat(model.getArn().equals(certificateArn));
    }

    @Test
    public void testHandleRequest__NoSuccess__RuntimeExceptionThrown() {
        val handler = new CreateHandler();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .templateArn(defaultTemplateArn)
            .validity(validity)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doThrow(new RuntimeException()).when(proxy)
            .injectCredentialsAndInvoke(any(IssueCertificateRequest.class), any());

        assertThrows(RuntimeException.class, () -> handler.handleRequest(proxy, request, null, log));
        assertNull(model.getCertificate());
        assertThat(model.getArn());
    }

    @Test
    public void testHandleRequest__SkippingIssueCertificate__InProgress() {
        val handler = new CreateHandler();

        val model = ResourceModel.builder()
            .arn(certificateArn)
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .templateArn(defaultTemplateArn)
            .validity(validity)
            .build();

        val request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        doThrow(new RequestInProgressException("IN_PROGRESS")).when(proxy)
            .injectCredentialsAndInvoke(any(GetCertificateRequest.class), any());

        val response = handler.handleRequest(proxy, request, null, log);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.IN_PROGRESS);
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(1);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }
}
