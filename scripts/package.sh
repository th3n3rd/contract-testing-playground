#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
APPS="cart catalogue orders"

echo "Navigating to the root of the project"
cd "$SCRIPT_DIR/.."

for APP in $APPS; do
    echo "Packaging $APP application"
    pushd "apps/$APP"
        ./mvnw package -DskipTests
    popd
done
