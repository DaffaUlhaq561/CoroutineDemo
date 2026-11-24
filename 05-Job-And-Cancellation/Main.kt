// https://pl.kotl.in/EPfl04d0x

import kotlinx.coroutines.*

suspend fun fetchTasks(): Int {
    delay(2000L)
    return 3
}

suspend fun fetchAnnouncement(): String {
    delay(1000L)
    return "Quiz on Monday"
}

suspend fun loadDashboard(): String = coroutineScope {
    val tasks = async { fetchTasks() }
    val note = async { fetchAnnouncement() }

    "${tasks.await()} tasks, ${note.await()}"
}

fun main() = runBlocking {
    println(loadDashboard())
}
