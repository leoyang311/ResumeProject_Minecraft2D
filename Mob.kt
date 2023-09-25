package homework9

/**
 * A Minecraft mob.
 */
open class Mob(type: String, imageFileName: String, maxHearts: Int, val behavior: Behavior, attackStrength: Int) :
    LivingEntity(type, imageFileName, maxHearts, attackStrength) {

    enum class Behavior { Passive, Hostile, Neutral, Boss }

    val isAggressive
        get() = if (!isAlive) false
        else when (behavior) {
            Behavior.Passive -> false
            Behavior.Boss, Behavior.Hostile -> true
            Behavior.Neutral -> status == Status.Injured
        }

    // While we normally don't provide KDoc when overriding methods,
    // there is important information here about an IllegalArgumentException.
    /**
     * Attacks [victim], doing [attackStrength] hearts of damage. This throws
     * [IllegalArgumentException] unless [isAggressive].
     */
    override fun attack(victim: LivingEntity) {
        require(this.isAggressive)
        super.attack(victim)
    }

    override fun tick() {
        // If aggressive, then
        if (this.isAggressive) {
            // - If the distance to the player is less than or equal to FIGHTING_RANGE,
            //   attack the player.
            if (this.isInFightingRange(Game.player)) {
                attack(Game.player)
            } else {
                // - Otherwise, move towards player.
                moveTowards(Game.player)
            }
        } else {
            // If not aggressive, move in a random direction.
            moveRandomly()
        }
    }
}





