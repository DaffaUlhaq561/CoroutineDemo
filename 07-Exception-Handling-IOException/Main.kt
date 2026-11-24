// https://pl.kotl.in/5bNJTrp4O 
import kotlinx.coroutines.*
import java.io.IOException

suspend fun fetchTasks(): Int {
    delay(500L)
    throw IOException("No connection")
}

fun main() {
    runBlocking {
        launch {
            try {
                val count = fetchTasks()
                println("Loaded $count tasks")
            } catch (e: IOException) {
                println("Could not load: ${e.message}")
            }
        }
    }
}
