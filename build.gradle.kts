import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta13"
}

group = "org.example"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    mavenCentral()
}

dependencies {
    implementation ("io.github.spair:imgui-java-binding:1.86.10")
    implementation ("io.github.spair:imgui-java-lwjgl3:1.86.10")

    implementation ("io.github.spair:imgui-java-natives-windows:1.86.10")
    implementation ("io.github.spair:imgui-java-natives-linux:1.86.10")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")
}

tasks.test {
    useJUnitPlatform()
}


tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("all")
    mergeServiceFiles()

    exclude("org.lwjgl.lwjgl:.*")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
