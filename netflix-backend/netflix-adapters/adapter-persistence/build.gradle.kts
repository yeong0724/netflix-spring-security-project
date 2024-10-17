dependencies {
    implementation(project(":netflix-core:core-port"))
    implementation(project(":netflix-core:core-domain"))
    implementation(project(":netflix-commons"))

    // jpa 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Transaction 의존성
    implementation("org.springframework:spring-tx")

    // flyway 의존성
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    runtimeOnly("com.mysql:mysql-connector-j")
}