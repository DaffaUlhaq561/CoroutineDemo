// https://pl.kotl.in/_2Won3ZcC

import kotlinx.coroutines.*

suspend fun fetchUserName(): String {
	delay(1000L)
	return "Aisyah"
}

fun main() = runBlocking {
	println("Loading...")
	val name = fetchUserName()
	println("Hello, $name")
}
