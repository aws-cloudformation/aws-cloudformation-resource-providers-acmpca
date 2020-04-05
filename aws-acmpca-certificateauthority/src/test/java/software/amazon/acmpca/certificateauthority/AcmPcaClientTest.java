package software.amazon.acmpca.certificateauthority;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
            .tags(tags)
            .build();

        doReturn(new CreateCertificateAuthorityResult()
            .withCertificateAuthorityArn(certificateAuthorityArn))
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any());

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
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
            .when(proxy).injectCredentialsAndInvoke(any(CreateCertificateAuthorityRequest.class), any());

        assertThat(acmPcaClient.createCertificateAuthority(model)).isEqualTo(certificateAuthorityArn);
    }

    @Test
    public void updateCertificateAuthorityRevocationConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .revocationConfiguration(revocationConfiguration)
            .build();

        acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
        verify(proxy).injectCredentialsAndInvoke(any(UpdateCertificateAuthorityRequest.class), any());
    }

    @Test
    public void updateCertificateAuthorityRevocationConfiguration__EmptyRevocationConfiguration() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val revocationConfiguration = RevocationConfiguration.builder()
            .build();

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .revocationConfiguration(revocationConfiguration)
            .build();

        acmPcaClient.updateCertificateAuthorityRevocationConfiguration(model);
        verify(proxy).injectCredentialsAndInvoke(any(UpdateCertificateAuthorityRequest.class), any());
    }

    @Test
    public void tagCertificateAuthority__EmptyTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .build();

        acmPcaClient.tagCertificateAuthority(model, Collections.emptyList());
        verify(proxy, never()).injectCredentialsAndInvoke(any(), any());
    }

    @Test
    public void untagCertificateAuthority__EmptyTags() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val model = ResourceModel.builder()
            .arn(certificateAuthorityArn)
            .build();

        acmPcaClient.untagCertificateAuthority(model, Collections.emptyList());
        verify(proxy, never()).injectCredentialsAndInvoke(any(), any());
    }

    @Test
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

        val populatedModel = acmPcaClient.populateResourceModel(model);
        assertThat(populatedModel.getArn()).isEqualTo(certificateAuthorityArn);
        assertThat(populatedModel.getCertificateSigningRequest()).isEqualTo(csr);
        assertThat(populatedModel.getKeyAlgorithm()).isNull();
        assertThat(populatedModel.getSigningAlgorithm()).isNull();
        assertThat(populatedModel.getSubject()).isNull();
    }

    @Test
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
            .build();

        val certificateAuthority = new CertificateAuthority()
            .withArn(certificateAuthorityArn)
            .withType(CertificateAuthorityType.ROOT)
            .withCertificateAuthorityConfiguration(new CertificateAuthorityConfiguration()
                .withSubject(new ASN1Subject())
                .withKeyAlgorithm(keyAlgorithm)
                .withSigningAlgorithm(signingAlgorithm))
            .withStatus(CertificateAuthorityStatus.CREATING);

        doReturn(new DescribeCertificateAuthorityResult()
            .withCertificateAuthority(certificateAuthority))
            .when(proxy).injectCredentialsAndInvoke(any(DescribeCertificateAuthorityRequest.class), any());

        val populatedModel = acmPcaClient.populateResourceModel(model);
        assertThat(populatedModel.getArn()).isEqualTo(certificateAuthorityArn);
        assertThat(populatedModel.getCertificateSigningRequest()).isNull();
        assertThat(populatedModel.getKeyAlgorithm()).isNull();
        assertThat(populatedModel.getSigningAlgorithm()).isNull();
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
    public void isRevocationConfigurationEmpty__EmptyCrlConfiguration() {
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
}
