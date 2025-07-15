#!/bin/bash

# List of all your microservice directories
services=(
  "auth-service"
  "user-service"
  "admin-service"
  "administrator-service"
  "api-gateway"
  "discovery-server"
  "hotel-booking-service"
  "springAdmin-server"
)

echo "🔁 Building all microservices..."

for service in "${services[@]}"
do
  echo ""
  echo "📦 Building: $service"
  cd "$service" || exit
  mvn clean package spring-boot:repackage
  if [ $? -ne 0 ]; then
    echo "❌ Build failed for $service. Stopping."
    exit 1
  fi
  cd ..
done

echo ""
echo "✅ All services built successfully!"
