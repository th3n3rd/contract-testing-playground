#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"

echo "Navigating to the root of the project"
cd "$SCRIPT_DIR/.."

pushd "system/e2e"
    ./scripts/test.sh
popd
