package wild.monitor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WildMonitorApp

fun main(args: Array<String>) {
    runApplication<WildMonitorApp>(*args)
}
