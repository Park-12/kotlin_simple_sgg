import java.io.File
import java.text.SimpleDateFormat

fun readLineTrim() = readLine()!!.trim()

fun mapFromJson(jsonStr: String): Map<String, Any> {
    val map = mutableMapOf<String, Any>()

    var jsonStr = jsonStr.drop(1)
    jsonStr = jsonStr.dropLast(1)

    val jsonItems = jsonStr.split(",\r\n")

    for (jsonItem in jsonItems) {
        val jsonItemBits = jsonItem.trim().split(":", limit = 2)

        val key = jsonItemBits[0].trim().drop(1).dropLast(1)
        var value = jsonItemBits[1].trim()

        when {
            value == "true" -> {
                map[key] = true
            }
            value == "false" -> {
                map[key] = false
            }
            value.startsWith("\"") -> {
                map[key] = value.drop(1).dropLast(1)
            }
            value.contains(".") -> {
                map[key] = value.toDouble()
            }
            else -> {
                map[key] = value.toInt()
            }
        }
    }

    return map.toMap()
}

fun readStrFromFile(filePath: String): String {
    if ( !File(filePath).isFile ) {
        return ""
    }

    return File(filePath).readText(Charsets.UTF_8)
}

fun writeStrFile(filePath: String, fileContent: String) {
    File(filePath).parentFile.mkdirs()
    File(filePath).writeText(fileContent)
}

fun readIntFromFile(filePath: String, default:Int): Int {
    val fileContent = readStrFromFile(filePath)

    if ( fileContent == "" ) {
        return default
    }

    return fileContent.toInt()
}

fun writeIntFile(filePath: String, fileContent: Int) {
    writeStrFile(filePath, fileContent.toString())
}

fun deleteFile(filePath:String) {
    File(filePath).delete()
}

object Util {
    fun getNowDateStr(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return format.format(System.currentTimeMillis())
    }
}