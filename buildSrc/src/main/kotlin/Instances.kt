import com.google.gson.Gson
import com.google.gson.JsonObject
import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File
import java.io.FileWriter

/**
 * Grabs the largest instances from instances.social, and generates copy-pasta for Android Manifest
 * and Info.plist, so Tangent is associated with the major Mastodon hosts.
 *
 * Requires an instances.social API token assigned to an environment variable named
 * INSTANCES_SOCIAL_API_KEY. [Get One Here](https://instances.social/api/token).
 *
 * At some point it'd be neat to make this a turnkey step of production builds, but I'm sure
 * we'll simply remember to run this task and manually update files before shipping :P
 */
fun handleHostLinks(buildPath: String) {
    val cwd = "$buildPath/hosts"
    File(cwd).apply { deleteRecursively(); mkdir() }
    val hosts = fetchHosts(cwd)
    androidHosts(cwd, hosts)
    // iosHosts(cwd, hosts)
}

private fun fetchHosts(cwd: String): List<String> {
    val outputPath = "$cwd/hosts.json"
    val file = File(outputPath).also { it.createNewFile() }
    val count = 50
    val cmd = listOf("curl", "-X", "GET",
        "-H", "Authorization: Bearer ${System.getenv("INSTANCES_SOCIAL_API_KEY")}",
        "https://instances.social/api/1.0/instances/list?count=$count&sort_by=users&sort_order=desc",
        "-o", outputPath)
    ProcessBuilder(cmd).start().waitFor()

    val json = Gson().fromJson(file.readText(), JsonObject::class.java)
    return json.get("instances").asJsonArray.map { it.asJsonObject["name"].asString }.toList()
}

private fun androidHosts(cwd: String, hosts: List<String>) {
    val outputPath = "$cwd/intent-filters.txt"
    val file = File(outputPath).also { it.createNewFile() }
    val filters = hosts.joinToString("") { filterFor(it) }
    FileWriter(file).use { it.write(filters) }

    if (Os.isFamily(Os.FAMILY_MAC)) {
        ProcessBuilder("open", outputPath).start()
    } else println("Android Intent Filters are ready in: $outputPath")
}

private fun filterFor(host: String): String {
    return """
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="$host"
                    android:scheme="https" />
                <data android:pathPattern=".*" />
            </intent-filter>
    """
}

private fun iosHosts(cwd: String, hosts: List<String>) {
    TODO()
}
