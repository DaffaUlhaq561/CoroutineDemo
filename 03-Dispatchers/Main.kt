// https://pl.kotl.in/UIhdWey6l 

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun fetchTasks(): Int {
	delay(2000L)
	return 3
}

suspend fun fetchAnnouncement(): String {
	delay(1000L)
	return "Quiz on Monday"
}

fun main() = runBlocking {
	val sequential = measureTimeMillis {
		val tasks = fetchTasks()
		val note = fetchAnnouncement()
		println("$tasks tasks, $note")
	}

	val concurrent = measureTimeMillis {
		val tasks = async { fetchTasks() }
		val note = async { fetchAnnouncement() }
		println("${tasks.await()} tasks, ${note.await()}")
	}
	println("Sequential: $sequential ms")
	println("Concurrent: $concurrent ms")
}
