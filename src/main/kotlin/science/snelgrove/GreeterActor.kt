package science.snelgrove

import akka.actor.AbstractActor

class GreeterActor() : AbstractActor() {
    override fun createReceive(): Receive {
        return receiveBuilder().matchAny { language: Any ->
            when (language) {
                "EN" -> sender.tell("Hello!", self)
                "FR" -> sender.tell("Salut!", self)
                "IT" -> sender.tell("Ciao!", self)
                is String -> sender.tell("Sorry, I can't greet you in $language yet", self)
                else -> unhandled(language)
            }
        }.build();
    }
}