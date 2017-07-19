# Copyright (c) 2016 Codenvy, S.A.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   Tyler Jewell - Initial Implementation
#
# Contains the Codenvy redhat CLI.
#
# build:
#   docker build -t codenvy/cli-redhat .
# 
# use:
#    docker run -v $(pwd):/codenvy codenvy/cli-redhat [command]

FROM codenvy/cli:5.15.0
RUN rm -rf /version && mkdir /scripts/codenvy
COPY scripts /scripts/
COPY version /version/

