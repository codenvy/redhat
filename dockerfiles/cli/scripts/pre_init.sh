#!/bin/bash
# Copyright (c) 2016 Codenvy, S.A.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#


pre_init() {
  # This must be incremented when BASE is incremented by an API developer
  CHE_CLI_API_VERSION=2

  CHE_PRODUCT_NAME="CODENVY"
  CHE_MINI_PRODUCT_NAME="codenvy"
  CHE_FORMAL_PRODUCT_NAME="Codenvy RedHat"
  CHE_CONTAINER_ROOT="/data"
  CHE_ASSEMBLY_IN_REPO_MODULE_NAME="assembly/assembly-main"
  CHE_ASSEMBLY_IN_REPO="${CHE_ASSEMBLY_IN_REPO_MODULE_NAME}/target/codenvy-onpremises-*/"
  WS_AGENT_IN_REPO_MODULE_NAME="assembly/assembly-wsagent-server"
  WS_AGENT_IN_REPO="${WS_AGENT_IN_REPO_MODULE_NAME}/target/assembly-wsagent-server-*.tar.gz"
  WS_AGENT_ASSEMBLY="ws-agent.tar.gz"
  CHE_SCRIPTS_CONTAINER_SOURCE_DIR="/repo/dockerfiles/cli/scripts"
  CHE_LICENSE=true
  CHE_LICENSE_URL="https://codenvy.com/legal/fair-source/"
  CHE_SERVER_CONTAINER_NAME="${CHE_MINI_PRODUCT_NAME}_${CHE_MINI_PRODUCT_NAME}_1"
  CHE_IMAGE_FULLNAME="codenvy/cli-redhat:<version>"
  CHE_PORT=80
  CODENVY_PORT=$CHE_PORT
  CHE_MIN_RAM=1.5
  CHE_MIN_DISK=100
  CHE_COMPOSE_PROJECT_NAME=$CHE_MINI_PRODUCT_NAME

  ADDITIONAL_MANDATORY_PARAMETERS=""
  ADDITIONAL_OPTIONAL_DOCKER_PARAMETERS="
  -e CODENVY_HOST=<YOUR_HOST>          IP address or hostname where codenvy will serve its users"
  ADDITIONAL_OPTIONAL_DOCKER_MOUNTS=""
  ADDITIONAL_COMMANDS="
  add-node                             Adds a physical node to serve workspaces intto the ${CHE_FORMAL_PRODUCT_NAME} cluster
  list-nodes                           Lists all physical nodes that are part of the ${CHE_FORMAL_PRODUCT_NAME} cluster
  remove-node <ip>                     Removes the physical node from the ${CHE_FORMAL_PRODUCT_NAME} cluster"
  ADDITIONAL_GLOBAL_OPTIONS=""
}
