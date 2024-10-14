dependencies {
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-port"))
    implementation(project(":netflix-core:core-domain"))
    implementation(project(":netflix-adapters:adapter-http"))
    implementation(project(":netflix-adapters:adapter-persistence"))
    implementation(project(":netflix-adapters:adapter-redis"))
    implementation(project(":netflix-commons"))

    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-context")
}