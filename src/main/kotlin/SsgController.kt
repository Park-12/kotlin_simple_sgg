class SsgController {
    fun build(rq: Rq) {
        val articles = articleRepository.getArticles()

        for (article in articles) {
            val fileContent = "hi"

            val fileName = "article_detail_${article.id}.html"
            writeStrFile("ssg/article_detail_${article.id}.html", fileContent)

            println(fileName + "파일이 생성되었습니다.")
        }
    }
}