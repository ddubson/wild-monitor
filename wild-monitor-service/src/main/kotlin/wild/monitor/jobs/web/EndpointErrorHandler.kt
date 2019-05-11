package wild.monitor.jobs.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import wild.monitor.JobNotFoundException

@ControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(JobNotFoundException::class)
    fun jobNotFound(e: JobNotFoundException): ResponseEntity<JobResponse> = ResponseEntity.notFound().build()
}
