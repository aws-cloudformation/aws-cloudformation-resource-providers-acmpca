# AWS::ACMPCA::Certificate GeneralName

Structure that contains X.509 GeneralName information. Assign one and ONLY one field.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#othername" title="OtherName">OtherName</a>" : <i><a href="othername.md">OtherName</a></i>,
    "<a href="#rfc822name" title="Rfc822Name">Rfc822Name</a>" : <i>String</i>,
    "<a href="#dnsname" title="DnsName">DnsName</a>" : <i>String</i>,
    "<a href="#directoryname" title="DirectoryName">DirectoryName</a>" : <i><a href="subject.md">Subject</a></i>,
    "<a href="#edipartyname" title="EdiPartyName">EdiPartyName</a>" : <i><a href="edipartyname.md">EdiPartyName</a></i>,
    "<a href="#uniformresourceidentifier" title="UniformResourceIdentifier">UniformResourceIdentifier</a>" : <i>String</i>,
    "<a href="#ipaddress" title="IpAddress">IpAddress</a>" : <i>String</i>,
    "<a href="#registeredid" title="RegisteredId">RegisteredId</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#othername" title="OtherName">OtherName</a>: <i><a href="othername.md">OtherName</a></i>
<a href="#rfc822name" title="Rfc822Name">Rfc822Name</a>: <i>String</i>
<a href="#dnsname" title="DnsName">DnsName</a>: <i>String</i>
<a href="#directoryname" title="DirectoryName">DirectoryName</a>: <i><a href="subject.md">Subject</a></i>
<a href="#edipartyname" title="EdiPartyName">EdiPartyName</a>: <i><a href="edipartyname.md">EdiPartyName</a></i>
<a href="#uniformresourceidentifier" title="UniformResourceIdentifier">UniformResourceIdentifier</a>: <i>String</i>
<a href="#ipaddress" title="IpAddress">IpAddress</a>: <i>String</i>
<a href="#registeredid" title="RegisteredId">RegisteredId</a>: <i>String</i>
</pre>

## Properties

#### OtherName

Structure that contains X.509 OtherName information.

_Required_: No

_Type_: <a href="othername.md">OtherName</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Rfc822Name

String that contains X.509 Rfc822Name information.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### DnsName

String that contains X.509 DnsName information.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### DirectoryName

Structure that contains X.500 distinguished name information.

_Required_: No

_Type_: <a href="subject.md">Subject</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### EdiPartyName

Structure that contains X.509 EdiPartyName information.

_Required_: No

_Type_: <a href="edipartyname.md">EdiPartyName</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### UniformResourceIdentifier

String that contains X.509 UniformResourceIdentifier information.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### IpAddress

String that contains X.509 IpAddress information.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### RegisteredId

String that contains X.509 ObjectIdentifier information.

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)
