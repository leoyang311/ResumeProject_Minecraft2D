package homework9

import kotlin.system.exitProcess

/**
 * The player, who is controlled by key presses.
 */
class Player : LivingEntity("Steve", "Steve.png", 20, 6) {
    var lastKeyPressed: String? = null

    override fun tick() {
        when (lastKeyPressed) {
            "arrow-right", "s" -> moveRight()
            "arrow-left", "a" -> moveLeft()
            "arrow-up", "w" -> moveUp()
            "arrow-down", "z" -> moveDown()
            "/" -> attackAggressiveMobs()
            "space", null -> {}
            else -> Game.addText("I don't know how to handle $lastKeyPressed")
        }
        lastKeyPressed = null
    }

    private fun attackAggressiveMobs() {
        var madeAttack = false
        val entities = Game.placedEntities
        for (entity in entities) {
            if (entity is Mob && entity.isAggressive) {
                if (this.isInFightingRange(entity)) {
                    attack(entity)
                    madeAttack = true
                }
            }
        }
        if (!madeAttack) {
            Game.addText("There were no hostile mobs for Steve to attack.")
        }
    }

    override fun die() {
        super.die()
        exitProcess(0)
    }
}