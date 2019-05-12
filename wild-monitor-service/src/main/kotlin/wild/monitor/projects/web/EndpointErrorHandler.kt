package wild.monitor.projects.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import wild.monitor.projects.NoProjectNameSuppliedException
import wild.monitor.projects.ProjectNotFoundException
import wild.monitor.web_support.ErrorResponse

@ControllerAdvice
public class EndpointErrorHandler {
    @ExceptionHandler(ProjectNotFoundException::class)
    fun projectDoesNotExistException( e: ProjectNotFoundException): ResponseEntity<ErrorResponse> =
            ResponseEntity.badRequest().body(ErrorResponse(e.message
                    ?: "Project does not exist."))

    @ExceptionHandler(NoProjectNameSuppliedException::class)
    fun noProjectNameSuppliedException(e: NoProjectNameSuppliedException): ResponseEntity<ErrorResponse> =
            ResponseEntity.badRequest().body(ErrorResponse(e.message!!, "Please provide a unique project name."))
}
