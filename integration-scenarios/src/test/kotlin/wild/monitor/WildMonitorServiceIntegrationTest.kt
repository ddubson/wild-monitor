package wild.monitor

import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class WildMonitorServiceIntegrationTest {
    @Test
    @DisplayName("WildMonitor Critical Path 1")
    fun criticalPath1() {
        given()
                .body("""{ "projectName": "Amazing Loon" }""")
                .contentType("application/json")
                .`when`()
                .post("/projects")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", notNullValue())
    }
}
