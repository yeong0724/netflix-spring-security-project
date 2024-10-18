dependencies {
    implementation(project(":netflix-core:core-domain"))
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-commons"))
    implementation(project(":netflix-core:core-service"))
    implementation(project(":netflix-adapters:adapter-http"))
    implementation(project(":netflix-adapters:adapter-persistence"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-batch")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Transaction
    implementation("org.springframework:spring-tx")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
}

val appMainClassName = "com.jinyeong.netflix.NetflixBatchApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}
