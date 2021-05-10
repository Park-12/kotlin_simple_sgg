import java.text.SimpleDateFormat

fun main() {
    println("== SIMPLE SSG 시작 ==")

    val memberRepository = MemberRepository()
    val articleRepository = ArticleRepository()

    memberRepository.makeTestMembers()
    articleRepository.makeTestArticles()

    val systemController = SystemController()
    val articleController = ArticleController()
    val memberController = MemberController()

    while (true) {
        val prompt = if (loginedMember == null) {
            "명령어) "
        } else {
            "${loginedMember!!.nickname}) "
        }

        print(prompt)
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath) {
            "/system/exit" -> {
                systemController.exit(rq)

                break
            }
            "/member/logout" -> {
                memberController.logout(rq)
            }
            "/member/login" -> {
                memberController.login(rq)
            }
            "/member/join" -> {
                memberController.join(rq)
            }
            "/article/write" -> {
                articleController.write(rq)
            }
            "/article/list" -> {
                articleController.list(rq)
            }
            "/article/detail" -> {
                articleController.detail(rq)
            }
            "/article/modify" -> {
                articleController.modify(rq)
            }
            "/article/delete" -> {
                articleController.delete(rq)
            }
        }
    }

    println("== SIMPLE SSG 끝 ==")
}

// Rq는 UserRequest의 줄임말이다.
// Request 라고 하지 않은 이유는, 이미 선점되어 있는 클래스명 이기 때문이다.
class Rq(command: String) {
    // 데이터 예시
    // 전체 URL : /artile/detail?id=1
    // actionPath : /artile/detail
    val actionPath: String

    // 데이터 예시
    // 전체 URL : /artile/detail?id=1&title=안녕
    // paramMap : {id:"1", title:"안녕"}
    private val paramMap: Map<String, String>

    // 객체 생성시 들어온 command 를 ?를 기준으로 나눈 후 추가 연산을 통해 actionPath와 paramMap의 초기화한다.
    // init은 객체 생성시 자동으로 딱 1번 실행된다.
    init {
        // ?를 기준으로 둘로 나눈다.
        val commandBits = command.split("?", limit = 2)

        // 앞부분은 actionPath
        actionPath = commandBits[0].trim()

        // 뒷부분이 있다면
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

    fun getStringParam(name: String, default: String): String {
        return paramMap[name] ?: default
    }

    fun getIntParam(name: String, default: Int): Int {
        return if (paramMap[name] != null) {
            try {
                paramMap[name]!!.toInt()
            } catch (e: NumberFormatException) {
                default
            }
        } else {
            default
        }
    }
}

// 세션 시작
var loginedMember: Member? = null
// 세션 끝

// 컨트롤러 시작
// 시스템 컨트롤러 시작
class SystemController {
    fun exit(rq: Rq) {
        println("프로그램을 종료합니다.")
    }
}
// 시스템 컨트롤러 끝

// 회원 컨트롤러 시작
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

// 회원 컨트롤러 끝

// 게시물 컨트롤러 시작
class ArticleController {
    fun write(rq: Rq) {
        if (loginedMember == null) {
            println("로그인 후 이용해주세요.")
            return
        }

        print("제목 : ")
        val title = readLineTrim()
        print("내용 : ")
        val body = readLineTrim()

        val id = articleRepository.addArticle(loginedMember!!.id, title, body)

        println("${id}번 게시물이 추가되었습니다.")
    }

    fun modify(rq: Rq) {
        if (loginedMember == null) {
            println("로그인 후 이용해주세요.")
            return
        }

        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
            return
        }

        val article = articleRepository.getArticleById(id)

        if (article == null) {
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }

        if (article.memberId != loginedMember!!.id) {
            println("권한이 없습니다.")
            return
        }

        print("${id}번 게시물 새 제목 : ")
        val title = readLineTrim()
        print("${id}번 게시물 새 내용 : ")
        val body = readLineTrim()

        articleRepository.modifyArticle(id, title, body)

