plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.10"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(":utilities"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.10")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
    }
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClassName = "MainKt"
}
