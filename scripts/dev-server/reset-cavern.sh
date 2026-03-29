#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

rm -rf "${CAVERN_WORLD_DIR}"
echo "Removed ${CAVERN_WORLD_DIR}"
