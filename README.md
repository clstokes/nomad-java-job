# nomad-java-job

A simple Java application that prints JVM options and application
arguments for testing [HashiCorp's](https://hashicorp.com/)
[Nomad scheduler](https://www.nomadproject.io/).

## Usage

### Development

Run `./gradlew build`.

### Running

```
$ java -Xmx512m -XX:+UseG1GC -Djava.io.tmpdir=/opt/java/tmp -jar build/libs/nomad-java-job.jar arg1 arg2
JVM Options:
  -Xmx512m
  -XX:+UseG1GC
  -Djava.io.tmpdir=/opt/java/tmp
App arguments:
  arg1
  arg2

CTRL+C to quit...
^CShutdown hook completed.
$
```

### Nomad Job Definition

```
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

```
