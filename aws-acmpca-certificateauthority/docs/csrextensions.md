# AWS::ACMPCA::CertificateAuthority CsrExtensions

Structure that contains CSR pass though extensions information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#keyusage" title="KeyUsage">KeyUsage</a>" : <i><a href="keyusage.md">KeyUsage</a></i>,
    "<a href="#subjectinformationaccess" title="SubjectInformationAccess">SubjectInformationAccess</a>" : <i>[ <a href="accessdescription.md">AccessDescription</a>, ... ]</i>
}
</pre>

### YAML

<pre>
<a href="#keyusage" title="KeyUsage">KeyUsage</a>: <i><a href="keyusage.md">KeyUsage</a></i>
<a href="#subjectinformationaccess" title="SubjectInformationAccess">SubjectInformationAccess</a>: <i>
      - <a href="accessdescription.md">AccessDescription</a></i>
</pre>

## Properties

#### KeyUsage

Structure that contains X.509 KeyUsage information.

_Required_: No

_Type_: <a href="keyusage.md">KeyUsage</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### SubjectInformationAccess

Array of X.509 AccessDescription.

_Required_: No

_Type_: List of <a href="accessdescription.md">AccessDescription</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)
