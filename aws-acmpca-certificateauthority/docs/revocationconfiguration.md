# AWS::ACMPCA::CertificateAuthority RevocationConfiguration

Certificate Authority revocation information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#crlconfiguration" title="CrlConfiguration">CrlConfiguration</a>" : <i><a href="crlconfiguration.md">CrlConfiguration</a></i>,
    "<a href="#ocspconfiguration" title="OcspConfiguration">OcspConfiguration</a>" : <i><a href="ocspconfiguration.md">OcspConfiguration</a></i>
}
</pre>

### YAML

<pre>
<a href="#crlconfiguration" title="CrlConfiguration">CrlConfiguration</a>: <i><a href="crlconfiguration.md">CrlConfiguration</a></i>
<a href="#ocspconfiguration" title="OcspConfiguration">OcspConfiguration</a>: <i><a href="ocspconfiguration.md">OcspConfiguration</a></i>
</pre>

## Properties

#### CrlConfiguration

Your certificate authority can create and maintain a certificate revocation list (CRL). A CRL contains information about certificates that have been revoked.

_Required_: No

_Type_: <a href="crlconfiguration.md">CrlConfiguration</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### OcspConfiguration

Helps to configure online certificate status protocol (OCSP) responder for your certificate authority

_Required_: No

_Type_: <a href="ocspconfiguration.md">OcspConfiguration</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

