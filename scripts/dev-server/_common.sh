#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
SERVER_RUN_DIR="${ROOT_DIR}/dev-server/server"
CAVERN_WORLD_DIR="${SERVER_RUN_DIR}/world/dimensions/cavernreborn/cavern"
WORLD_DIR="${SERVER_RUN_DIR}/world"
DEV_SERVER_PORT="${DEV_SERVER_PORT:-25585}"
CLIENT_MOD_DIR="${CLIENT_MOD_DIR:-/path/to/client/mods}"
CLIENT_MOD_JAR="${CLIENT_MOD_DIR}/cavernreborn-0.1.0.jar"
BUILD_JAR="${ROOT_DIR}/app-neoforge/build/libs/cavernreborn-0.1.0.jar"
SERVER_MOD_DIR="${SERVER_RUN_DIR}/mods"
SERVER_MOD_JAR="${SERVER_MOD_DIR}/cavernreborn-0.1.0.jar"
MINECRAFT_VERSION="$(awk -F= '$1 == "minecraft_version" { print $2 }' "${ROOT_DIR}/gradle.properties")"
NEO_VERSION="$(awk -F= '$1 == "neo_version" { print $2 }' "${ROOT_DIR}/gradle.properties")"
SERVER_VERSION_FILE="${SERVER_RUN_DIR}/.neoforge-version"
INSTALLER_JAR="${SERVER_RUN_DIR}/neoforge-${NEO_VERSION}-installer.jar"
INSTALLER_URL="https://maven.neoforged.net/releases/net/neoforged/neoforge/${NEO_VERSION}/neoforge-${NEO_VERSION}-installer.jar"
MINECRAFT_SERVER_JAR="${SERVER_RUN_DIR}/server.jar"

compose() {
    docker compose "$@"
}

ensure_server_layout() {
    mkdir -p "${SERVER_RUN_DIR}"
    mkdir -p "${SERVER_MOD_DIR}"
    if [[ ! -f "${SERVER_RUN_DIR}/eula.txt" ]]; then
        printf 'eula=true\n' > "${SERVER_RUN_DIR}/eula.txt"
    fi
    if [[ ! -f "${SERVER_RUN_DIR}/server.properties" ]]; then
        cat > "${SERVER_RUN_DIR}/server.properties" <<'EOF'
allow-flight=true
difficulty=peaceful
gamemode=creative
motd=Cavern Reborn Dev Server
online-mode=false
spawn-protection=0
view-distance=12
simulation-distance=8
EOF
    fi
}
