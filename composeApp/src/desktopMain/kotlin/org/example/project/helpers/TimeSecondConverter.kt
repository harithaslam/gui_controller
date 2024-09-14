package org.example.project.helpers

fun timeToMilliseconds(time: String): Long {
    val parts = time.split(":")
    val hours = parts[0].toLong()
    val minutes = parts[1].toLong()
    val seconds = parts[2].toLong()
    return (hours * 3600 + minutes * 60 + seconds) * 1000
}
fun convertTimesToRelativeMilliseconds(times: List<String>): List<Long> {
    if (times.isEmpty()) return emptyList()

    val firstTimeMilliseconds = timeToMilliseconds(times[0])
    return times.map { time ->
        timeToMilliseconds(time) - firstTimeMilliseconds
    }
}