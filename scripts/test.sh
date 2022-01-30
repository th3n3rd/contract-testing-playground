#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"

echo "Navigating to the root of the project"
cd "$SCRIPT_DIR/.."

pushd "apps/cart"
    ./scripts/test.sh
popd

pushd "apps/catalogue"
    ./scripts/test.sh
popd
