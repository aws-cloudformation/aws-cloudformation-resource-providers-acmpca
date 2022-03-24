# AWS::ACMPCA::CertificateAuthority CrlConfiguration

Your certificate authority can create and maintain a certificate revocation list (CRL). A CRL contains information about certificates that have been revoked.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#enabled" title="Enabled">Enabled</a>" : <i>Boolean</i>,
    "<a href="#expirationindays" title="ExpirationInDays">ExpirationInDays</a>" : <i>Integer</i>,
    "<a href="#customcname" title="CustomCname">CustomCname</a>" : <i>String</i>,
    "<a href="#s3bucketname" title="S3BucketName">S3BucketName</a>" : <i>String</i>,
    "<a href="#s3objectacl" title="S3ObjectAcl">S3ObjectAcl</a>" : <i>String</i>
}
</pre>

### YAML

<pre>
<a href="#enabled" title="Enabled">Enabled</a>: <i>Boolean</i>
<a href="#expirationindays" title="ExpirationInDays">ExpirationInDays</a>: <i>Integer</i>
<a href="#customcname" title="CustomCname">CustomCname</a>: <i>String</i>
<a href="#s3bucketname" title="S3BucketName">S3BucketName</a>: <i>String</i>
<a href="#s3objectacl" title="S3ObjectAcl">S3ObjectAcl</a>: <i>String</i>
</pre>

## Properties

#### Enabled

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### ExpirationInDays

_Required_: No

_Type_: Integer

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CustomCname

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### S3BucketName

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### S3ObjectAcl

_Required_: No

_Type_: String

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)
