package software.amazon.acmpca.certificate;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.amazonaws.services.acmpca.AWSACMPCA;
import com.amazonaws.services.acmpca.AWSACMPCAClientBuilder;
import com.amazonaws.services.acmpca.model.ApiPassthrough;
import com.amazonaws.services.acmpca.model.GetCertificateRequest;
import com.amazonaws.services.acmpca.model.GetCertificateResult;
import com.amazonaws.services.acmpca.model.IssueCertificateRequest;
import com.amazonaws.services.acmpca.model.Validity;
import com.google.common.annotations.VisibleForTesting;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public final class AcmPcaClient {

    private static final AWSACMPCA pcaClient;
    private static final Mapper mapper;

    private AmazonWebServicesClientProxy clientProxy;

    static {
        pcaClient = AWSACMPCAClientBuilder.defaultClient();

        /*
         * Collections are not mapping correctly in Dozer. The current theory is that
         * it has something to do with proper getters and setters not being generated
         * for the CloudFormation models. The fix: map them manually. Better solutions
         * are welcome.
         */
        val csrExtensionBeanMappingBuilder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(type(Extensions.class).accessible(true),
                    type(com.amazonaws.services.acmpca.model.Extensions.class).accessible(true))
                    .fields("certificatePolicies", "certificatePolicies")
                    .fields("extendedKeyUsage", "extendedKeyUsage")
                    .fields("subjectAlternativeNames", "subjectAlternativeNames");

                mapping(type(PolicyInformation.class).accessible(true),
                    type(com.amazonaws.services.acmpca.model.PolicyInformation.class).accessible(true))
                    .fields("policyQualifiers", "policyQualifiers");
            }
        };

        mapper = DozerBeanMapperBuilder.create()
                .withMappingBuilder(csrExtensionBeanMappingBuilder)
                .build();
    }

    public GetCertificateResult getCertificate(final ResourceModel model) {
        val getCertificateRequest = new GetCertificateRequest()
            .withCertificateArn(model.getArn())
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn());

        return clientProxy.injectCredentialsAndInvoke(getCertificateRequest, pcaClient::getCertificate);
    }

    public String issueCertificate(final ResourceModel model) {
        val issueCertificateRequest = createIssueCertificateRequest(model);

        return clientProxy.injectCredentialsAndInvoke(issueCertificateRequest, pcaClient::issueCertificate)
            .getCertificateArn();
    }

    @VisibleForTesting
    IssueCertificateRequest createIssueCertificateRequest(final ResourceModel model) {
        val apiPassthrough = toSdkApiPassthrough(model.getApiPassthrough());
        val csr = toByteBuffer(model.getCertificateSigningRequest());
        val validityNotBefore = toSdkValidity(model.getValidityNotBefore());
        val validityNotAfter = toSdkValidity(model.getValidity());

        return new IssueCertificateRequest()
            .withApiPassthrough(apiPassthrough)
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn())
            .withCsr(csr)
            .withSigningAlgorithm(model.getSigningAlgorithm())
            .withTemplateArn(model.getTemplateArn())
            .withValidityNotBefore(validityNotBefore)
            .withValidity(validityNotAfter)
            .withIdempotencyToken(UUID.randomUUID().toString());
    }

    @VisibleForTesting
    ByteBuffer toByteBuffer(final String string) {
        val bytes = string.getBytes(StandardCharsets.UTF_8);
        return ByteBuffer.wrap(bytes);
    }

    private ApiPassthrough toSdkApiPassthrough(final software.amazon.acmpca.certificate.ApiPassthrough apiPassthrough) {
        if (Objects.isNull(apiPassthrough)) {
            return null;
        }
        return mapper.map(apiPassthrough, ApiPassthrough.class);
    }

    private Validity toSdkValidity(final software.amazon.acmpca.certificate.Validity validity) {
        if (Objects.isNull(validity)) {
            return null;
        }
        return mapper.map(validity, Validity.class);
    }
}
