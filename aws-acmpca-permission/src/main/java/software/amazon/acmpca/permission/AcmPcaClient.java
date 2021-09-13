package software.amazon.acmpca.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.amazonaws.services.acmpca.AWSACMPCA;
import com.amazonaws.services.acmpca.AWSACMPCAClientBuilder;
import com.amazonaws.services.acmpca.model.CreatePermissionRequest;
import com.amazonaws.services.acmpca.model.CreatePermissionResult;
import com.amazonaws.services.acmpca.model.DeletePermissionRequest;
import com.amazonaws.services.acmpca.model.DeletePermissionResult;
import com.amazonaws.services.acmpca.model.ListPermissionsRequest;
import com.amazonaws.services.acmpca.model.ListPermissionsResult;
import com.amazonaws.services.acmpca.model.Permission;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;

import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public final class AcmPcaClient {

    private static final AWSACMPCA pcaClient = AWSACMPCAClientBuilder.defaultClient();

    private AmazonWebServicesClientProxy clientProxy;

    public CreatePermissionResult createPermission(final ResourceModel model) {
        val createPermissionRequest = createPermissionRequest(model);
        return clientProxy.injectCredentialsAndInvoke(createPermissionRequest, pcaClient::createPermission);
    }

    public DeletePermissionResult deletePermission(final ResourceModel model) {
        val request = deletePermissionRequest(model);
        return clientProxy.injectCredentialsAndInvoke(request, pcaClient::deletePermission);
    }

    public List<Permission> listPermissions(final ResourceModel model) {
        ListPermissionsRequest listRequest = new ListPermissionsRequest()
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn())
            .withMaxResults(1000);

        ListPermissionsResult listResult = clientProxy.injectCredentialsAndInvoke(listRequest, pcaClient::listPermissions);

        String nextToken = listResult.getNextToken();
        val resultList = new ArrayList<>(listResult.getPermissions());

        while (Objects.nonNull(nextToken)) {
            listRequest = new ListPermissionsRequest()
                .withCertificateAuthorityArn(model.getCertificateAuthorityArn())
                .withMaxResults(1000)
                .withNextToken(nextToken);

            listResult = clientProxy.injectCredentialsAndInvoke(listRequest, pcaClient::listPermissions);
            nextToken = listResult.getNextToken();
            resultList.addAll(listResult.getPermissions());
        }

        return resultList;
    }

    public Permission getPermission(final ResourceModel model) {
        val permissions = listPermissions(model).stream()
            .filter(permission -> Objects.equals(permission.getPrincipal(), model.getPrincipal()))
            .collect(Collectors.toList());

        return Iterables.getOnlyElement(permissions);
    }

    @VisibleForTesting
    CreatePermissionRequest createPermissionRequest(final ResourceModel model) {
        val request = new CreatePermissionRequest()
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn())
            .withActions(model.getActions())
            .withPrincipal(model.getPrincipal());

        val sourceAccount = model.getSourceAccount();
        if (Objects.nonNull(sourceAccount)) {
            request.setSourceAccount(sourceAccount);
        }

        return request;
    }

    @VisibleForTesting
    DeletePermissionRequest deletePermissionRequest(final ResourceModel model) {
        val request = new DeletePermissionRequest()
            .withCertificateAuthorityArn(model.getCertificateAuthorityArn())
            .withPrincipal(model.getPrincipal());

        val sourceAccount = model.getSourceAccount();
        if (Objects.nonNull(sourceAccount)) {
            request.setSourceAccount(sourceAccount);
        }

        return request;
    }
}
