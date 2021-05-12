class ArticleRepository {
    fun getArticles(): List<Article> {
        val lastId = getLastId()

        for (id in 1..lastId) {
            val jsonStr = readStrFromFile("data/article/$id.json")

            println(jsonStr)
        }

        return mutableListOf<Article>()
    }

    fun getLastId(): Int {
        val lastId = readIntFromFile("data/article/lastId.txt")

        return lastId
    }

    fun deleteArticle(article: Article) {
        // 파일 삭제
    }

    fun getArticleById(id: Int): Article? {
        // 파일에서 객체 얻기
        return Article(1, "", "", 1, 1, "", "")
    }

    fun addArticle(boardId: Int, memberId: Int, title: String, body: String): Int {
        // 파일 생성
        return 0
    }

    fun makeTestArticles() {
        /*
        for (id in 1..20) {
            addArticle(id % 2 + 1, id % 9 + 1, "제목_$id", "내용_$id")
        }
        */
    }

    fun modifyArticle(id: Int, title: String, body: String) {
        val article = getArticleById(id)!!

        article.title = title
        article.body = body
        article.updateDate = Util.getNowDateStr()

        // 파일 수정
    }

    fun getFilteredArticles(
        boardCode: String,
        searchKeyword: String,
        page: Int,
        itemsCountInAPage: Int
    ): List<Article> {
        val filtered1Articles = getSearchKeywordFilteredArticles(getArticles(), boardCode, searchKeyword)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemsCountInAPage)

        return filtered2Articles
    }

    private fun getSearchKeywordFilteredArticles(
        articles: List<Article>,
        boardCode: String,
        searchKeyword: String
    ): List<Article> {
        if (boardCode.isEmpty() && searchKeyword.isEmpty()) {
            return articles
        }

        val filteredArticles = mutableListOf<Article>()

        val boardId = if (boardCode.isEmpty()) {
            0
        } else {
            boardRepository.getBoardByCode(boardCode)!!.id
        }

        for (article in articles) {
            if (boardId != 0) {
                if (article.boardId != boardId) {
                    continue
                }
            }

            if (searchKeyword.isNotEmpty()) {
                if (!article.title.contains(searchKeyword)) {
                    continue
                }
            }

            filteredArticles.add(article)
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