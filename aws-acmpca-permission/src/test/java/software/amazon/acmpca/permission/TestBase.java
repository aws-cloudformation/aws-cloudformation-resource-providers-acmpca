package software.amazon.acmpca.permission;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;

@ExtendWith(MockitoExtension.class)
public abstract class TestBase {

    @Mock protected AmazonWebServicesClientProxy proxy;
    @Mock protected Logger log;

    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
        log = mock(Logger.class);
    }

    protected static final String certificateAuthorityArn = "arn:aws:acm-pca:region:account:certificate-authority/uuid/certificate/uuid";
    protected static final String principal = "principal";
    protected static final String sourceAccount = "accountId";
    protected static final String policy = "policy";
    protected static final List<String> actionList = Arrays.asList("action1", "action2");
}
