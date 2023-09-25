package homework9

import kotlin.random.Random

const val FIGHTING_RANGE = 3.0

/**
 * A living entity, such as a [Mob] or [Player] in the game.
 */
abstract class LivingEntity(
    type: String,
    imageFileName: String,
    val maxHearts: Int,
    val attackStrength: Int
) : Entity(type, imageFileName) {
    enum class Status { Healthy, Injured, Dead }

    var numHearts = maxHearts
        private set

    var status = Status.Healthy
        private set
        get() =
            when (numHearts) {
                maxHearts -> Status.Healthy
                0 -> Status.Dead
                else -> Status.Injured
            }

    val isAlive
        get() = status != Status.Dead

    override fun toString() = "$status $type"

    /**
     * Checks whether [entity] is within fighting range of this living entity.
     * This throws [IllegalArgumentException] if either does not have a position.
     */
    fun isInFightingRange(entity: Entity): Boolean {
        val thisPos = Game.getPosition(this)
        val entityPos = Game.getPosition(entity)
        require(thisPos != null && entityPos != null)
        return Game.calculateDistance(this, entity) < FIGHTING_RANGE
    }

    /**
     * Attacks [victim], doing [attackStrength] hearts of damage.
     */
    open fun attack(victim: LivingEntity) {
        victim.takeDamage(this, attackStrength)
    }

    /**
     * Takes up to [damage] hearts of damage, to a maximum of [numHearts],
     * printing a message with the amount of damage taken and the
     * resulting [status].
     */
    fun takeDamage(attacker: LivingEntity, damage: Int) {
        Game.addText("${attacker.type} attacked ${type}.")
        val actualDamage = if (damage > numHearts) numHearts else damage
        numHearts -= actualDamage
        val text = if (actualDamage == 1) "heart" else "hearts"
        Game.addText("$type took $actualDamage $text of damage and is now $status.")
        if (status == Status.Dead) {
            this.die()
        }
    }

    private fun move(deltaX: Int, deltaY: Int) {
        val position = Game.getPosition(this)
        require(position != null)
        if (Game.isInBounds(position.x + deltaX, position.y + deltaY) &&
            Game.isEmpty(position.x + deltaX, position.y + deltaY)
        ) {
            Game.place(this, position.x + deltaX, position.y + deltaY)
        }
    }

    /**
     * Moves right one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds. This should throw
     * [IllegalArgumentException] if this living entity is not on the board.
     */
    fun moveRight() {
        move(1, 0)
    }

    /**
     * Moves left one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds.
     */
    fun moveLeft() {
        move(-1, 0)
    }

    /**
     * Moves up one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds.
     */
    fun moveUp() {
        move(0, -1)
    }

    /**
     * Moves down one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds.
     */
    fun moveDown() {
        move(0, 1)
    }

    /**
     * Moves randomly to an adjacent unoccupied cell. Cells
     * are considered adjacent if they are to the right, left, up,
     * or down (i.e., changing by 1 either the x-coordinate or the
     * y-coordinate but not both).
     */
    fun moveRandomly() {
        val oldPosition = Game.getPosition(this)
        require(oldPosition != null)
        val remainingDirections = mutableListOf(0, 1, 2, 3)
        while (Game.getPosition(this) == oldPosition &&
            remainingDirections.isNotEmpty()
        ) {
            val dir = remainingDirections.random()
            remainingDirections.remove(dir)
            when (dir) {
                0 -> moveUp()
                1 -> moveDown()
                2 -> moveLeft()
                else -> moveRight()
            }
        }
    }

    /**
     * Tries to move to an adjacent cell closer to [player]. Cells
     * are considered adjacent if they are to the right, left, up,
     * or down (i.e., changing by 1 either the x-coordinate or the
     * y-coordinate but not both). If all closer adjacent cells are
     * occupied, no movement will occur. This should throw
     * [IllegalArgumentException] if this living entity or [player]
     * is not on the board.
     */
    fun moveTowards(player: Player) {
        val originalPosition = Game.getPosition(this)
        require(originalPosition != null)
        val playerPosition = Game.getPosition(player)
        require(playerPosition != null)
        if (originalPosition.x > playerPosition.x) {
            moveLeft()
        } else if (originalPosition.x < playerPosition.x) {
            moveRight()
        }
        if (originalPosition == Game.getPosition(this)) {
            if (originalPosition.y > playerPosition.y) {
                moveUp()
            } else if (originalPosition.y < playerPosition.y) {
                moveDown()
            }
        }
    }
}