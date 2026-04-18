#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

if dev_server_running; then
    echo "Stop the dev server before resetting the full world." >&2
    exit 1
fi

remove_repo_path "${WORLD_DIR}"
echo "Removed ${WORLD_DIR}"
