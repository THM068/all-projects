plugins {
    id("groovy")
    id("io.micronaut.application") version "4.5.0"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.aot") version "4.5.0"
}

version = "0.1"
group = "fashion"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.micronaut.groovy:micronaut-runtime-groovy")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("io.micronaut:micronaut-http-validation")
    compileOnly("io.micronaut.serde:micronaut-serde-processor")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass = "fashion.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("fashion.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


