package software.amazon.acmpca.certificateauthorityactivation;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import com.amazonaws.services.acmpca.AWSACMPCA;
import com.amazonaws.services.acmpca.AWSACMPCAClientBuilder;
import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCertificateRequest;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCertificateResult;
import com.amazonaws.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;
import com.amazonaws.services.acmpca.model.UpdateCertificateAuthorityRequest;
import com.google.common.annotations.VisibleForTesting;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public final class AcmPcaClient {

    private static final AWSACMPCA pcaClient = AWSACMPCAClientBuilder.defaultClient();

    private AmazonWebServicesClientProxy clientProxy;

    public void getCertificateAuthorityCertificate(final ResourceModel model) {
        val certificateAuthorityArn = model.getCertificateAuthorityArn();

        val request = new GetCertificateAuthorityCertificateRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn);

        val response = clientProxy.injectCredentialsAndInvoke(request, pcaClient::getCertificateAuthorityCertificate);

        setCompleteCertificateChain(response, model);
    }

    public void importCertificateAuthorityCertificate(final ResourceModel model) {
        val certificateAuthorityArn = model.getCertificateAuthorityArn();
        val certificate = model.getCertificate();
        val certificateChain = model.getCertificateChain();

        val request = new ImportCertificateAuthorityCertificateRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withCertificate(toByteBuffer(certificate));

        if (Objects.nonNull(certificateChain)) {
            val chainByteBuffer = toByteBuffer(certificateChain);
            request.setCertificateChain(chainByteBuffer);
        }

        clientProxy.injectCredentialsAndInvoke(request, pcaClient::importCertificateAuthorityCertificate);
    }

    public void updateCertificateAuthorityStatus(final ResourceModel model) {
        val certificateAuthorityArn = model.getCertificateAuthorityArn();
        val status = Optional.ofNullable(model.getStatus())
            .orElse(CertificateAuthorityStatus.ACTIVE.name());

        val request = new UpdateCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withStatus(status);

        clientProxy.injectCredentialsAndInvoke(request, pcaClient::updateCertificateAuthority);
    }

    public void disableCertificateAuthority(final ResourceModel model) {
        model.setStatus(CertificateAuthorityStatus.DISABLED.name());
        updateCertificateAuthorityStatus(model);
    }

    public void setCompleteCertificateChain(final ResourceModel model) {
        val completeCertificateChain = getCompleteCertificateChain(model.getCertificateChain(), model.getCertificate());
        model.setCompleteCertificateChain(completeCertificateChain);
    }

    @VisibleForTesting
    String getCompleteCertificateChain(final String certificateChain, final String certificate) {
        return Optional.ofNullable(certificateChain)
            .map(chain -> certificate + "\n" + chain)
            .orElse(certificate);
    }

    private void setCompleteCertificateChain(final GetCertificateAuthorityCertificateResult response, final ResourceModel model) {
        val completeCertificateChain = getCompleteCertificateChain(response.getCertificateChain(), response.getCertificate());
        model.setCompleteCertificateChain(completeCertificateChain);
    }

    private ByteBuffer toByteBuffer(final String string) {
        val bytes = string.getBytes(StandardCharsets.UTF_8);
        return ByteBuffer.wrap(bytes);
    }
}
