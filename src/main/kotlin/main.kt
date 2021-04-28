fun readLineTrim() = readLine()!!.trim()

fun main() {
    println("== SIMPLE SSG 시작 ==")

    while (true) {
        print("명령어) ")
        val command = readLineTrim()
        val commandBits = command.split("?", limit = 2)
        val url = commandBits[0]
        val paramStr = commandBits[1]
        val paramMap = mutableMapOf<String, String>()

        // paramStr = id=2&title=안녕&body=ㅋㅋ
        val paramStrBits = paramStr.split("&")

        for (paramStrBit in paramStrBits) {
            val paramStrBitBits = paramStrBit.split("=", limit = 2)
            val key = paramStrBitBits[0]
            val value = paramStrBitBits[1]

            paramMap[key] = value
        }

        when (url) {
            "/system/exit" -> {
                println("프로그램이 종료됩니다.")
                break
            }
            "/article/detail" -> {
                val id = paramMap["id"]!!.toInt()

                println("${id}번 게시물을 선택하였습니다.")
            }
        }
    }

    println("== SIMPLE SSG 끝 ==")
}