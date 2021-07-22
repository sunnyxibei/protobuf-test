import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

buildscript {
    repositories {
        maven {
            setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
        }
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.17")
    }
}

plugins {
    application
    java
    kotlin("jvm") version "1.5.21"
    id("com.google.protobuf") version "0.8.17"
}

group = "me.xibeisunny"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src/main/kotlin", "src/main/java"))
        proto {
            // In addition to the default 'src/main/proto'
            srcDir("src/main/proto")
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("com.google.protobuf:protobuf-kotlin:3.17.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.17.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.39.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
            }
        }
    }
}