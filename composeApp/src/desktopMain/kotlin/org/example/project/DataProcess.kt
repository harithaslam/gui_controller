package org.example.project

import MalaysianTimeFormatter
import com.example.project.cache.SensorDataQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.util.Date

class DataProcess(private val sensorDataQueries: SensorDataQueries) {


    suspend fun exportSensorDataToExcel(filePath: String) {
        // Create a new workbook and sheet
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("ArduinoSensorData")

        // Create header row
        val headerRow = sheet.createRow(0)
        val headers = arrayOf(
            "roll_IMU1", "pitch_IMU1", "roll_IMU2", "pitch_IMU2",
            "rs775_speed", "spg_speed", "rs775_motor_voltage",
            "rs775_current", "spg_voltage", "spg_current","date","time"
        )

        for ((index, header) in headers.withIndex()) {
            headerRow.createCell(index).setCellValue(header)
        }


        // Populate data rows
        for ((rowIndex, sensorData) in sensorDataQueries.selectAllSensorData(::mapToSensorData).executeAsList().withIndex()) {
            println(sensorData.pitch_IMU1)
            val row = sheet.createRow(rowIndex + 1)
            row.createCell(0).setCellValue(sensorData.pitch_IMU1 ?: 0.0)
            row.createCell(1).setCellValue(sensorData.pitch_IMU1 ?: 0.0)
            row.createCell(2).setCellValue(sensorData.roll_IMU2 ?: 0.0)
            row.createCell(3).setCellValue(sensorData.pitch_IMU2 ?: 0.0)
            row.createCell(4).setCellValue(sensorData.rs775_speed ?: 0.0)
            row.createCell(5).setCellValue(sensorData.spg_speed ?: 0.0)
            row.createCell(6).setCellValue(sensorData.rs775_motor_voltage ?: 0.0)
            row.createCell(7).setCellValue(sensorData.rs775_current ?: 0.0)
            row.createCell(8).setCellValue(sensorData.spg_voltage ?: 0.0)
            row.createCell(9).setCellValue(sensorData.spg_current ?: 0.0)
            row.createCell(10).setCellValue(sensorData.date ?: "")
            row.createCell(11).setCellValue(sensorData.time ?: "")

        }

        // Write the workbook to a file
        withContext(Dispatchers.IO) {
            FileOutputStream("${filePath}_${MalaysianTimeFormatter.getCurrentMalaysianTimeForFile()}.xlsx").use { fileOut ->
                workbook.write(fileOut)
            }
        }

        // Close the workbook
        workbook.close()
    }

    fun mapToSensorData(
        id: Long?,
        roll_IMU1: Double?,
        pitch_IMU1: Double?,
        roll_IMU2: Double?,
        pitch_IMU2: Double?,
        rs775_speed: Double?,
        spg_speed: Double?,
        rs775_motor_voltage: Double?,
        rs775_current: Double?,
        spg_voltage: Double?,
        spg_current: Double?,
        date:String?,
        time:String?
    ): ArduinoSensorData {
        return ArduinoSensorData(
            roll_IMU1 = roll_IMU1,
            pitch_IMU1 = pitch_IMU1,
            roll_IMU2 = roll_IMU2,
            pitch_IMU2 = pitch_IMU2,
            rs775_speed = rs775_speed,
            spg_speed = spg_speed,
            rs775_motor_voltage = rs775_motor_voltage,
            rs775_current = rs775_current,
            spg_voltage = spg_voltage,
            spg_current = spg_current,
            date = date,
            time = time

        )
    }
}