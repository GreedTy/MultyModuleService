plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    jacoco
}

group = "com.micro.server.api"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(project(":dao"))
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

// jacoco {
//    toolVersion = "0.8.7"
//    reportsDirectory.set(file("$buildDir/jacoco/report"))
// }
//
// tasks.test {
//    useJUnitPlatform()
//    extensions.configure(JacocoTaskExtension::class) {
//        ignoreFailures = true
//        isEnabled = true
//        excludes = listOf(":main")
//        isIncludeNoLocationClasses = false
//        sessionId = "<auto-generated value>"
//        isDumpOnExit = true
//        output = JacocoTaskExtension.Output.FILE
//        isJmx = false
//    }
//    finalizedBy("jacocoTestReport")
// }
//
// tasks.jacocoTestReport {
//    reports {
//        xml.required.set(false)
//        csv.required.set(true)
//        html.required.set(false)
//    }
//    finalizedBy("jacocoTestCoverageVerification")
// }
//
// tasks.jacocoTestCoverageVerification {
//    violationRules {
//        rule {
//            element = "CLASS"
//
//            limit {
//                counter = "INSTRUCTION"
//                value = "COVEREDRATIO"
//                // minimum = "0.50".toBigDecimal()
//            }
//
//            excludes = listOf(
//                "*.Kotlin*"
//            )
//        }
//    }
// }

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = true
jar.enabled = false
