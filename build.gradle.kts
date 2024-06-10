plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.jet.client.Main"
        )
    }
}

repositories {
    mavenCentral()
}

object Versions {
    const val gson = "2.8.9"
    const val lombok = "1.18.32"
    const val junitBom = "5.10.0"
    const val junitJupiter = "5.10.0-RC2"
}

dependencies {
    implementation ("com.google.code.gson:gson:${Versions.gson}")
    compileOnly("org.projectlombok:lombok:${Versions.lombok}")
    annotationProcessor("org.projectlombok:lombok:${Versions.lombok}")
    testImplementation(platform("org.junit:junit-bom:${Versions.junitBom}"))
    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.junitJupiter}")
}

tasks.test {
    useJUnitPlatform()
}