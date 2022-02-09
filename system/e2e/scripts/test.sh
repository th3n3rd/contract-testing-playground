#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
BASE_URL="http://api.ecommerce.k8s"

cd "$SCRIPT_DIR/.."

echo "Running e2e tests against $BASE_URL"
./mvnw clean test -DbaseUrl="$BASE_URL"
