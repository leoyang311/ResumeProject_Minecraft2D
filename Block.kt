package homework9

/**
 * An inanimate object in the game, such as [Sand].
 */
open class Block(type: String, imageFileName: String) : Entity(type, imageFileName) {
    override fun tick() {
        // Blocks don't do anything.
    }
}