data class Article(
    val id: Int,
    val regDate: String,
    var updateDate: String,
    val boardId: Int, // 어떤 게시판인지 구분
    val memberId: Int, // 누가 썻는지 구분
    var title: String,
    var body: String
)