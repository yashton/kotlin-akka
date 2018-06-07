package science.snelgrove
import akka.actor.*
import akka.event.Logging
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit

data class Poke(val language: String);

class PromptingActor() : AbstractActorWithTimers() {
    private val log = Logging.getLogger(context.system(), this)

    val greeter: ActorRef

    init {
        greeter = context.actorOf(Props.create(GreeterActor::class.java))
        timers.startPeriodicTimer("EN", Poke("EN"), FiniteDuration.apply(13, TimeUnit.SECONDS))
        timers.startPeriodicTimer("FR", Poke("FR"), FiniteDuration.apply(7, TimeUnit.SECONDS))
        timers.startPeriodicTimer("ES", Poke("ES"), FiniteDuration.apply(17, TimeUnit.SECONDS))
        timers.startPeriodicTimer("IT", Poke("IT"), FiniteDuration.apply(3, TimeUnit.SECONDS))
    }
    override fun createReceive(): Receive {
        return receiveBuilder().matchAny { msg: Any ->
            when(msg) {
                is Poke -> {
                    println("Prompting in ${msg.language}")
                    greeter.tell(msg.language, self)
                }
                is String -> println("They said $msg")
                else -> unhandled(msg)
            }

        }.build()
    }
}