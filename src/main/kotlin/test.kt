fun main() {
    //testWriteFile();
    //testWriteFile2();
    //testWriteFile3();
    //testReadFile();
    //testReadFile2();
    testReadFile3();
}

data class TestArticle(
    val id: Int,
    val title: String,
    val body: String
) {
    fun toJson(): String {
        var jsonStr = ""

        jsonStr += "{"
        jsonStr += "\r\n"

        jsonStr += "\t" + """ "id":$id """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "title":"$title" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "body":"$body" """.trim()

        jsonStr += "\r\n"

        jsonStr += "}"

        return jsonStr
    }
}

fun testReadFile3() {
    writeIntFile("test/4.txt", 100)
    val num = readIntFromFile("test/4.txt")

    println("num : $num")
}

fun testReadFile2() {
    testWriteFile3();

    // fileContent 변수에 담기는 내용
    /*
    {
        "id":1,
        "title":"제목1",
        "body":"내용1"
    }
    */
    val fileContent = readStrFromFile("test/3.json")

    val testArticle = testArticleFromJson(fileContent)
    println(testArticle.id) // 출력 : 1
}

fun testArticleFromJson(jsonStr: String): TestArticle {
    val jsonMap = mapFromJson(jsonStr)

    val id = jsonMap["id"].toString().toInt()
    val title = jsonMap["title"].toString()
    val body = jsonMap["body"].toString()

    return TestArticle(id, title, body)
}

fun testReadFile() {
    testWriteFile()

    val fileContent = readStrFromFile("test/1.txt")
    println("test/1.txt의 내용 : $fileContent")
}

fun testWriteFile3() {
    val testArticle = TestArticle(8, "제목1", "내용1")
    writeStrFile("test/3.json", testArticle.toJson())
}

fun testWriteFile2() {
    val id = 1
    val title = "안녕하세요, 저는,age=33"
    val body = "내용"

    var fileContent = ""
    fileContent += "{"
    fileContent += "\r\n"
    fileContent += "\t" + """ "id":$id, """.trim()
    fileContent += "\r\n"
    fileContent += "\t" + """ "title":"$title," """.trim()
    fileContent += "\r\n"
    fileContent += "\t" + """ "body":"$body" """.trim()
    fileContent += "\r\n"
    fileContent += "}"

    writeStrFile("test/2.txt", fileContent)
}

fun testWriteFile() {
    writeStrFile("test/1.txt", "안녕하세요.")
}