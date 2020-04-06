package software.amazon.acmpca.certificateauthority;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;

public abstract class TestBase {

    @Mock protected AmazonWebServicesClientProxy proxy;
    @Mock protected Logger log;

    protected static final String certificateAuthorityArn = "arn:aws:acm-pca:region:account:certificate-authority/uuid";
    protected static final String keyAlgorithm = "keyAlgorithm";
    protected static final String signingAlgorithm = "signingAlgorithm";

    protected static final Subject subject = Subject.builder()
        .build();

    protected static final RevocationConfiguration revocationConfiguration = RevocationConfiguration.builder()
        .crlConfiguration(CrlConfiguration.builder()
            .enabled(true)
            .expirationInDays(1)
            .customCname("cname")
            .s3BucketName("s3bucket")
            .build())
        .build();

    protected static final String csr =
        "-----BEGIN CERTIFICATE REQUEST-----\n" +
        "MIICvDCCAaQCAQAwdzELMAkGA1UEBhMCVVMxDTALBgNVBAgMBFV0YWgxDzANBgNV\n" +
        "BAcMBkxpbmRvbjEWMBQGA1UECgwNRGlnaUNlcnQgSW5jLjERMA8GA1UECwwIRGln\n" +
        "aUNlcnQxHTAbBgNVBAMMFGV4YW1wbGUuZGlnaWNlcnQuY29tMIIBIjANBgkqhkiG\n" +
        "9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8+To7d+2kPWeBv/orU3LVbJwDrSQbeKamCmo\n" +
        "wp5bqDxIwV20zqRb7APUOKYoVEFFOEQs6T6gImnIolhbiH6m4zgZ/CPvWBOkZc+c\n" +
        "1Po2EmvBz+AD5sBdT5kzGQA6NbWyZGldxRthNLOs1efOhdnWFuhI162qmcflgpiI\n" +
        "WDuwq4C9f+YkeJhNn9dF5+owm8cOQmDrV8NNdiTqin8q3qYAHHJRW28glJUCZkTZ\n" +
        "wIaSR6crBQ8TbYNE0dc+Caa3DOIkz1EOsHWzTx+n0zKfqcbgXi4DJx+C1bjptYPR\n" +
        "BPZL8DAeWuA8ebudVT44yEp82G96/Ggcf7F33xMxe0yc+Xa6owIDAQABoAAwDQYJ\n" +
        "KoZIhvcNAQEFBQADggEBAB0kcrFccSmFDmxox0Ne01UIqSsDqHgL+XmHTXJwre6D\n" +
        "hJSZwbvEtOK0G3+dr4Fs11WuUNt5qcLsx5a8uk4G6AKHMzuhLsJ7XZjgmQXGECpY\n" +
        "Q4mC3yT3ZoCGpIXbw+iP3lmEEXgaQL0Tx5LFl/okKbKYwIqNiyKWOMj7ZR/wxWg/\n" +
        "ZDGRs55xuoeLDJ/ZRFf9bI+IaCUd1YrfYcHIl3G87Av+r49YVwqRDT0VDV7uLgqn\n" +
        "29XI1PpVUNCPQGn9p/eX6Qo7vpDaPybRtA2R7XLKjQaF9oXWeCUqy1hvJac9QFO2\n" +
        "97Ob1alpHPoZ7mWiEuJwjBPii6a9M9G30nUo39lBi1w=\n" +
        "-----END CERTIFICATE REQUEST-----";

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
        log = mock(Logger.class);
    }
}
