job "java" {
  datacenters = ["us-east-1"]

  type = "service"

  constraint {
    attribute = "$attr.kernel.name"
    value = "linux"
  }

  update {
    stagger = "10s"
    max_parallel = 1
  }

  group "java" {
    count = 1

    task "java" {
      driver = "java"

      config {
        jar_source = "https://s3.amazonaws.com/clstokes-public/bin/nomad-java-job.jar"
        args = "-Djava.io.tmpdir=/opt/"
        # JVM Options are not available as of 0.1.2
      }

      resources {
        cpu = 500
        memory = 256
        network {
          mbits = 10
        }
      }
    }
  }

}
