package wild.monitor.projects.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import wild.monitor.ProjectNotFoundException
import wild.monitor.web_support.ErrorResponse

@ControllerAdvice
public class EndpointErrorHandler {
    @ExceptionHandler(ProjectNotFoundException::class)
    fun projectDoesNotExistException( e: ProjectNotFoundException): ResponseEntity<ErrorResponse> =
            ResponseEntity.badRequest().body(ErrorResponse(e.message
                    ?: "Project does not exist."))
}
