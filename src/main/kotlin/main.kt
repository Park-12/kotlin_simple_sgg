fun readLineTrim() = readLine()!!.trim()

fun main() {
    println("== SIMPLE SSG 시작 ==")

    while (true) {
        print("명령어) ")
        val command = readLineTrim()

        val rq = Rq(command)
        println(rq.getStringParam("title") == "제목1") // true
        println(rq.getIntParam("id") == 1) // true
    }

    println("== SIMPLE SSG 끝 ==")
}

class Rq(command: String) {
    val actionPath: String
    val paramMap: Map<String, String>

    init {
        val commandBits = command.split("?", limit = 2)

        actionPath = commandBits[0].trim()
        val queryStr = if (commandBits.lastIndex == 1 && commandBits[1].isNotEmpty()) {
            commandBits[1].trim()
        } else {
            ""
        }

        paramMap = if (queryStr.isEmpty()) {
            mapOf()
        } else {
            val paramMapTemp = mutableMapOf<String, String>()

            val queryStrBits = queryStr.split("&")

            for (queryStrBit in queryStrBits) {
                val queryStrBitBits = queryStrBit.split("=", limit = 2)
                val paramName = queryStrBitBits[0]
                val paramValue = if (queryStrBitBits.lastIndex == 1 && queryStrBitBits[1].isNotEmpty()) {
                    queryStrBitBits[1].trim()
                } else {
                    ""
                }

                if (paramValue.isNotEmpty()) {
                    paramMapTemp[paramName] = paramValue
                }
            }

            paramMapTemp.toMap()
        }
    }

    fun getStringParam(name: String): String {
        return paramMap[name]!!
    }

    fun getIntParam(name: String): Int {
        return paramMap[name]!!.toInt()
    }
}