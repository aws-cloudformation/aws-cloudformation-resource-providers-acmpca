# AWS::ACMPCA::Certificate ApiPassthrough

Structure that specifies fields to be overridden in a certificate at the time of issuance. These requires an API Passthrough template be used or they will be ignored.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#extensions" title="Extensions">Extensions</a>" : <i><a href="extensions.md">Extensions</a></i>,
    "<a href="#subject" title="Subject">Subject</a>" : <i><a href="subject.md">Subject</a></i>
}
</pre>

### YAML

<pre>
<a href="#extensions" title="Extensions">Extensions</a>: <i><a href="extensions.md">Extensions</a></i>
<a href="#subject" title="Subject">Subject</a>: <i><a href="subject.md">Subject</a></i>
</pre>

## Properties

#### Extensions

Structure that contains X.500 extensions for a Certificate.

_Required_: No

_Type_: <a href="extensions.md">Extensions</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### Subject

_Required_: No

_Type_: <a href="subject.md">Subject</a>

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

