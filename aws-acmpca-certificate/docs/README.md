# AWS::ACMPCA::Certificate

A certificate issued via a private certificate authority

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "AWS::ACMPCA::Certificate",
    "Properties" : {
        "<a href="#apipassthrough" title="ApiPassthrough">ApiPassthrough</a>" : <i><a href="apipassthrough.md">ApiPassthrough</a></i>,
        "<a href="#certificateauthorityarn" title="CertificateAuthorityArn">CertificateAuthorityArn</a>" : <i>String</i>,
        "<a href="#certificatesigningrequest" title="CertificateSigningRequest">CertificateSigningRequest</a>" : <i>String</i>,
        "<a href="#signingalgorithm" title="SigningAlgorithm">SigningAlgorithm</a>" : <i>String</i>,
        "<a href="#templatearn" title="TemplateArn">TemplateArn</a>" : <i>String</i>,
        "<a href="#validity" title="Validity">Validity</a>" : <i><a href="validity.md">Validity</a></i>,
        "<a href="#validitynotbefore" title="ValidityNotBefore">ValidityNotBefore</a>" : <i><a href="validity.md">Validity</a></i>,
    }
}
</pre>

### YAML

<pre>
Type: AWS::ACMPCA::Certificate
Properties:
    <a href="#apipassthrough" title="ApiPassthrough">ApiPassthrough</a>: <i><a href="apipassthrough.md">ApiPassthrough</a></i>
    <a href="#certificateauthorityarn" title="CertificateAuthorityArn">CertificateAuthorityArn</a>: <i>String</i>
    <a href="#certificatesigningrequest" title="CertificateSigningRequest">CertificateSigningRequest</a>: <i>String</i>
    <a href="#signingalgorithm" title="SigningAlgorithm">SigningAlgorithm</a>: <i>String</i>
    <a href="#templatearn" title="TemplateArn">TemplateArn</a>: <i>String</i>
    <a href="#validity" title="Validity">Validity</a>: <i><a href="validity.md">Validity</a></i>
    <a href="#validitynotbefore" title="ValidityNotBefore">ValidityNotBefore</a>: <i><a href="validity.md">Validity</a></i>
</pre>

## Properties

#### ApiPassthrough

Structure that specifies fields to be overridden in a certificate at the time of issuance. These requires an API Passthrough template be used or they will be ignored.

_Required_: No

_Type_: <a href="apipassthrough.md">ApiPassthrough</a>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### CertificateAuthorityArn

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### CertificateSigningRequest

The certificate signing request (CSR) for the Certificate.

_Required_: Yes

_Type_: String

_Minimum Length_: <code>1</code>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### SigningAlgorithm

The name of the algorithm that will be used to sign the Certificate.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### TemplateArn

_Required_: No

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Validity

Validity for a certificate.

_Required_: Yes

_Type_: <a href="validity.md">Validity</a>

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### ValidityNotBefore

_Required_: No

_Type_: <a href="validity.md">Validity</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

## Return Values

### Fn::GetAtt

The `Fn::GetAtt` intrinsic function returns a value for a specified attribute of this type. The following are the available attributes and sample return values.

For more information about using the `Fn::GetAtt` intrinsic function, see [Fn::GetAtt](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html).

#### Arn

Returns the <code>Arn</code> value.

#### Certificate

The issued certificate in base 64 PEM-encoded format.
