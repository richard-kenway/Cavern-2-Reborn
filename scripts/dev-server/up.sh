#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

ensure_server_layout
if [[ -f "${BUILD_JAR}" ]]; then
    cp "${BUILD_JAR}" "${SERVER_MOD_JAR}"
fi
compose up -d --build dev-server
echo "Dev server started on localhost:${DEV_SERVER_PORT}. Tail logs with scripts/dev-server/logs.sh"
