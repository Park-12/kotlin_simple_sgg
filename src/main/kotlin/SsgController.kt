class SsgController {
    fun build(rq: Rq) {
        println("= 게시물 상세페이지 생성 시작 =")
        val articles = articleRepository.getArticles()

        for (article in articles) {
            var fileContent = """
                <meta charset="UTF-8">
            """.trimIndent() + "\n"

            fileContent += "<h1>${article.id}번 게시물</h1>\n"

            fileContent += "<div>번호 : ${article.id}</div>\n"

            val fileName = "article_detail${article.id}.html"
            writeStrFile("ssg/${fileName}", fileContent)

            println(fileName + "파일이 생성되었습니다.")
        }
        println("= 게시물 상세페이지 생성 끝 =")

        println("= 게시물 리스트페이지 생성 시작 =")
        val boards = boardRepository.getBoards()

        for (board in boards) {
            var fileContent = """
                <meta charset="UTF-8">
            """.trimIndent() + "\n"

            fileContent += "<h1>${board.name} 게시물 리스트</h1>\n"

            for (article in articles) {
                if (article.boardId == board.id) {
                    fileContent += "<div>${article.id}번 / 제목 : ${article.title}</div>\n"
                }
            }

            val fileName = "article_list${board.code}.html"
            writeStrFile("ssg/${fileName}", fileContent)

            println(fileName + "파일이 생성되었습니다.")
        }
        println("= 게시물 리스트페이지 생성 끝 =")
    }
}