        println("${id}번 게시물이 수정되었습니다.")
    }

    fun delete(rq: Rq) {
        if (loginedMember == null) {
            println("로그인 후 이용해주세요.")
            return
        }

        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
            return
        }

        val article = articleRepository.getArticleById(id)

        if (article == null) {
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }

        if (article.memberId != loginedMember!!.id) {
            println("권한이 없습니다.")
            return
        }

        articleRepository.deleteArticle(article)

        println("${id}번 게시물을 삭제하였습니다.")
    }

    fun detail(rq: Rq) {
        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
            return
        }

        val article = articleRepository.getArticleById(id)

        if (article == null) {
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }

        println("번호 : ${article.id}")
        println("작성날짜 : ${article.regDate}")
        println("갱신날짜 : ${article.updateDate}")
        println("제목 : ${article.title}")
        println("내용 : ${article.body}")
    }

    fun list(rq: Rq) {
        val page = rq.getIntParam("page", 1)
        val searchKeyword = rq.getStringParam("searchKeyword", "")

        val filteredArticles = articleRepository.getFilteredArticles(searchKeyword, page, 10)

        println("번호 / 작성날짜 / 작성자 / 제목 / 내용")

        for (article in filteredArticles) {
            val writer = memberRepository.getMemberById(article.memberId)!!
            val writerName = writer.nickname
            println("${article.id} / ${article.regDate} / ${writerName} / ${article.title}")
        }
    }
}
// 게시물 컨트롤러 끝

// 컨트롤러 끝

// 회원 DTO
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
)

// 회원 리포지터리
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

// 게시물 DTO
data class Article(
    val id: Int,
    val regDate: String,
    var updateDate: String,
    val memberId: Int,
    var title: String,
    var body: String
)

// 게시물 리포지터리
class ArticleRepository {
    private val articles = mutableListOf<Article>()
    private var lastId = 0

    fun deleteArticle(article: Article) {
        articles.remove(article)
    }

    fun getArticleById(id: Int): Article? {
        for (article in articles) {
            if (article.id == id) {
                return article
            }
        }

        return null
    }

    fun addArticle(memberId: Int, title: String, body: String): Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        articles.add(Article(id, regDate, updateDate, memberId, title, body))

        return id
    }

    fun makeTestArticles() {
        for (id in 1..20) {
            addArticle(id % 9 + 1, "제목_$id", "내용_$id")
        }
    }

    fun modifyArticle(id: Int, title: String, body: String) {
        val article = getArticleById(id)!!

        article.title = title
        article.body = body
        article.updateDate = Util.getNowDateStr()
    }

    fun getFilteredArticles(searchKeyword: String, page: Int, itemsCountInAPage: Int): List<Article> {
        val filtered1Articles = getSearchKeywordFilteredArticles(articles, searchKeyword)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemsCountInAPage)

        return filtered2Articles
    }

    private fun getSearchKeywordFilteredArticles(articles: List<Article>, searchKeyword: String): List<Article> {
        val filteredArticles = mutableListOf<Article>()

        for (article in articles) {
            if (article.title.contains(searchKeyword)) {
                filteredArticles.add(article)
            }
        }

        return filteredArticles
    }

    private fun getPageFilteredArticles(articles: List<Article>, page: Int, itemsCountInAPage: Int): List<Article> {
        val filteredArticles = mutableListOf<Article>()

        val offsetCount = (page - 1) * itemsCountInAPage

        val startIndex = articles.lastIndex - offsetCount
        var endIndex = startIndex - (itemsCountInAPage - 1)

        if (endIndex < 0) {
            endIndex = 0
        }

        for (i in startIndex downTo endIndex) {
            filteredArticles.add(articles[i])
        }

        return filteredArticles
    }
}

// 유틸 관련 시작
fun readLineTrim() = readLine()!!.trim()

object Util {
    fun getNowDateStr(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return format.format(System.currentTimeMillis())
    }
}
// 유틸 관련 끝