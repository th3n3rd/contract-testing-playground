#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &> /dev/null && pwd)

NAMESPACE=${1:-"ecommerce"}
DOMAIN=${2:-"ecommerce.k8s"}

echo "Creating $NAMESPACE namespace (if does not exist)"
kubectl create ns "$NAMESPACE" || true

echo "Deploying RabbitMQ onto $NAMESPACE namespace"
helm status -n "$NAMESPACE" message-queue > /dev/null 2>&1 || helm install -n "$NAMESPACE" message-queue bitnami/rabbitmq \
    --set persistence.enabled=false \
    --set auth.username=ecommerce \
    --set auth.password=ecommerce \
    --set ingress.enabled=true \
    --set ingress.hostname="message-queue.$DOMAIN" \
    --set ingress.annotations."kubernetes\.io/ingress\.class"=nginx

kubectl -n "$NAMESPACE" rollout status "statefulset/message-queue-rabbitmq" --timeout=240s
