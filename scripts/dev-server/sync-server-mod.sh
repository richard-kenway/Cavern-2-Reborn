#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

if [[ ! -f "${BUILD_JAR}" ]]; then
    echo "Build artifact not found: ${BUILD_JAR}" >&2
    echo "Run scripts/dev-server/build-server-mod.sh or scripts/dev-server/build-mods.sh first." >&2
    exit 1
fi

ensure_server_layout
cp "${BUILD_JAR}" "${SERVER_MOD_JAR}"
echo "Synced ${SERVER_MOD_JAR}"
