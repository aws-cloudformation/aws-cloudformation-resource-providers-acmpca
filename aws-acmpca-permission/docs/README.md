# AWS::ACMPCA::Permission

Permission set on private certificate authority

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "Type" : "AWS::ACMPCA::Permission",
    "Properties" : {
        "<a href="#actions" title="Actions">Actions</a>" : <i>[ String, ... ]</i>,
        "<a href="#certificateauthorityarn" title="CertificateAuthorityArn">CertificateAuthorityArn</a>" : <i>String</i>,
        "<a href="#principal" title="Principal">Principal</a>" : <i>String</i>,
        "<a href="#sourceaccount" title="SourceAccount">SourceAccount</a>" : <i>String</i>
    }
}
</pre>

### YAML

<pre>
Type: AWS::ACMPCA::Permission
Properties:
    <a href="#actions" title="Actions">Actions</a>: <i>
      - String</i>
    <a href="#certificateauthorityarn" title="CertificateAuthorityArn">CertificateAuthorityArn</a>: <i>String</i>
    <a href="#principal" title="Principal">Principal</a>: <i>String</i>
    <a href="#sourceaccount" title="SourceAccount">SourceAccount</a>: <i>String</i>
</pre>

## Properties

#### Actions

The actions that the specified AWS service principal can use. Actions IssueCertificate, GetCertificate and ListPermissions must be provided.

_Required_: Yes

_Type_: List of String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### CertificateAuthorityArn

The Amazon Resource Name (ARN) of the Private Certificate Authority that grants the permission.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### Principal

The AWS service or identity that receives the permission. At this time, the only valid principal is acm.amazonaws.com.

_Required_: Yes

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)

#### SourceAccount

The ID of the calling account.

_Required_: No

_Type_: String

_Update requires_: [Replacement](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-replacement)
