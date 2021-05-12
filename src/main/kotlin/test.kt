import java.io.File

fun main() {
    //testWriteFile()
    //testWriteFile2()
    testWriteFile3()
}

data class TestArticle(
    val id: Int,
    val title: String,
    val body: String
) {
    fun toJson(): String {
        var jsonStr = ""

        jsonStr += "{"
        jsonStr += "\r"
        jsonStr += "\t" + """ "id":$id """.trim() + ", "
        jsonStr += "\r"
        jsonStr += "\t" + """ "title":"$title" """.trim() + ", "
        jsonStr += "\r"
        jsonStr += "\t" + """ "body":"$body" """.trim()
        jsonStr += "\r"
        jsonStr += "}"
        return jsonStr
    }
}

fun testWriteFile3() {
    val testArticle = TestArticle(1,"제목1", "내용1")


    File("test/3.json").writeText(testArticle.toJson())
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
