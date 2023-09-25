package homework9

import kotlin.random.Random

class Sheep : Mob("Sheep", "Sheep.png", 8, Behavior.Passive, 0) {
    override fun tick() {
        super.tick()
        if (fx != null && Random.nextDouble() < .1) {
            fx?.play()
            fx?.rewind()
        }
    }


    companion object {
        // Easter Egg
        var fx: ddf.minim.AudioPlayer? = null
    }
}