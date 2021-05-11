class MemberController {
    fun logout(rq: Rq) {
        loginedMember = null

        println("로그아웃 되었습니다.")
    }

    fun login(rq: Rq) {
        print("로그인아이디 : ")
        val loginId = readLineTrim()

        val member = memberRepository.getMemberByLoginId(loginId)

        if (member == null) {
            println("`$loginId`(은)는 존재하지 않는 회원의 로그인아이디 입니다.")
            return
        }

        print("로그인비밀번호 : ")
        val loginPw = readLineTrim()

        if (member.loginPw != loginPw) {
            println("비밀번호가 일치하지 않습니다.")
            return
        }

        loginedMember = member

        println("${member.nickname}님 환영합니다.")
    }

    fun join(rq: Rq) {
        print("로그인아이디 : ")
        val loginId = readLineTrim()

        val isJoinableLoginId = memberRepository.isJoinableLoginId(loginId)

        if (isJoinableLoginId == false) {
            println("`$loginId`(은)는 이미 사용중인 로그인아이디 입니다.")
            return
        }

        print("로그인비밀번호 : ")
        val loginPw = readLineTrim()
        print("이름 : ")
        val name = readLineTrim()
        print("별명 : ")
        val nickname = readLineTrim()
        print("휴대전화번호 : ")
        val cellphoneNo = readLineTrim()
        print("이메일 : ")
        val email = readLineTrim()

        val id = memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email)

        println("${id}번 회원으로 가입되었습니다.")
    }
}