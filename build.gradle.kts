plugins {
    java
    application
}

application {
    mainClass.set("Main")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "Main"
    }
}