#!/usr/bin/env bash
set -euo pipefail

./gradlew --no-daemon test
./gradlew --no-daemon :app-neoforge:runGameTestServer
./gradlew --no-daemon build
