#!/bin/bash

set -x

NAMESPACE=${1:-"ecommerce"}

stern -n $NAMESPACE -l app.kubernetes.io/part-of=ecommerce --template '{{color .ContainerColor .ContainerName}} {{.Message}}{{"\n"}}'
