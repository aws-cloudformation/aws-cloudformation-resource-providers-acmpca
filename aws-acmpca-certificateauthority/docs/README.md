# AWS::ACMPCA::CertificateAuthority

Private certificate authority.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "AWS::ACMPCA::CertificateAuthority",
    "Properties" : {
        "<a href="#type" title="Type">Type</a>" : <i>String</i>,
        "<a href="#keyalgorithm" title="KeyAlgorithm">KeyAlgorithm</a>" : <i>String</i>,
        "<a href="#signingalgorithm" title="SigningAlgorithm">SigningAlgorithm</a>" : <i>String</i>,
        "<a href="#subject" title="Subject">Subject</a>" : <i><a href="subject.md">Subject</a></i>,
        "<a href="#revocationconfiguration" title="RevocationConfiguration">RevocationConfiguration</a>" : <i><a href="revocationconfiguration.md">RevocationConfiguration</a></i>,
        "<a href="#tags" title="Tags">Tags</a>" : <i>[ <a href="tag.md">Tag</a>, ... ]</i>,
        "<a href="#csrextensions" title="CsrExtensions">CsrExtensions</a>" : <i><a href="csrextensions.md">CsrExtensions</a></i>,
        "<a href="#keystoragesecuritystandard" title="KeyStorageSecurityStandard">KeyStorageSecurityStandard</a>" : <i>String</i>
    }
}
</pre>

### YAML

<pre>
Type: AWS::ACMPCA::CertificateAuthority
Properties:
    <a href="#type" title="Type">Type</a>: <i>String</i>
    <a href="#keyalgorithm" title="KeyAlgorithm">KeyAlgorithm</a>: <i>String</i>
    <a href="#signingalgorithm" title="SigningAlgorithm">SigningAlgorithm</a>: <i>String</i>
    <a href="#subject" title="Subject">Subject</a>: <i><a href="subject.md">Subject</a></i>
    <a href="#revocationconfiguration" title="RevocationConfiguration">RevocationConfiguration</a>: <i><a href="revocationconfiguration.md">RevocationConfiguration</a></i>
    <a href="#tags" title="Tags">Tags</a>: <i>
      - <a href="tag.md">Tag</a></i>
    <a href="#csrextensions" title="CsrExtensions">CsrExtensions</a>: <i><a href="csrextensions.md">CsrExtensions</a></i>
    <a href="#keystoragesecuritystandard" title="KeyStorageSecurityStandard">KeyStorageSecurityStandard</a>: <i>String</i>
</pre>

## Properties

#### Type

The type of the certificate authority.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### KeyAlgorithm

Public key algorithm and size, in bits, of the key pair that your CA creates when it issues a certificate.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### SigningAlgorithm

Algorithm your CA uses to sign certificate requests.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Subject

Structure that contains X.500 distinguished name information for your CA.

_Required_: Yes

_Type_: <a href="subject.md">Subject</a>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### RevocationConfiguration

Certificate Authority revocation information.

_Required_: No

_Type_: <a href="revocationconfiguration.md">RevocationConfiguration</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Tags

_Required_: No

_Type_: List of <a href="tag.md">Tag</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CsrExtensions

Structure that contains CSR pass though extensions information.

_Required_: No

_Type_: <a href="csrextensions.md">CsrExtensions</a>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### KeyStorageSecurityStandard

KeyStorageSecurityStadard defines a cryptographic key management compliance standard used for handling CA keys.

_Required_: No

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

## Return Values

### Ref

When you pass the logical ID of this resource to the intrinsic `Ref` function, Ref returns the Arn.

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### Arn

Returns the <code>Arn</code> value.

#### CertificateSigningRequest

The base64 PEM-encoded certificate signing request (CSR) for your certificate authority certificate.
