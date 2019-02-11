package wild.monitor

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalTime
import java.util.concurrent.Executors

@RestController
class EventHoseController {
    @GetMapping("/events", produces=[MediaType.TEXT_EVENT_STREAM_VALUE])
    fun events(): ResponseBodyEmitter {
        val emitter = SseEmitter()
        Executors.newSingleThreadExecutor().execute {
            for (i in 0..499) {
                try {
                    emitter.send(LocalTime.now().toString(), MediaType.TEXT_PLAIN)
                    Thread.sleep(5000)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emitter.completeWithError(e)
                    return@execute
                }
            }
            emitter.complete()
        }

        return emitter
    }
}