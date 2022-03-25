# AWS::ACMPCA::Certificate Subject

Structure that contains X.500 distinguished name information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#country" title="Country">Country</a>" : <i>String</i>,
    "<a href="#organization" title="Organization">Organization</a>" : <i>String</i>,
    "<a href="#organizationalunit" title="OrganizationalUnit">OrganizationalUnit</a>" : <i>String</i>,
    "<a href="#distinguishednamequalifier" title="DistinguishedNameQualifier">DistinguishedNameQualifier</a>" : <i>String</i>,
    "<a href="#state" title="State">State</a>" : <i>String</i>,
    "<a href="#commonname" title="CommonName">CommonName</a>" : <i>String</i>,
    "<a href="#serialnumber" title="SerialNumber">SerialNumber</a>" : <i>String</i>,
    "<a href="#locality" title="Locality">Locality</a>" : <i>String</i>,
    "<a href="#title" title="Title">Title</a>" : <i>String</i>,
    "<a href="#surname" title="Surname">Surname</a>" : <i>String</i>,
    "<a href="#givenname" title="GivenName">GivenName</a>" : <i>String</i>,
    "<a href="#initials" title="Initials">Initials</a>" : <i>String</i>,
    "<a href="#pseudonym" title="Pseudonym">Pseudonym</a>" : <i>String</i>,
    "<a href="#generationqualifier" title="GenerationQualifier">GenerationQualifier</a>" : <i>String</i>,
    "<a href="#customattributes" title="CustomAttributes">CustomAttributes</a>" : <i>[ <a href="customattribute.md">CustomAttribute</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#country" title="Country">Country</a>: <i>String</i>
<a href="#organization" title="Organization">Organization</a>: <i>String</i>
<a href="#organizationalunit" title="OrganizationalUnit">OrganizationalUnit</a>: <i>String</i>
<a href="#distinguishednamequalifier" title="DistinguishedNameQualifier">DistinguishedNameQualifier</a>: <i>String</i>
<a href="#state" title="State">State</a>: <i>String</i>
<a href="#commonname" title="CommonName">CommonName</a>: <i>String</i>
<a href="#serialnumber" title="SerialNumber">SerialNumber</a>: <i>String</i>
<a href="#locality" title="Locality">Locality</a>: <i>String</i>
<a href="#title" title="Title">Title</a>: <i>String</i>
<a href="#surname" title="Surname">Surname</a>: <i>String</i>
<a href="#givenname" title="GivenName">GivenName</a>: <i>String</i>
<a href="#initials" title="Initials">Initials</a>: <i>String</i>
<a href="#pseudonym" title="Pseudonym">Pseudonym</a>: <i>String</i>
<a href="#generationqualifier" title="GenerationQualifier">GenerationQualifier</a>: <i>String</i>
<a href="#customattributes" title="CustomAttributes">CustomAttributes</a>: <i>
      - <a href="customattribute.md">CustomAttribute</a></i>
</pre>

## Properties

#### Country

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Organization

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### OrganizationalUnit

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### DistinguishedNameQualifier

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### State

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CommonName

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### SerialNumber

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Locality

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Title

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Surname

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### GivenName

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Initials

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Pseudonym

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### GenerationQualifier

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CustomAttributes

Array of X.500 attribute type and value. CustomAttributes cannot be used along with pre-defined attributes.

_Required_: No

_Type_: List of <a href="customattribute.md">CustomAttribute</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)
