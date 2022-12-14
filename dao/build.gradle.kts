import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "com.micro.server.dao"
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
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("mysql:mysql-connector-java")
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
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
