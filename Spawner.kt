package homework9

import kotlin.random.Random

open class Spawner<T: Entity>(val mobType: String, val mobBuilder: () -> T):
    Entity("$mobType Spawner", "SpawnerOnSand.png") {

    private fun makeNewMob(): T {
        return mobBuilder()
    }

    override fun tick() {
        TODO("Not yet implemented")
    }
}