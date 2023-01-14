import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File

/**
 * Grabs the 50 largest instances from instances.social, and generates intent-filters (Android)
 * and Info.plist (iOS), so Tangent is associated with the major Mastodon hosts.
 *
 * Requires an instances.social API token assigned to an environment variable named
 * INSTANCES_SOCIAL_API_KEY. [Get One Here](https://instances.social/api/token).
 */
fun handleHostLinks(buildPath: String, androidPath: String, iosPath: String = "") {
    val hosts = fetchHosts(buildPath)
    androidHosts(hosts)
    iosHosts(hosts)
    // cleanup(buildPath)
}

private fun fetchHosts(path: String): List<String> {
    val file = File(path).also { it.createNewFile() }
    val count = 50
    val cmd = listOf("curl", "-X", "GET",
        "-H", "Authorization: Bearer ${System.getenv("INSTANCES_SOCIAL_API_KEY")}",
        "https://instances.social/api/1.0/instances/list?count=$count&sort_by=users&sort_order=desc",
        "-o", path)
    ProcessBuilder(cmd).start().waitFor()

    val json = Gson().fromJson(file.readText(), JsonObject::class.java)
    return json.get("instances").asJsonArray.map { it.asJsonObject["name"].asString }.toList()
}

private fun androidHosts(hosts: List<String>) {
    println("Creating host handling for Android...")
}

private fun iosHosts(hosts: List<String>) {
    println("Creating host handling for iOS...")
}

private fun cleanup(path: String) = File(path).delete()