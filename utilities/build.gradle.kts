plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.11"
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:27.0-jre")
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
