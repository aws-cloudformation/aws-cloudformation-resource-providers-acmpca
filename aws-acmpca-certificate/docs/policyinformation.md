# AWS::ACMPCA::Certificate PolicyInformation

Structure that contains X.509 Policy information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#certpolicyid" title="CertPolicyId">CertPolicyId</a>" : <i>String</i>,
    "<a href="#policyqualifiers" title="PolicyQualifiers">PolicyQualifiers</a>" : <i>[ <a href="policyqualifierinfo.md">PolicyQualifierInfo</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#certpolicyid" title="CertPolicyId">CertPolicyId</a>: <i>String</i>
<a href="#policyqualifiers" title="PolicyQualifiers">PolicyQualifiers</a>: <i>
      - <a href="policyqualifierinfo.md">PolicyQualifierInfo</a></i>
</pre>

## Properties

#### CertPolicyId

String that contains X.509 ObjectIdentifier information.

_Required_: Yes

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### PolicyQualifiers

_Required_: No

_Type_: List of <a href="policyqualifierinfo.md">PolicyQualifierInfo</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

