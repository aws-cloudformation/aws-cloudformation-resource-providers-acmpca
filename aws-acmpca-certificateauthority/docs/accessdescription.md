# AWS::ACMPCA::CertificateAuthority AccessDescription

Structure that contains X.509 AccessDescription information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#accessmethod" title="AccessMethod">AccessMethod</a>" : <i><a href="accessmethod.md">AccessMethod</a></i>,
    "<a href="#accesslocation" title="AccessLocation">AccessLocation</a>" : <i><a href="generalname.md">GeneralName</a></i>
}
</pre>

### YAML

<pre>
<a href="#accessmethod" title="AccessMethod">AccessMethod</a>: <i><a href="accessmethod.md">AccessMethod</a></i>
<a href="#accesslocation" title="AccessLocation">AccessLocation</a>: <i><a href="generalname.md">GeneralName</a></i>
</pre>

## Properties

#### AccessMethod

Structure that contains X.509 AccessMethod information. Assign one and ONLY one field.

_Required_: Yes

_Type_: <a href="accessmethod.md">AccessMethod</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### AccessLocation

Structure that contains X.509 GeneralName information. Assign one and ONLY one field.

_Required_: Yes

_Type_: <a href="generalname.md">GeneralName</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

