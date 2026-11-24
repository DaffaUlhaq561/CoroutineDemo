// https://pl.kotl.in/OwMLuaABI

import kotlinx.coroutines.*
import java.io.IOException

suspend fun fetchTasks(): Int {
    delay(500L)
    throw IOException("No connection")
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
    try {
        println(loadDashboard())
    } catch (e: IOException) {
        println("Error: ${e.message}")
    }
}
