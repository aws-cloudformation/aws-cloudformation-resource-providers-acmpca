# AWS::ACMPCA::Certificate Extensions

Structure that contains X.500 extensions for a Certificate.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#certificatepolicies" title="CertificatePolicies">CertificatePolicies</a>" : <i>[ <a href="policyinformation.md">PolicyInformation</a>, ... ]</i>,
    "<a href="#extendedkeyusage" title="ExtendedKeyUsage">ExtendedKeyUsage</a>" : <i>[ [ <a href="extendedkeyusage.md">ExtendedKeyUsage</a>, ... ], ... ]</i>,
    "<a href="#keyusage" title="KeyUsage">KeyUsage</a>" : <i><a href="keyusage.md">KeyUsage</a></i>,
    "<a href="#subjectalternativenames" title="SubjectAlternativeNames">SubjectAlternativeNames</a>" : <i>[ <a href="generalname.md">GeneralName</a>, ... ]</i>,
    "<a href="#customextensions" title="CustomExtensions">CustomExtensions</a>" : <i>[ <a href="customextension.md">CustomExtension</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#certificatepolicies" title="CertificatePolicies">CertificatePolicies</a>: <i>
      - <a href="policyinformation.md">PolicyInformation</a></i>
<a href="#extendedkeyusage" title="ExtendedKeyUsage">ExtendedKeyUsage</a>: <i>
      -
      - <a href="extendedkeyusage.md">ExtendedKeyUsage</a></i>
<a href="#keyusage" title="KeyUsage">KeyUsage</a>: <i><a href="keyusage.md">KeyUsage</a></i>
<a href="#subjectalternativenames" title="SubjectAlternativeNames">SubjectAlternativeNames</a>: <i>
      - <a href="generalname.md">GeneralName</a></i>
<a href="#customextensions" title="CustomExtensions">CustomExtensions</a>: <i>
      - <a href="customextension.md">CustomExtension</a></i>
</pre>

## Properties

#### CertificatePolicies

_Required_: No

_Type_: List of <a href="policyinformation.md">PolicyInformation</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### ExtendedKeyUsage

_Required_: No

_Type_: List of List of <a href="extendedkeyusage.md">ExtendedKeyUsage</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### KeyUsage

Structure that contains X.509 KeyUsage information.

_Required_: No

_Type_: <a href="keyusage.md">KeyUsage</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### SubjectAlternativeNames

_Required_: No

_Type_: List of <a href="generalname.md">GeneralName</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CustomExtensions

Array of X.509 extensions for a certificate.

_Required_: No

_Type_: List of <a href="customextension.md">CustomExtension</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)
