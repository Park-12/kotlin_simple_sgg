import java.text.SimpleDateFormat

fun main() {
    println("== SIMPLE SSG 시작 ==")

    boardRepository.makeTestBoards()
    memberRepository.makeTestMembers()
    articleRepository.makeTestArticles()

    val systemController = SystemController()
    val boardController = BoardController()

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
            "/board/list" -> {
                boardController.list(rq)
            }
            "/board/add" -> {
                boardController.add(rq)
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
// 세션 시작
var loginedMember: Member? = null
// 세션 끝
val memberRepository = MemberRepository()
val articleRepository = ArticleRepository()
val boardRepository = BoardRepository()
