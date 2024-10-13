dependencies {
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-port"))
    implementation(project(":netflix-adapters:adapter-http"))
    implementation(project(":netflix-adapters:adapter-persistence"))

    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-context")
}