package wild.monitor.models

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.SecondaryTable
import javax.persistence.Table

@Entity
@Table(name = "projects_meta")
@SecondaryTable(
        name = "projects_record",
        pkJoinColumns = [PrimaryKeyJoinColumn(name = "meta_db_id", referencedColumnName = "db_id")])
class Project(@Column(name = "project_name") val projectName: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="db_id")
    var dbId: Int = 0

    @Column(name = "job_id")
    val id: UUID = UUID.randomUUID()

    @Column(name = "project_key")
    val projectKey = Hashing.sha512().hashString(id.toString(), StandardCharsets.UTF_8).toString()

    @Column(name = "created_on")
    val createdOn: LocalDateTime = LocalDateTime.now()
}