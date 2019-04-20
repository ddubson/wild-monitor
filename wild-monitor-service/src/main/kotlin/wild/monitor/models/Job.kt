package wild.monitor.models

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.SecondaryTable
import javax.persistence.Table

@Entity
@Table(name = "jobs_meta")
@SecondaryTable(
        name = "jobs_record",
        pkJoinColumns = [PrimaryKeyJoinColumn(name = "meta_db_id", referencedColumnName = "db_id")])
class Job(@Column(name="job_id") val jobId: UUID,
               val status: JobStatus,
               @ManyToOne
               val project: Project) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id")
    var dbId: Int = 0

    @Column(name = "created_on")
    val createdOn = LocalDateTime.now()!!

    @Column(name = "expires_on")
    val expiresOn = createdOn.plusHours(1)!!
}