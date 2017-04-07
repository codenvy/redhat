class addon {
  # creating redhat.properties
  file { "/opt/codenvy/config/codenvy/conf/redhat.properties":
    ensure  => "present",
    content => template("addon/redhat.properties.erb"),
    mode    => "644",
  }
}
