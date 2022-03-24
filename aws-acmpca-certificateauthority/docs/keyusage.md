# AWS::ACMPCA::CertificateAuthority KeyUsage

Structure that contains X.509 KeyUsage information.

## Syntax

To declare this entity in your AWS CloudFormation template, use the following syntax:

### JSON

<pre>
{
    "<a href="#digitalsignature" title="DigitalSignature">DigitalSignature</a>" : <i>Boolean</i>,
    "<a href="#nonrepudiation" title="NonRepudiation">NonRepudiation</a>" : <i>Boolean</i>,
    "<a href="#keyencipherment" title="KeyEncipherment">KeyEncipherment</a>" : <i>Boolean</i>,
    "<a href="#dataencipherment" title="DataEncipherment">DataEncipherment</a>" : <i>Boolean</i>,
    "<a href="#keyagreement" title="KeyAgreement">KeyAgreement</a>" : <i>Boolean</i>,
    "<a href="#keycertsign" title="KeyCertSign">KeyCertSign</a>" : <i>Boolean</i>,
    "<a href="#crlsign" title="CRLSign">CRLSign</a>" : <i>Boolean</i>,
    "<a href="#encipheronly" title="EncipherOnly">EncipherOnly</a>" : <i>Boolean</i>,
    "<a href="#decipheronly" title="DecipherOnly">DecipherOnly</a>" : <i>Boolean</i>
}
</pre>

### YAML

<pre>
<a href="#digitalsignature" title="DigitalSignature">DigitalSignature</a>: <i>Boolean</i>
<a href="#nonrepudiation" title="NonRepudiation">NonRepudiation</a>: <i>Boolean</i>
<a href="#keyencipherment" title="KeyEncipherment">KeyEncipherment</a>: <i>Boolean</i>
<a href="#dataencipherment" title="DataEncipherment">DataEncipherment</a>: <i>Boolean</i>
<a href="#keyagreement" title="KeyAgreement">KeyAgreement</a>: <i>Boolean</i>
<a href="#keycertsign" title="KeyCertSign">KeyCertSign</a>: <i>Boolean</i>
<a href="#crlsign" title="CRLSign">CRLSign</a>: <i>Boolean</i>
<a href="#encipheronly" title="EncipherOnly">EncipherOnly</a>: <i>Boolean</i>
<a href="#decipheronly" title="DecipherOnly">DecipherOnly</a>: <i>Boolean</i>
</pre>

## Properties

#### DigitalSignature

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### NonRepudiation

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### KeyEncipherment

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### DataEncipherment

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### KeyAgreement

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### KeyCertSign

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### CRLSign

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### EncipherOnly

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)

#### DecipherOnly

_Required_: No

_Type_: Boolean

_Update requires_: [No interruption](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-update-behaviors.html#update-no-interrupt)
