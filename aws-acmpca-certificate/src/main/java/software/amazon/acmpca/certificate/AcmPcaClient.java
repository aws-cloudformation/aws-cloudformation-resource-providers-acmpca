package software.amazon.acmpca.certificate;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.amazonaws.services.acmpca.AWSACMPCA;
import com.amazonaws.services.acmpca.AWSACMPCAClientBuilder;
import com.amazonaws.services.acmpca.model.GetCertificateRequest;
import com.amazonaws.services.acmpca.model.GetCertificateResult;
import com.amazonaws.services.acmpca.model.IssueCertificateRequest;
import com.amazonaws.services.acmpca.model.Validity;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public final class AcmPcaClient {

    private static final AWSACMPCA pcaClient = AWSACMPCAClientBuilder.defaultClient();

    private AmazonWebServicesClientProxy clientProxy;

    public GetCertificateResult getCertificate(final ResourceModel model) {
        val getCertificateRequest = new GetCertificateRequest()
            .withCertificateArn(model.getArn())
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn());

        return clientProxy.injectCredentialsAndInvoke(getCertificateRequest, pcaClient::getCertificate);
    }

    public String issueCertificate(final ResourceModel model) {
        val csr = toByteBuffer(model.getCertificateSigningRequest());
        val validityValue = model.getValidity().getValue().longValue();
        val validityType = model.getValidity().getType();

        val validity = new Validity()
            .withValue(validityValue)
            .withType(validityType);

        val issueCertificateRequest = new IssueCertificateRequest()
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn())
            .withSigningAlgorithm(model.getSigningAlgorithm())
            .withValidity(validity)
            .withCsr(csr)
            .withIdempotencyToken(UUID.randomUUID().toString());

        val templateArn = model.getTemplateArn();
        if (Objects.nonNull(templateArn)) {
            issueCertificateRequest.setTemplateArn(templateArn);
        }

        return clientProxy.injectCredentialsAndInvoke(issueCertificateRequest, pcaClient::issueCertificate)
            .getCertificateArn();
    }

    private ByteBuffer toByteBuffer(final String string) {
        val bytes = string.getBytes(StandardCharsets.UTF_8);
        return ByteBuffer.wrap(bytes);
    }
}
