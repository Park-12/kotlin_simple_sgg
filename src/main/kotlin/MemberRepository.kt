class MemberRepository {
    private val members = mutableListOf<Member>()
    private var lastId = 0

    fun join(
        loginId: String,
        loginPw: String,
        name: String,
        nickname: String,
        cellphoneNo: String,
        email: String
    ): Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()
        members.add(Member(id, regDate, updateDate, loginId, loginPw, name, nickname, cellphoneNo, email))

        return id
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