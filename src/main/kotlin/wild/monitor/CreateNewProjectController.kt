package wild.monitor

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class CreateNewProjectController {
    @PostMapping
    fun createANewProject(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello World")
    }
}