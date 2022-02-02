#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"

cd "$SCRIPT_DIR/.."

MICROSERVICE=$(basename "$PWD")

echo "Running tests for $MICROSERVICE and publishing verified contracts results"
./mvnw clean test -Dpact.verifier.publishResults

echo "Publishing consumer contracts"
./mvnw pact:publish
