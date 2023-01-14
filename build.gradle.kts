import com.google.gson.Gson
import com.google.gson.JsonObject

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.code.gson:gson:2.10.1")
    }
}

plugins {
    // trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(Versions.androidPlugin).apply(false)
    id("com.android.library").version(Versions.androidPlugin).apply(false)
    // id("org.jmailen.kotlinter").version(buildsrc.Versions.kotlinter).apply(false)
    kotlin("android").version(Versions.kotlin).apply(false)
    kotlin("multiplatform").version(Versions.kotlin).apply(false)
    kotlin("jvm").version(Versions.kotlin).apply(false)
    id("com.google.devtools.ksp") version Versions.ksp apply false
    id("com.squareup.sqldelight") version Versions.sqldelight apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

// TODO separating this logic into its own gradle.kts file breaks the gson dependency and i don't know why
// TODO rename to handle_host_links
tasks.register("temp") {
    val tempPath = "${rootProject.buildDir}/hosts.txt"
    val hosts = fetchHosts(tempPath)
    androidHosts(hosts)
    iosHosts(hosts)
    cleanup(tempPath)
}

fun fetchHosts(path: String): List<String> {
    val file = file(path).also { it.createNewFile() }
    val count = 50
    val cmd = listOf("curl", "-X", "GET",
        "-H", "Authorization: Bearer ${System.getenv("INSTANCES_SOCIAL_API_KEY")}",
        "https://instances.social/api/1.0/instances/list?count=$count&sort_by=users&sort_order=desc",
        "-o", path)
    ProcessBuilder(cmd).start().waitFor()

    val json = Gson().fromJson(file.readText(), JsonObject::class.java)
    return json.get("instances").asJsonArray.map { it.asJsonObject["name"].asString }.toList()
}

fun androidHosts(hosts: List<String>) {
    println("Creating host handling for Android...")
    val androidDir = "$projectDir/androidApp/src/main"
}

fun iosHosts(hosts: List<String>) {
    println("Creating host handling for iOS...")
}

fun cleanup(path: String) = file(path).delete()