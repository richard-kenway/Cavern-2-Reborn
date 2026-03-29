#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

if [[ ! -f "${BUILD_JAR}" ]]; then
    echo "Build artifact not found: ${BUILD_JAR}" >&2
    echo "Run scripts/dev-server/build-client-mod.sh first." >&2
    exit 1
fi

mkdir -p "${CLIENT_MOD_DIR}"
cp "${BUILD_JAR}" "${CLIENT_MOD_JAR}"
echo "Synced ${CLIENT_MOD_JAR}"
