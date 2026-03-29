#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

compose run --rm gradle ./gradlew --no-daemon test build
"$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/sync-client-mod.sh"
