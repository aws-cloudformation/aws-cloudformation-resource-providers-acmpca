package software.amazon.acmpca.certificateauthorityactivation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class AcmPcaClientTest extends TestBase {

    @Mock
    private AmazonWebServicesClientProxy proxy;

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
    }

    @Test
    public void testGetCompleteCertificateChain() {
        val acmPcaClient = new AcmPcaClient(proxy);
        val chain = acmPcaClient.getCompleteCertificateChain(certificateChain, certificate);

        assertEquals(completeCertificateChain, chain);
    }

    @Test
    public void testGetCompleteCertificateChain__NullChain() {
        val acmPcaClient = new AcmPcaClient(proxy);
        val chain = acmPcaClient.getCompleteCertificateChain(null, certificate);

        assertEquals(certificate, chain);
    }
}
