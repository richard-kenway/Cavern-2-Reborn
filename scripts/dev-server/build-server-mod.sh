#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "${SCRIPT_DIR}/_common.sh"

compose run --rm gradle ./gradlew --no-daemon test build
"${SCRIPT_DIR}/sync-server-mod.sh"
