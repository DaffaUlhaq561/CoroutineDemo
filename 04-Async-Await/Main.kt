// https://pl.kotl.in/ANgNTktG_

import kotlinx.coroutines.*

fun main() = runBlocking {
	val job = launch {
		repeat(5) { i ->
			println("Downloading part ${i + 1}")
			delay(500L)
		}
	}
	delay(1200L)
	job.cancel()
	println("Download cancelled")
}
