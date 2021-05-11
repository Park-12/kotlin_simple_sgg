class BoardRepository {
    private val boards = mutableListOf<Board>()
    private var lastId = 0

    fun makeTestBoard() {
        addBoard("공지", "notice")
        addBoard("자유", "free")
    }
    fun getFilteredBoards(): List<Board> {
        return boards
    }

    fun getBoardByName(name: String): Board? {
        for (board in boards) {
            if (board.name == name) {
                return board
            }
        }
        return null
    }

    fun getBoardByCode(code: String): Board? {
        for (board in boards) {
            if (board.code == code) {
                return board
            }
        }
        return null
    }

    fun addBoard(name: String, code: String): Int {
        val id = ++ lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        boards.add(Board(id, regDate, updateDate, name, code))

        return id
    }
}