class MemberRepository {
    private val members = mutableListOf<Member>()

    fun join(
        loginId: String,
        loginPw: String,
        name: String,
        nickname: String,
        cellphoneNo: String,
        email: String
    ): Int {
        val id = getLastId() + 1
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()
        val member = Member(id, regDate, updateDate, loginId, loginPw, name, nickname, cellphoneNo, email)

        writeStrFile("data/member/${member.id}.json", member.toJson())
        setLastId(id)

        return id
    }

    private fun setLastId(newLastId: Int) {
        writeIntFile("data/member/lastId.txt", newLastId)
    }

    private fun getLastId(): Int {
        val lastId = readIntFromFile("data/member/lastId.txt", 0)

        return lastId
    }


    fun isJoinableLoginId(loginId: String): Boolean {
        val member = getMemberByLoginId(loginId)

        return member == null
    }

    fun getMemberByLoginId(loginId: String): Member? {
        for (member in members) {
            if (member.loginId == loginId) {
                return member
            }
        }

        return null
    }

    fun makeTestMembers() {
        for (id in 1..9) {
            join("user${id}", "user${id}", "홍길동${id}", "사용자${id}", "0101234123${id}", "user${id}@test.com")
        }
    }

    fun getMemberById(id: Int): Member? {
        for (member in members) {
            if (member.id == id) {
                return member
            }
        }

        return null
    }

} 