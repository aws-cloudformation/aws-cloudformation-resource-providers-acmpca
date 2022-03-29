package software.amazon.acmpca.certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.acmpca.model.ExtendedKeyUsageType;
import com.amazonaws.services.acmpca.model.PolicyQualifierId;
import com.amazonaws.services.acmpca.model.ValidityPeriodType;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class AcmPcaClientTest extends TestBase {

    @Mock private AmazonWebServicesClientProxy proxy;

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
    }

    @Test
    public void testCreateIssueCertificateRequest() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val validityNotAfter = Validity.builder()
            .type(ValidityPeriodType.DAYS.toString())
            .value(1.0)
            .build();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .templateArn(defaultTemplateArn)
            .validity(validityNotAfter)
            .build();

        val issueCertificateRequest = acmPcaClient.createIssueCertificateRequest(model);

        assertThat(issueCertificateRequest.getCertificateAuthorityArn()).isEqualTo(model.getCertificateAuthorityArn());
        assertThat(issueCertificateRequest.getCsr()).isEqualTo(acmPcaClient.toByteBuffer(model.getCertificateSigningRequest()));
        assertThat(issueCertificateRequest.getSigningAlgorithm()).isEqualTo(model.getSigningAlgorithm());
        assertThat(issueCertificateRequest.getTemplateArn()).isEqualTo(model.getTemplateArn());
        assertThat(issueCertificateRequest.getValidity().getType()).isEqualTo(model.getValidity().getType());
        assertThat(issueCertificateRequest.getValidity().getValue().doubleValue()).isEqualTo(Double.valueOf(model.getValidity().getValue()));
        assertThat(issueCertificateRequest.getIdempotencyToken()).isNotNull();
    }

    @Test
    public void testCreateIssueCertificateRequest__ValidityNotBefore() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val validityNotBefore = Validity.builder()
            .type(ValidityPeriodType.MONTHS.toString())
            .value(2.0)
            .build();

        val model = ResourceModel.builder()
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .validityNotBefore(validityNotBefore)
            .validity(validity)
            .build();

        val issueCertificateRequest = acmPcaClient.createIssueCertificateRequest(model);

        assertThat(issueCertificateRequest.getValidityNotBefore().getType()).isEqualTo(model.getValidityNotBefore().getType());
        assertThat(issueCertificateRequest.getValidityNotBefore().getValue().doubleValue()).isEqualTo(model.getValidityNotBefore().getValue());
    }

    @Test
    public void testCreateIssueCertificateRequest__ApiPassthrough__Extensions() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val keyUsage = KeyUsage.builder()
            .digitalSignature(true)
            .nonRepudiation(false)
            .keyEncipherment(true)
            .dataEncipherment(false)
            .keyAgreement(true)
            .keyCertSign(false)
            .cRLSign(true)
            .encipherOnly(false)
            .decipherOnly(true)
            .build();

        val extendedKeyUsageList = Arrays.asList(
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.SERVER_AUTH.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.CODE_SIGNING.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.TIME_STAMPING.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.SMART_CARD_LOGIN.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.CERTIFICATE_TRANSPARENCY.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageObjectIdentifier("0.1.2.3")
                .build()
        );

        val certificatePolicies = Arrays.asList(
            PolicyInformation.builder()
                .certPolicyId("0.1.2.3")
                .build(),
            PolicyInformation.builder()
                .certPolicyId("2.1.2.3")
                .policyQualifiers(Arrays.asList(
                    PolicyQualifierInfo.builder()
                        .policyQualifierId(PolicyQualifierId.CPS.toString())
                        .qualifier(Qualifier.builder()
                            .cpsUri("cpsUri")
                            .build())
                        .build()
                )).build()
        );

        val subjectAlternativeNames = Arrays.asList(
            GeneralName.builder()
                .directoryName(Subject.builder()
                    .country("US")
                    .build())
                .ediPartyName(EdiPartyName.builder()
                    .partyName("partyName")
                    .nameAssigner("nameAssigner")
                    .build())
                .otherName(OtherName.builder()
                    .typeId("typeId")
                    .value("value")
                    .build())
                .build()
        );

        val customExtensions = Arrays.asList(
            CustomExtension.builder()
                .objectIdentifier("1.3.4.5")
                .value("YCMF")
                .critical(true)
                .build(),
            CustomExtension.builder()
                .objectIdentifier("1.3.4.6")
                .value("YCMD")
                .build(),
            CustomExtension.builder()
                .objectIdentifier("1.3.4.7")
                .value("YCME")
                .critical(false)
                .build()
        );

        val extensions = Extensions.builder()
            .keyUsage(keyUsage)
            .extendedKeyUsage(extendedKeyUsageList)
            .certificatePolicies(certificatePolicies)
            .subjectAlternativeNames(subjectAlternativeNames)
            .customExtensions(customExtensions)
            .build();

        val apiPassthrough = ApiPassthrough.builder()
            .extensions(extensions)
            .build();

        val model = ResourceModel.builder()
            .apiPassthrough(apiPassthrough)
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .validity(validity)
            .build();

        val issueCertificateRequest = acmPcaClient.createIssueCertificateRequest(model);
        val sdkExtensions = issueCertificateRequest.getApiPassthrough().getExtensions();

        assertThat(sdkExtensions.getKeyUsage()).isEqualTo(new com.amazonaws.services.acmpca.model.KeyUsage()
            .withDigitalSignature(true)
            .withNonRepudiation(false)
            .withKeyEncipherment(true)
            .withDataEncipherment(false)
            .withKeyAgreement(true)
            .withKeyCertSign(false)
            .withCRLSign(true)
            .withEncipherOnly(false)
            .withDecipherOnly(true));

        assertThat(sdkExtensions.getExtendedKeyUsage()).isEqualTo(Arrays.asList(
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.SERVER_AUTH.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.CODE_SIGNING.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.TIME_STAMPING.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.SMART_CARD_LOGIN.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.CERTIFICATE_TRANSPARENCY.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageObjectIdentifier("0.1.2.3")
        ));

        assertThat(sdkExtensions.getCertificatePolicies()).isEqualTo(Arrays.asList(
            new com.amazonaws.services.acmpca.model.PolicyInformation()
                .withCertPolicyId("0.1.2.3"),
            new com.amazonaws.services.acmpca.model.PolicyInformation()
                .withCertPolicyId("2.1.2.3")
                .withPolicyQualifiers(Arrays.asList(
                    new com.amazonaws.services.acmpca.model.PolicyQualifierInfo()
                        .withPolicyQualifierId(PolicyQualifierId.CPS.toString())
                        .withQualifier(new com.amazonaws.services.acmpca.model.Qualifier()
                            .withCpsUri("cpsUri"))
                ))
        ));

        assertThat(sdkExtensions.getSubjectAlternativeNames()).isEqualTo(Arrays.asList(
            new com.amazonaws.services.acmpca.model.GeneralName()
                .withDirectoryName(new com.amazonaws.services.acmpca.model.ASN1Subject()
                    .withCountry("US"))
                .withEdiPartyName(new com.amazonaws.services.acmpca.model.EdiPartyName()
                    .withPartyName("partyName")
                    .withNameAssigner("nameAssigner"))
                .withOtherName(new com.amazonaws.services.acmpca.model.OtherName()
                    .withTypeId("typeId")
                    .withValue("value"))
        ));

        assertThat(sdkExtensions.getCustomExtensions()).isEqualTo(Arrays.asList(
            new com.amazonaws.services.acmpca.model.CustomExtension()
                .withObjectIdentifier("1.3.4.5")
                .withValue("YCMF")
                .withCritical(true),
            new com.amazonaws.services.acmpca.model.CustomExtension()
                .withObjectIdentifier("1.3.4.6")
                .withValue("YCMD"),
            new com.amazonaws.services.acmpca.model.CustomExtension()
                .withObjectIdentifier("1.3.4.7")
                .withValue("YCME")
                .withCritical(false)
        ));
    }

    @Test
    public void testCreateIssueCertificateRequest__ApiPassthrough__Extensions__Subject() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val keyUsage = KeyUsage.builder()
            .nonRepudiation(true)
            .dataEncipherment(true)
            .keyCertSign(true)
            .encipherOnly(true)
            .build();

        val extendedKeyUsageList = Arrays.asList(
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.CLIENT_AUTH.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.EMAIL_PROTECTION.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.OCSP_SIGNING.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageType(ExtendedKeyUsageType.DOCUMENT_SIGNING.toString())
                .build(),
            ExtendedKeyUsage.builder()
                .extendedKeyUsageObjectIdentifier("1.1.2.3")
                .build()
        );

        val subject = Subject.builder()
            .country("US")
            .state("VA")
            .build();

        val extensions = Extensions.builder()
            .keyUsage(keyUsage)
            .extendedKeyUsage(extendedKeyUsageList)
            .build();

        val apiPassthrough = ApiPassthrough.builder()
            .extensions(extensions)
            .subject(subject)
            .build();

        val model = ResourceModel.builder()
            .apiPassthrough(apiPassthrough)
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .validity(validity)
            .build();

        val issueCertificateRequest = acmPcaClient.createIssueCertificateRequest(model);
        val sdkExtensions = issueCertificateRequest.getApiPassthrough().getExtensions();

        assertThat(sdkExtensions.getKeyUsage()).isEqualTo(new com.amazonaws.services.acmpca.model.KeyUsage()
            .withNonRepudiation(true)
            .withDataEncipherment(true)
            .withKeyCertSign(true)
            .withEncipherOnly(true)
        );

        assertThat(sdkExtensions.getExtendedKeyUsage()).isEqualTo(Arrays.asList(
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.CLIENT_AUTH.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.EMAIL_PROTECTION.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.OCSP_SIGNING.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageType(ExtendedKeyUsageType.DOCUMENT_SIGNING.toString()),
            new com.amazonaws.services.acmpca.model.ExtendedKeyUsage()
                .withExtendedKeyUsageObjectIdentifier("1.1.2.3")
        ));

        assertThat(issueCertificateRequest.getApiPassthrough().getSubject()).isEqualTo(
            new com.amazonaws.services.acmpca.model.ASN1Subject()
                .withCountry("US")
                .withState("VA")
        );
    }

    @Test
    public void testCreateIssueCertificateRequest__ApiPassthrough__Subject__CustomAttributes() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val subject = Subject.builder()
            .customAttributes(Arrays.asList(
                CustomAttribute.builder()
                    .objectIdentifier("1.2.3.4")
                    .value("value1")
                    .build(),
                CustomAttribute.builder()
                    .objectIdentifier("1.2.3.4")
                    .value("value2")
                    .build()))
            .build();

        val apiPassthrough = ApiPassthrough.builder()
            .subject(subject)
            .build();

        val model = ResourceModel.builder()
            .apiPassthrough(apiPassthrough)
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .validity(validity)
            .build();

        val issueCertificateRequest = acmPcaClient.createIssueCertificateRequest(model);

        assertThat(issueCertificateRequest.getApiPassthrough().getSubject()).isEqualTo(
            new com.amazonaws.services.acmpca.model.ASN1Subject()
                .withCustomAttributes(Arrays.asList(
                    new com.amazonaws.services.acmpca.model.CustomAttribute()
                        .withObjectIdentifier("1.2.3.4")
                        .withValue("value1"),
                    new com.amazonaws.services.acmpca.model.CustomAttribute()
                        .withObjectIdentifier("1.2.3.4")
                        .withValue("value2")))
        );
    }

    @Test
    public void testCreateIssueCertificateRequest__ApiPassthrough__SAN__CustomAttributes() {
        val acmPcaClient = new AcmPcaClient(proxy);

        val subject = Subject.builder()
            .customAttributes(Arrays.asList(
                CustomAttribute.builder()
                    .objectIdentifier("1.2.3.4")
                    .value("value1")
                    .build(),
                CustomAttribute.builder()
                    .objectIdentifier("1.2.3.4")
                    .value("value2")
                    .build()))
            .build();

        val subjectAlternativeNames = Arrays.asList(
            GeneralName.builder()
                .directoryName(subject)
                .ediPartyName(EdiPartyName.builder()
                    .partyName("partyName")
                    .nameAssigner("nameAssigner")
                    .build())
                .otherName(OtherName.builder()
                    .typeId("typeId")
                    .value("value")
                    .build())
                .build()
        );

        val extensions = Extensions.builder()
            .subjectAlternativeNames(subjectAlternativeNames)
            .build();

        val apiPassthrough = ApiPassthrough.builder()
            .extensions(extensions)
            .build();

        val model = ResourceModel.builder()
            .apiPassthrough(apiPassthrough)
            .certificateAuthorityArn(certificateAuthorityArn)
            .certificateSigningRequest(csr)
            .signingAlgorithm(signingAlgorithm)
            .validity(validity)
            .build();

        val issueCertificateRequest = acmPcaClient.createIssueCertificateRequest(model);
        val sdkExtensions = issueCertificateRequest.getApiPassthrough().getExtensions();

        assertThat(sdkExtensions.getSubjectAlternativeNames()).isEqualTo(Arrays.asList(
            new com.amazonaws.services.acmpca.model.GeneralName()
                .withDirectoryName(new com.amazonaws.services.acmpca.model.ASN1Subject()
                    .withCustomAttributes(Arrays.asList(
                        new com.amazonaws.services.acmpca.model.CustomAttribute()
                            .withObjectIdentifier("1.2.3.4")
                            .withValue("value1"),
                        new com.amazonaws.services.acmpca.model.CustomAttribute()
                            .withObjectIdentifier("1.2.3.4")
                            .withValue("value2"))))
                .withEdiPartyName(new com.amazonaws.services.acmpca.model.EdiPartyName()
                    .withPartyName("partyName")
                    .withNameAssigner("nameAssigner"))
                .withOtherName(new com.amazonaws.services.acmpca.model.OtherName()
                    .withTypeId("typeId")
                    .withValue("value"))
        ));
    }
}
