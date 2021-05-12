import java.io.File

fun main() {
  //testWriteFile()
    testWriteFile2()
}

fun testWriteFile2() {
    val id = 1
    val title = "안녕하세요, 저는, age=33"
    val body = "내용"

    var fileContent = ""
    fileContent += "{"
    fileContent += "\r"
    fileContent += "\t" + """ "id":$id, """.trim()
    fileContent += "\r"
    fileContent += "\t" + """ "title":$title, """.trim()
    fileContent += "\r"
    fileContent += "\t" + """ "body":$body, """.trim()
    fileContent += "\r"
    fileContent += "}"
    File("test/2.txt").writeText(fileContent)
}

fun testWriteFile() {
    File("test/1.txt").writeText("안녕하세요.")
}
