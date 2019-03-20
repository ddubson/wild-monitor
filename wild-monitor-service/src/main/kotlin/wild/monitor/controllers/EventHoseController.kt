package wild.monitor.controllers

import org.springframework.context.event.EventListener
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import javax.servlet.http.HttpServletResponse

@RestController
class EventHoseController {
    private val emitters = CopyOnWriteArrayList<SseEmitter>()

    @GetMapping("/events", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun events(response: HttpServletResponse): ResponseBodyEmitter {
        response.setHeader("Cache-Control", "no-store")

        val emitter = SseEmitter()
        // SseEmitter emitter = new SseEmitter(180_000L);

        this.emitters.add(emitter)

        emitter.onCompletion { this.emitters.remove(emitter) }
        emitter.onTimeout { this.emitters.remove(emitter) }

        return emitter
    }

    @EventListener
    fun onMemoryInfo(jobEvent: String) {
        val deadEmitters = ArrayList<SseEmitter>()
        this.emitters.forEach { emitter ->
            try {
                emitter.send(jobEvent)

                // close connnection, browser automatically reconnects
                // emitter.complete();
                // SseEventBuilder builder = SseEmitter.event().name("second").data("1");
                // SseEventBuilder builder =
                // SseEmitter.event().reconnectTime(10_000L).data(jobEvent).id("1");
                // emitter.send(builder);
            } catch (e: Exception) {
                deadEmitters.add(emitter)
            }
        }

        this.emitters.removeAll(deadEmitters)
    }
}