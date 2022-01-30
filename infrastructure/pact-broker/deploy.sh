#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &> /dev/null && pwd)

echo "Deploying Pact Broker (backed by SQLite)"
kubectl create ns pact || true
kubectl apply -f "$SCRIPT_DIR/manifest.yaml" -n pact --wait

echo "Pact Broker is ready, opening browser"
open http://localhost:9292
