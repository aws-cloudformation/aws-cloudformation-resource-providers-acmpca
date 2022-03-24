## The CloudFormation Resource Provider Package For Amazon Certificate Manager Private Certificate Authority.

This repository contains AWS-owned resource providers for the `AWS::ACMPCA::*` namespace.

CloudFormation Documentation: https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/AWS_ACMPCA.html

ACM-PCA Documentation: https://docs.aws.amazon.com/acm-pca/latest/userguide/PcaWelcome.html

## Development

### Environment Setup

Follow the instructions on [Cloudformation Environment setup](https://docs.aws.amazon.com/cloudformation-cli/latest/userguide/resource-type-setup.html),
but the general instructions are:

1. Install `java8`, `maven`, `python3`, `pip3` and `awscli` for your platform
2. Install the cloudformation cli and java plugin using pip:

```
$ pip3 install cloudformation-cli
$ pip3 install cloudformation-cli-java-plugin
```

If you prefer, you could also run this under a virtual environment:

```
$ python3 -m venv cli
$ source cli/bin/activate
$ pip3 install cloudformation-cli
$ pip3 install cloudformation-cli-java-plugin
```

### Building the resource

Before running the code, make sure to set the environment variable `AWS_REGION`
to the region you want to use:
```
# e.g. setting region to us-east-1
$ export AWS_REGION="us-east-1"
```

Linting is done via [pre-commit](https://pre-commit.com/).

```shell
pre-commit install
```

Manual options are available so you don't have to commit:

```shell
# run all hooks on all files, mirrors what the CI runs
pre-commit run --all-files
# run unit tests and coverage checks
mvn clean verify
```


## Contributing

Please refer to the [contributing guidelines](CONTRIBUTING.md).

## License

This project is licensed under the Apache-2.0 License.
