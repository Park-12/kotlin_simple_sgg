data class Article(
    val id: Int,
    val regDate: String,
    var updateDate: String,
    val boardId: Int,
    val memberId: Int,
    var title: String,
    var body: String
)