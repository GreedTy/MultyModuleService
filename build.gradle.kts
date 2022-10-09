import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("org.openapi.generator") version "5.4.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

allprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }

//    configure<KtlintExtension> {
//        debug.set(true)
//        version.set("0.45.1")
//        verbose.set(true)
//        outputToConsole.set(true)
//        outputColorName.set("RED")
//        //ignoreFailures.set(true)
//        additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
//        disabledRules.set(
//            kotlin.collections.setOf(
//                "import-ordering",
//                "no-wildcard-imports",
//                "filename",
//                "comment-spacing"
//            )
//        )
//        filter {
//            exclude { tree ->
//                kotlin.collections.listOf("/build/generated/").any {
//                    tree.file.path.contains(it)
//                }
//            }
//        }
//    }
}

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.openapi.generator")
    }

    group = "com.micro.one"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.21")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.7.0")
        implementation("org.springframework.security:spring-security-oauth2-jose:5.7.1")
        implementation("org.springframework.boot:spring-boot-starter-validation:2.7.0")
        implementation("org.springframework:spring-tx:5.3.22")

        testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0") {
            exclude(module = "mockito-core")
        }
        testImplementation("com.ninja-squad:springmockk:3.1.1")
        testImplementation("io.kotest:kotest-runner-junit5:5.3.0")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")
        testImplementation("org.springframework.security:spring-security-test:5.6.2")
    }

    extra["kotlin-coroutines.version"] = "1.6.1"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}
