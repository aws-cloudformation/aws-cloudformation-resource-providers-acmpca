package software.amazon.acmpca.certificateauthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;

import com.amazonaws.services.acmpca.AWSACMPCA;
import com.amazonaws.services.acmpca.AWSACMPCAClientBuilder;
import com.amazonaws.services.acmpca.model.ASN1Subject;
import com.amazonaws.services.acmpca.model.CertificateAuthority;
import com.amazonaws.services.acmpca.model.CertificateAuthorityConfiguration;
import com.amazonaws.services.acmpca.model.CertificateAuthorityStatus;
import com.amazonaws.services.acmpca.model.CreateCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.CrlConfiguration;
import com.amazonaws.services.acmpca.model.DeleteCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.DescribeCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import com.amazonaws.services.acmpca.model.ListCertificateAuthoritiesRequest;
import com.amazonaws.services.acmpca.model.ListCertificateAuthoritiesResult;
import com.amazonaws.services.acmpca.model.ListTagsRequest;
import com.amazonaws.services.acmpca.model.RevocationConfiguration;
import com.amazonaws.services.acmpca.model.Tag;
import com.amazonaws.services.acmpca.model.TagCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.UntagCertificateAuthorityRequest;
import com.amazonaws.services.acmpca.model.UpdateCertificateAuthorityRequest;
import com.google.common.annotations.VisibleForTesting;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public final class AcmPcaClient {

    private static final AWSACMPCA pcaClient = AWSACMPCAClientBuilder.defaultClient();
    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    private AmazonWebServicesClientProxy clientProxy;

    public String createCertificateAuthority(final ResourceModel model) {
        val subject = mapper.map(model.getSubject(), ASN1Subject.class);

        val tags = getTags(model);

        val certificateAuthorityConfiguration = new CertificateAuthorityConfiguration()
            .withKeyAlgorithm(model.getKeyAlgorithm())
            .withSigningAlgorithm(model.getSigningAlgorithm())
            .withSubject(subject);

        val createRequest = new CreateCertificateAuthorityRequest()
            .withCertificateAuthorityType(model.getType())
            .withCertificateAuthorityConfiguration(certificateAuthorityConfiguration)
            .withIdempotencyToken(UUID.randomUUID().toString());

        if (!isRevocationConfigurationEmpty(model)) {
            val revocationConfiguration = mapper.map(model.getRevocationConfiguration(), RevocationConfiguration.class);
            createRequest.setRevocationConfiguration(revocationConfiguration);
        }

        if (!tags.isEmpty()) {
            createRequest.setTags(tags);
        }

        return clientProxy.injectCredentialsAndInvoke(createRequest, pcaClient::createCertificateAuthority)
            .getCertificateAuthorityArn();
    }

    public void updateCertificateAuthorityRevocationConfiguration(final ResourceModel model) {
        val certificateAuthorityArn = model.getArn();

        val revocationConfiguration = isRevocationConfigurationEmpty(model) ?
            new RevocationConfiguration()
                .withCrlConfiguration(new CrlConfiguration()
                    .withEnabled(false)) :
            mapper.map(model.getRevocationConfiguration(), RevocationConfiguration.class);

        val updateRequest = new UpdateCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withRevocationConfiguration(revocationConfiguration);

        clientProxy.injectCredentialsAndInvoke(updateRequest, pcaClient::updateCertificateAuthority);
    }

    public void deleteCertificateAuthority(final ResourceModel model) {
        val certificateAuthorityArn = model.getArn();

        val deleteRequest = new DeleteCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn);

        clientProxy.injectCredentialsAndInvoke(deleteRequest, pcaClient::deleteCertificateAuthority);
    }

    public List<CertificateAuthority> listCertificateAuthorities() {
        ListCertificateAuthoritiesRequest listRequest = new ListCertificateAuthoritiesRequest()
            .withMaxResults(1000);
        ListCertificateAuthoritiesResult listResult = clientProxy.injectCredentialsAndInvoke(listRequest, pcaClient::listCertificateAuthorities);

        String nextToken = listResult.getNextToken();
        val resultList = new ArrayList<CertificateAuthority>(listResult.getCertificateAuthorities());

        while (Objects.nonNull(nextToken)) {
            listRequest = new ListCertificateAuthoritiesRequest()
                .withMaxResults(1000)
                .withNextToken(nextToken);

            listResult = clientProxy.injectCredentialsAndInvoke(listRequest, pcaClient::listCertificateAuthorities);
            nextToken = listResult.getNextToken();
            resultList.addAll(listResult.getCertificateAuthorities());
        }

        return resultList;
    }

    public CertificateAuthority describeCertificateAuthority(final ResourceModel model) {
        val certificateAuthorityArn = model.getArn();

        val describeRequest = new DescribeCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn);

        return clientProxy.injectCredentialsAndInvoke(describeRequest, pcaClient::describeCertificateAuthority)
            .getCertificateAuthority();
    }

    public String getCertificateAuthorityCsr(final ResourceModel model) {
        val certificateAuthorityArn = model.getArn();

        val getCsrRequest = new GetCertificateAuthorityCsrRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn);

        return clientProxy.injectCredentialsAndInvoke(getCsrRequest, pcaClient::getCertificateAuthorityCsr)
            .getCsr();
    }

    public List<Tag> listCertificateAuthorityTags(final ResourceModel model) {
        val certificateAuthorityArn = model.getArn();

        val listTagsRequest = new ListTagsRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn);

        return clientProxy.injectCredentialsAndInvoke(listTagsRequest, pcaClient::listTags)
            .getTags();
    }

    public void tagCertificateAuthority(final ResourceModel model, final Collection<Tag> tags) {
        if (tags.isEmpty()) {
            return;
        }

        val certificateAuthorityArn = model.getArn();

        val tagRequest = new TagCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withTags(tags);

        clientProxy.injectCredentialsAndInvoke(tagRequest, pcaClient::tagCertificateAuthority);
    }

    public void untagCertificateAuthority(final ResourceModel model, final Collection<Tag> tags) {
        if (tags.isEmpty()) {
            return;
        }

        val certificateAuthorityArn = model.getArn();

        val untagRequest = new UntagCertificateAuthorityRequest()
            .withCertificateAuthorityArn(certificateAuthorityArn)
            .withTags(tags);

        clientProxy.injectCredentialsAndInvoke(untagRequest, pcaClient::untagCertificateAuthority);
    }

    public ResourceModel populateResourceModel(final ResourceModel model) {
        val certificateAuthority = describeCertificateAuthority(model);

        val populatedModel = ResourceModel.builder()
            .arn(certificateAuthority.getArn())
            .build();

        if (certificateAuthorityFinishedCreation(certificateAuthority)) {
            val csr = getCertificateAuthorityCsr(model);
            populatedModel.setCertificateSigningRequest(csr);
        }

        return populatedModel;
    }

    public List<Tag> getTags(final ResourceModel model) {
        val tags = model.getTags();

        if (Objects.isNull(tags)) {
            return Collections.emptyList();
        }

        return tags.stream()
            .map(tag -> mapper.map(tag, Tag.class))
            .collect(Collectors.toList());
    }

    @VisibleForTesting
    boolean isRevocationConfigurationEmpty(final ResourceModel model) {
        val revocationConfiguration = model.getRevocationConfiguration();
        return Objects.isNull(revocationConfiguration) || Objects.isNull(revocationConfiguration.getCrlConfiguration());
    }

    @VisibleForTesting
    boolean certificateAuthorityFinishedCreation(final CertificateAuthority certificateAuthority) {
        val status = certificateAuthority.getStatus();
        return !Objects.equals(status, CertificateAuthorityStatus.CREATING.name())
            && !Objects.equals(status, CertificateAuthorityStatus.FAILED.name());
    }
}
