data class Member(
    val id: Int,
    val regDate: String,
    var updateDate: String,
    var loginId: String,
    var loginPw: String,
    var name: String,
    var nickname: String,
    var cellphoneNo: String,
    var email: String
) {
    fun toJson(): String {
        var jsonStr = ""

        jsonStr += "{"
        jsonStr += "\r\n"

        jsonStr += "\t" + """ "id":$id """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "regDate":"$regDate" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "updateDate":"$updateDate" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "loginId":"$loginId" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "loginPw":"$loginPw" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "name":"$name" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "nickname":"$nickname" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "cellphoneNo":"$cellphoneNo" """.trim() + ","

        jsonStr += "\r\n"

        jsonStr += "\t" + """ "email":"$email" """.trim()

        jsonStr += "\r\n"

        jsonStr += "}"

        return jsonStr
    }
} 