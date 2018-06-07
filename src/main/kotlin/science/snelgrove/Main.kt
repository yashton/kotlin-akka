package science.snelgrove

import akka.actor.*
import java.time.Duration

fun main(vararg args: String) {
    val system = ActorSystem.create()
    system.actorOf(Props.create(PromptingActor::class.java), "prompting-actor")

    val term: Runnable = object : Runnable { override fun run() { system.terminate() }}
    system.scheduler().scheduleOnce(Duration.ofSeconds(60), term, system.dispatcher())
}