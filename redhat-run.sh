#!/bin/bash

# See: https://sipb.mit.edu/doc/safe-shell/
set -e
set -u

. ./build.include
init "$@"

docker_exec run -it --rm  ${DOCKER_RUN_OPTIONS}  \
         -v /var/run/docker.sock:/var/run/docker.sock \
         -v "$HOME/.redhat/data:/data" \
         -v "$PWD":/repo \
         codenvy/cli-redhat:nightly start --skip:scripts
