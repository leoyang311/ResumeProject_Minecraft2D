package homework9

class Queue {
    private val data = mutableListOf<Int>()
    fun push(item: Int): Unit {
        data.add(item)
    }

    fun pop(): Unit {
        data.removeLast()
    }
}

class Queue2<T> {
    private val data = mutableListOf<T>()
    fun push(item: T): Unit {
        data.add(item)
    }

    fun pop(): Unit {
        data.removeLast()
    }
}

fun main() {
    val q = Queue()
    q.push(1)
    q.push(2)
    q.push(3)
    q.pop()
    val q2 = Queue2<String>()
    q2.push("2")
}