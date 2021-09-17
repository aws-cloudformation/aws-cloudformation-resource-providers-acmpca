# AWS::ACMPCA::Certificate PolicyQualifierInfo

Structure that contains X.509 Policy qualifier information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#policyqualifierid" title="PolicyQualifierId">PolicyQualifierId</a>" : <i>String</i>,
    "<a href="#qualifier" title="Qualifier">Qualifier</a>" : <i><a href="qualifier.md">Qualifier</a></i>
}
</pre>

### YAML

<pre>
<a href="#policyqualifierid" title="PolicyQualifierId">PolicyQualifierId</a>: <i>String</i>
<a href="#qualifier" title="Qualifier">Qualifier</a>: <i><a href="qualifier.md">Qualifier</a></i>
</pre>

## Properties

#### PolicyQualifierId

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Qualifier

Structure that contains a X.509 policy qualifier.

_Required_: Yes

_Type_: <a href="qualifier.md">Qualifier</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

