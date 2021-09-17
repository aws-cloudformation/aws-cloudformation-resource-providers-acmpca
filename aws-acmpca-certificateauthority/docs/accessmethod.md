# AWS::ACMPCA::CertificateAuthority AccessMethod

Structure that contains X.509 AccessMethod information. Assign one and ONLY one field.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#customobjectidentifier" title="CustomObjectIdentifier">CustomObjectIdentifier</a>" : <i>String</i>,
    "<a href="#accessmethodtype" title="AccessMethodType">AccessMethodType</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#customobjectidentifier" title="CustomObjectIdentifier">CustomObjectIdentifier</a>: <i>String</i>
<a href="#accessmethodtype" title="AccessMethodType">AccessMethodType</a>: <i>String</i>
</pre>

## Properties

#### CustomObjectIdentifier

String that contains X.509 ObjectIdentifier information.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AccessMethodType

Pre-defined enum string for X.509 AccessMethod ObjectIdentifiers.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

