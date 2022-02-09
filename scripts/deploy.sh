#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
NAMESPACE=${1:-"ecommerce"}
DOMAIN=${2:-"ecommerce.k8s"}
APPS="cart catalogue orders"

echo "Navigating to the root of the project"
cd "$SCRIPT_DIR/.."

echo "Creating $NAMESPACE namespace (if does not exist)"
kubectl create ns "$NAMESPACE" || true

for APP in $APPS; do
    echo "Deploying $APP application onto $NAMESPACE namespace"
    pushd "apps/$APP"
        kubectl -n "$NAMESPACE" apply -f deployment/manifest.yaml
        kubectl -n "$NAMESPACE" rollout status "deploy/$APP" --timeout=120s
    popd
done

echo "Deploying ingress onto $NAMESPACE namespace"
cat <<EOF | kubectl -n "$NAMESPACE" apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ecommerce-ingress
  labels:
    app.kubernetes.io/part-of: ecommerce
  annotations:
    kubernetes.io/ingress.class: "nginx"
    nginx.ingress.kubernetes.io/rewrite-target: /\$1
spec:
  rules:
    - host: "api.$DOMAIN"
      http:
        paths:
          - path: /(carts.*)
            pathType: ImplementationSpecific
            backend:
              service:
                name: cart
                port:
                  number: 80
          - path: /(products.*)
            pathType: ImplementationSpecific
            backend:
              service:
                name: catalogue
                port:
                  number: 80
EOF
