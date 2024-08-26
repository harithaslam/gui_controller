import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object MalaysianTimeFormatter {

    private val malaysiaZoneId = ZoneId.of("Asia/Kuala_Lumpur")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")

    /**
     * Gets the current date formatted in Malaysian time.
     * @return A string representing the current date formatted for filenames.
     */
    fun getDateForFile(): String {
        val malaysiaDateTime = ZonedDateTime.now(malaysiaZoneId)
        return malaysiaDateTime.format(dateFormatter)
    }

    /**
     * Gets the current time formatted in Malaysian time.
     * @return A string representing the current time formatted for filenames.
     */
    fun getTimeForFile(): String {
        val malaysiaDateTime = ZonedDateTime.now(malaysiaZoneId)
        return malaysiaDateTime.format(timeFormatter)
    }

    /**
     * Gets the current date and time formatted in Malaysian time.
     * @return A string representing the current date and time formatted for filenames.
     */
    fun getCurrentMalaysianTimeForFile(): String {
        val malaysiaDateTime = ZonedDateTime.now(malaysiaZoneId)
        return malaysiaDateTime.format(dateTimeFormatter)
    }
}
