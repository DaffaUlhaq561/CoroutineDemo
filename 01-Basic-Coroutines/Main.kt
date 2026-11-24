import kotlinx.coroutines.*

fun main() = runBlocking {
	launch {
		delay(1000L)
		println("Task finished")
	}
	println("Waiting for the task...")
}
