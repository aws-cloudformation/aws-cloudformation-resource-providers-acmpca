package software.amazon.acmpca.certificateauthority;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.ASN1Subject;
import com.amazonaws.services.acmpca.model.AccessDescription;
import com.amazonaws.services.acmpca.model.AccessMethod;
import com.amazonaws.services.acmpca.model.AccessMethodType;
import com.amazonaws.services.acmpca.model.CertificateAuthority;
import com.amazonaws.services.acmpca.model.CertificateAuthorityConfiguration;
import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;
import com.amazonaws.services.acmpca.model.CertificateAuthorityType;
import com.amazonaws.services.acmpca.model.CreateCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.CreateCertificateAuthorityResult;
import com.amazonaws.services.acmpca.model.CsrExtensions;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityResult;
import com.amazonaws.services.acmpca.model.GeneralName;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCsrResult;
import com.amazonaws.services.acmpca.model.KeyUsage;
import com.amazonaws.services.acmpca.model.UpdateCertificateAuthorityRequest;

import com.google.common.collect.ImmutableList;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class AcmPcaClientTest extends TestBase {

    @Mock private AmazonWebServicesClientProxy proxy;

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createCertificateAuthority() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val tags = ImmutableList.of(Tag.builder()
            .key("key")
            .value("value")
            .build());

        val model = ResourceModel.builder()
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .keyStorageSecurityStandard(keyStorageSecurityStandard)
            .tags(tags)
            .build();

        doReturn(new CreateCertificateAuthorityResult()
            .withCertificateAuthorityArn(certificateAuthorityArn))
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any(Function.class));

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createCertificateAuthority__WithEmptyCsrExtensions() {
        val acmPcaClient = new AcmPcaClient(proxy);

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
                .csrExtensions(emptyCsrExtensions)
                .build();

        doReturn(new CreateCertificateAuthorityResult()
                .withCertificateAuthorityArn(certificateAuthorityArn))
                .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any(Function.class));

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createCertificateAuthority__WithCsrExtensions() {
        val acmPcaClient = new AcmPcaClient(proxy);

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
                .csrExtensions(csrExtensions)
                .build();

        doReturn(new CreateCertificateAuthorityResult()
                .withCertificateAuthorityArn(certificateAuthorityArn))
                .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any(Function.class));

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createCertificateAuthority__EmptyRevocationConfiguration__And__NullTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .build();

        val model = ResourceModel.builder()
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .build();

        doReturn(new CreateCertificateAuthorityResult()
            .withCertificateAuthorityArn(certificateAuthorityArn))
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any(Function.class));

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createCertificateAuthority__EmptyKeyStorageSecurityStandard() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .build();

        val model = ResourceModel.builder()
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .build();

        doReturn(new CreateCertificateAuthorityResult()
            .withCertificateAuthorityArn(certificateAuthorityArn))
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any(Function.class));

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createCertificateAuthority__WithKeyStorageSecurityStandard() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .build();

        val model = ResourceModel.builder()
            .subject(subject)
            .signingAlgorithm(signingAlgorithm)
            .keyAlgorithm(keyAlgorithm)
            .revocationConfiguration(revocationConfiguration)
            .keyStorageSecurityStandard(keyStorageSecurityStandard)
            .build();

        doReturn(new CreateCertificateAuthorityResult()
            .withCertificateAuthorityArn(certificateAuthorityArn))
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any(Function.class));

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateCertificateAuthorityRevocationConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .revocationConfiguration(revocationConfiguration)
            .build();

        acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
        verify(proxy).injectCredentialsAndInvoke(any(UpdateCertificateAuthorityRequest.class), any(Function.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateCertificateAuthorityRevocationConfiguration__EmptyRevocationConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .build();

        val expectedRevocationConfiguration = new com.amazonaws.services.acmpca.model.RevocationConfiguration()
            .withCrlConfiguration(new com.amazonaws.services.acmpca.model.CrlConfiguration().withEnabled(false))
            .withOcspConfiguration(new com.amazonaws.services.acmpca.model.OcspConfiguration().withEnabled(false));

        val expectedUpdateRequest = new UpdateCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withRevocationConfiguration(expectedRevocationConfiguration);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .revocationConfiguration(revocationConfiguration)
            .build();

        acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
        verify(proxy).injectCredentialsAndInvoke(eq(expectedUpdateRequest), any(Function.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateCertificateAuthorityRevocationConfiguration__WithCrlConfigurationAndEmptyOcspConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .crlConfiguration(CrlConfiguration.builder().enabled(true).build())
            .build();

        val expectedRevocationConfiguration = new com.amazonaws.services.acmpca.model.RevocationConfiguration()
            .withCrlConfiguration(new com.amazonaws.services.acmpca.model.CrlConfiguration().withEnabled(true))
            .withOcspConfiguration(new com.amazonaws.services.acmpca.model.OcspConfiguration().withEnabled(false));

        val expectedUpdateRequest = new UpdateCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withRevocationConfiguration(expectedRevocationConfiguration);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .revocationConfiguration(revocationConfiguration)
            .build();

        acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
        verify(proxy).injectCredentialsAndInvoke(eq(expectedUpdateRequest), any(Function.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateCertificateAuthorityRevocationConfiguration__WithOcspConfigurationAndEmptyCrlConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .ocspConfiguration(OcspConfiguration.builder().enabled(true).build())
            .build();

        val expectedRevocationConfiguration = new com.amazonaws.services.acmpca.model.RevocationConfiguration()
            .withCrlConfiguration(new com.amazonaws.services.acmpca.model.CrlConfiguration().withEnabled(false))
            .withOcspConfiguration(new com.amazonaws.services.acmpca.model.OcspConfiguration().withEnabled(true));

        val expectedUpdateRequest = new UpdateCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withRevocationConfiguration(expectedRevocationConfiguration);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .revocationConfiguration(revocationConfiguration)
            .build();

        acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
        verify(proxy).injectCredentialsAndInvoke(eq(expectedUpdateRequest), any(Function.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void tagCertificateAuthority__EmptyTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .build();

        acmPcaClient.tagCertificateAuthority(model, Collections.emptyList());
        verify(proxy, never()).injectCredentialsAndInvoke(any(), any(Function.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void untagCertificateAuthority__EmptyTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .build();

        acmPcaClient.untagCertificateAuthority(model, Collections.emptyList());
        verify(proxy, never()).injectCredentialsAndInvoke(any(), any(Function.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void populateResourceModel() {
        val acmPcaClient = new AcmPcaClient(proxy);

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
            .csrExtensions(csrExtensions)
            .build();

        val certificateAuthority = new CertificateAuthority()
            .withArn(certificateAuthorityArn)
            .withType(CertificateAuthorityType.ROOT)
            .withCertificateAuthorityConfiguration(new CertificateAuthorityConfiguration()
                .withSubject(new ASN1Subject())
                .withKeyAlgorithm(keyAlgorithm)
                .withSigningAlgorithm(signingAlgorithm)
                .withCsrExtensions(buildSDKModelCsrExtensions()))
            .withStatus(CertificateAuthorityStatus.PENDING_CERTIFICATE);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any(Function.class));

        doReturn(new GetCertificateAuthorityCsrResult()
            .withCsr(csrWithCsrExtensions))
            .when(proxy).injectCredentialsAndInvoke(any(GetCertificateAuthorityCsrRequest.class), any(Function.class));

        val populatedModel = acmPcaClient.populateResourceModel(model);
        assertThat(populatedModel.getArn()).isEqualTo(certificateAuthorityArn);
        assertThat(populatedModel.getCertificateSigningRequest()).isEqualTo(csrWithCsrExtensions);
        assertThat(populatedModel.getSubject()).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void populateResourceModel__CREATING() {
        val acmPcaClient = new AcmPcaClient(proxy);

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
            .csrExtensions(csrExtensions)
            .build();

        val certificateAuthority = new CertificateAuthority()
            .withArn(certificateAuthorityArn)
            .withType(CertificateAuthorityType.ROOT)
            .withCertificateAuthorityConfiguration(new CertificateAuthorityConfiguration()
                .withSubject(new ASN1Subject())
                .withKeyAlgorithm(keyAlgorithm)
                .withSigningAlgorithm(signingAlgorithm)
                .withCsrExtensions(buildSDKModelCsrExtensions()))
            .withStatus(CertificateAuthorityStatus.CREATING);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any(Function.class));

        val populatedModel = acmPcaClient.populateResourceModel(model);
        assertThat(populatedModel.getArn()).isEqualTo(certificateAuthorityArn);
        assertThat(populatedModel.getCertificateSigningRequest()).isNull();
        assertThat(populatedModel.getSubject()).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void populateResourceModel__DeletedCA() {
        val acmPcaClient = new AcmPcaClient(proxy);

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
            .csrExtensions(csrExtensions)
            .build();

        val certificateAuthority = new CertificateAuthority()
            .withArn(certificateAuthorityArn)
            .withType(CertificateAuthorityType.ROOT)
            .withCertificateAuthorityConfiguration(new CertificateAuthorityConfiguration()
                .withSubject(new ASN1Subject())
                .withKeyAlgorithm(keyAlgorithm)
                .withSigningAlgorithm(signingAlgorithm)
                .withCsrExtensions(buildSDKModelCsrExtensions()))
            .withStatus(CertificateAuthorityStatus.DELETED);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any(Function.class));

        val populatedModel = acmPcaClient.populateResourceModel(model);
        assertThat(populatedModel.getArn()).isEqualTo(certificateAuthorityArn);
        assertThat(populatedModel.getCertificateSigningRequest()).isNull();
        assertThat(populatedModel.getSubject()).isNull();
    }

    @Test
    public void getTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val tags = ImmutableList.of(Tag.builder()
            .key("key")
            .value("value")
            .build());

        val model = ResourceModel.builder()
            .tags(tags)
            .build();

        val outputTags = ImmutableList.of(new com.amazonaws.services.acmpca.model.Tag()
            .withKey("key")
            .withValue("value"));

        assertThat(acmPcaClient.getTags(model)).isEqualTo(outputTags);
    }

    @Test
    public void getTags__NullTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .build();

        val outputTags = ImmutableList.<com.amazonaws.services.acmpca.model.Tag>of();

        assertThat(acmPcaClient.getTags(model)).isEqualTo(outputTags);
    }

    @Test
    public void getTags__EmptyTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val tags = ImmutableList.<Tag>of();

        val model = ResourceModel.builder()
            .tags(tags)
            .build();

        val outputTags = ImmutableList.<com.amazonaws.services.acmpca.model.Tag>of();

        assertThat(acmPcaClient.getTags(model)).isEqualTo(outputTags);
    }

    @Test
    public void isRevocationConfigurationEmpty__EmptyRevocationConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .build();

        assertThat(acmPcaClient.isRevocationConfigurationEmpty(model)).isTrue();
    }

    @Test
    public void isRevocationConfigurationEmpty__EmptyCrlOCSPConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .build();

        val model = ResourceModel.builder()
            .revocationConfiguration(revocationConfiguration)
            .build();

        assertThat(acmPcaClient.isRevocationConfigurationEmpty(model)).isTrue();
    }

    @Test
    public void isRevocationConfigurationEmpty__False() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .crlConfiguration(CrlConfiguration.builder()
                .build())
            .ocspConfiguration(OcspConfiguration.builder()
                .build())
            .build();

        val model = ResourceModel.builder()
            .revocationConfiguration(revocationConfiguration)
            .build();

        assertThat(acmPcaClient.isRevocationConfigurationEmpty(model)).isFalse();
    }

    @Test
    public void certificateAuthorityFinishedCreation__Finished() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val certificateAuthority = new CertificateAuthority()
            .withStatus(CertificateAuthorityStatus.ACTIVE);

        assertThat(acmPcaClient.certificateAuthorityFinishedCreation(certificateAuthority)).isTrue();
    }

    @Test
    public void certificateAuthorityFinishedCreation__FAILED() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val certificateAuthority = new CertificateAuthority()
            .withStatus(CertificateAuthorityStatus.FAILED);

        assertThat(acmPcaClient.certificateAuthorityFinishedCreation(certificateAuthority)).isFalse();
    }

    @Test
    public void certificateAuthorityFinishedCreation__CREATING() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val certificateAuthority = new CertificateAuthority()
            .withStatus(CertificateAuthorityStatus.CREATING);

        assertThat(acmPcaClient.certificateAuthorityFinishedCreation(certificateAuthority)).isFalse();
    }

    @Test
    public void testMapCsrExtensions() {
        val result = AcmPcaClient.getMapper().map(csrExtensions, com.amazonaws.services.acmpca.model.CsrExtensions.class);
        assertNotNull(result);
        assertNotNull(result.getKeyUsage());
        assertNotNull(result.getSubjectInformationAccess());
        assertEquals(2, result.getSubjectInformationAccess().size());
    }

    @Test
    public void testMapCsrExtensions__NoSia() {
        val modelCsrExtensions = software.amazon.acmpca.certificateauthority.CsrExtensions.builder()
                .keyUsage(csrExtensions.getKeyUsage())
                .build();
        val result = AcmPcaClient.getMapper().map(modelCsrExtensions, com.amazonaws.services.acmpca.model.CsrExtensions.class);
        assertNotNull(result);
        assertNotNull(result.getKeyUsage());
        assertNull(result.getSubjectInformationAccess());
    }

    @Test
    public void testMapCsrExtensions__NoKu() {
        val modelCsrExtensions = software.amazon.acmpca.certificateauthority.CsrExtensions.builder()
                .subjectInformationAccess(csrExtensions.getSubjectInformationAccess())
                .build();
        val result = AcmPcaClient.getMapper().map(modelCsrExtensions, com.amazonaws.services.acmpca.model.CsrExtensions.class);
        assertNotNull(result);
        assertNull(result.getKeyUsage());
        assertNotNull(result.getSubjectInformationAccess());
    }

    @Test
    public void testMapCsrExtensions__Empty() {
        val modelCsrExtensions = software.amazon.acmpca.certificateauthority.CsrExtensions.builder().build();
        val result = AcmPcaClient.getMapper().map(modelCsrExtensions, com.amazonaws.services.acmpca.model.CsrExtensions.class);
        assertNotNull(result);
        assertNull(result.getKeyUsage());
        assertNull(result.getSubjectInformationAccess());
    }

    private CsrExtensions buildSDKModelCsrExtensions() {
        return new CsrExtensions()
                .withKeyUsage(new KeyUsage()
                        .withDigitalSignature(true))
                .withSubjectInformationAccess(new ImmutableList.Builder<AccessDescription>()
                        .add(new AccessDescription()
                                .withAccessMethod(new AccessMethod().withAccessMethodType(AccessMethodType.CA_REPOSITORY))
                                .withAccessLocation(new GeneralName().withUniformResourceIdentifier("fakeURI-CA_REPOSITORY")))
                        .add(new AccessDescription()
                                .withAccessMethod(new AccessMethod().withCustomObjectIdentifier("1.3.6.1.5.5.7.48.10"))
                                .withAccessLocation(new GeneralName().withUniformResourceIdentifier("fakeURI-RESOURCE_PKI_MANIFEST")))
                        .build());
    }
}
