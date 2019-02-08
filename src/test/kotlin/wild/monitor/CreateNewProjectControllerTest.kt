package wild.monitor

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(CreateNewProjectController::class)
class CreateNewProjectControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun createNewProject_whenProvidedAName_shouldReturnProjectDetails() {
        this.mockMvc.perform(post("/projects"))
                .andExpect(status().isOk)
    }
}