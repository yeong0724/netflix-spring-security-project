dependencies {
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-port"))

    implementation("org.springframework:spring-context")

    implementation(project(":netflix-adapters:adapter-http"))
}