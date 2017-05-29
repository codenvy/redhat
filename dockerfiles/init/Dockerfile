# Copyright (c) 2016 Codenvy, S.A.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Initializes an empty directory with the templates needed to configure and run Codenvy.
#
# build:
#   docker build -t codenvy/init-redhat:<version> .
#
# use (to copy files onto folder):
#   docker run -v $(pwd):/codenvy codenvy/init-redhat:<version>
#
# use (to run puppet config):
#   docker run <puppet-mounts> --entrypoint=/usr/bin/puppet codenvy/init-redhat:<version> apply <puppet-apply-options>


FROM codenvy/init:5.11.2
COPY /addon/addon.pp /etc/puppet/manifests/
COPY /addon/modules/ /etc/puppet/addon/
