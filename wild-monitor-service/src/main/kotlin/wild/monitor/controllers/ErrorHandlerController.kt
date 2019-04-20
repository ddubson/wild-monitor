package wild.monitor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wild.monitor.ProjectNotFoundException;

@ControllerAdvice
public class ErrorHandlerController {
	@ExceptionHandler(ProjectNotFoundException::class)
	fun projectDoesNotExistException( e:ProjectNotFoundException): ResponseEntity<ErrorResponse> =
			ResponseEntity.badRequest().body(ErrorResponse(e.message
			?: "Project does not exist."))
}
