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