#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

"${SCRIPT_DIR}/sync-server-mod.sh"
"${SCRIPT_DIR}/sync-client-mod.sh"
