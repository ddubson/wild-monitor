package wild.monitor

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.util.*

data class Project(val projectName: String) {
    val id: UUID = UUID.randomUUID()
    val projectKey = Hashing.sha512().hashString(id.toString(), StandardCharsets.UTF_8).toString()
}