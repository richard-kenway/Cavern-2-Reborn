#!/usr/bin/env bash
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/_common.sh"

ensure_server_layout

if [[ -f "${SERVER_RUN_DIR}/run.sh" ]] && [[ -f "${SERVER_VERSION_FILE}" ]] && [[ "$(cat "${SERVER_VERSION_FILE}")" == "${NEO_VERSION}" ]]; then
    exit 0
fi

VERSION_MANIFEST_URL="https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"
VERSION_JSON_URL="$(
    curl -fsSL "${VERSION_MANIFEST_URL}" | python3 -c '
import json
import sys

target = sys.argv[1]
manifest = json.load(sys.stdin)
for version in manifest["versions"]:
    if version["id"] == target:
        print(version["url"])
        break
else:
    raise SystemExit(f"Version {target} not found in Mojang manifest")
' "${MINECRAFT_VERSION}"
)"

SERVER_DOWNLOAD_INFO="$(
    curl -fsSL "${VERSION_JSON_URL}" | python3 -c '
import json
import sys

manifest = json.load(sys.stdin)
server = manifest["downloads"]["server"]
server_mappings = manifest["downloads"]["server_mappings"]
print(server["url"])
print(server["sha1"])
print(server_mappings["url"])
print(server_mappings["sha1"])
'
)"

SERVER_DOWNLOAD_URL="$(printf '%s\n' "${SERVER_DOWNLOAD_INFO}" | sed -n '1p')"
SERVER_DOWNLOAD_SHA1="$(printf '%s\n' "${SERVER_DOWNLOAD_INFO}" | sed -n '2p')"
SERVER_MAPPINGS_URL="$(printf '%s\n' "${SERVER_DOWNLOAD_INFO}" | sed -n '3p')"
SERVER_MAPPINGS_SHA1="$(printf '%s\n' "${SERVER_DOWNLOAD_INFO}" | sed -n '4p')"

rm -f "${MINECRAFT_SERVER_JAR}"
curl -fsSL "${SERVER_DOWNLOAD_URL}" -o "${MINECRAFT_SERVER_JAR}"

ACTUAL_SERVER_SHA1="$(sha1sum "${MINECRAFT_SERVER_JAR}" | awk '{print $1}')"
if [[ "${ACTUAL_SERVER_SHA1}" != "${SERVER_DOWNLOAD_SHA1}" ]]; then
    echo "Downloaded minecraft server jar has unexpected sha1: ${ACTUAL_SERVER_SHA1}" >&2
    exit 1
fi

rm -f "${INSTALLER_JAR}"
curl -fsSL "${INSTALLER_URL}" -o "${INSTALLER_JAR}"

run_installer() {
    (
        cd "${SERVER_RUN_DIR}"
        java -jar "${INSTALLER_JAR}" --installServer
    )
}

if ! run_installer; then
    SERVER_LIBRARY_DIR="$(find "${SERVER_RUN_DIR}/libraries/net/minecraft/server" -maxdepth 1 -mindepth 1 -type d -name "${MINECRAFT_VERSION}-*" | head -n1 || true)"
    if [[ -z "${SERVER_LIBRARY_DIR}" ]]; then
        echo "NeoForge installer failed before creating the minecraft server library directory." >&2
        exit 1
    fi

    SERVER_LIBRARY_VERSION="$(basename "${SERVER_LIBRARY_DIR}")"
    SERVER_MAPPINGS_FILE="${SERVER_LIBRARY_DIR}/server-${SERVER_LIBRARY_VERSION}-mappings.txt"

    rm -f "${SERVER_MAPPINGS_FILE}"
    curl -fsSL "${SERVER_MAPPINGS_URL}" -o "${SERVER_MAPPINGS_FILE}"

    ACTUAL_MAPPINGS_SHA1="$(sha1sum "${SERVER_MAPPINGS_FILE}" | awk '{print $1}')"
    if [[ "${ACTUAL_MAPPINGS_SHA1}" != "${SERVER_MAPPINGS_SHA1}" ]]; then
        echo "Downloaded server mappings have unexpected sha1: ${ACTUAL_MAPPINGS_SHA1}" >&2
        exit 1
    fi

    run_installer
fi

printf '%s\n' "${NEO_VERSION}" > "${SERVER_VERSION_FILE}"
rm -f "${INSTALLER_JAR}"
