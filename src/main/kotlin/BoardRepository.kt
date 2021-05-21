class BoardRepository {
    private val boards = mutableListOf<Board>()

    private var lastId = 0

    fun getBoards(): List<Board> {
        return boards
    }

    fun makeTestBoards() {
        makeBoard("공지", "notice")
        makeBoard("자유", "free")
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

    fun makeBoard(name: String, code: String): Int {
        val id = getLastId() + 1
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        val board = Board(id, regDate, updateDate, name, code)

        writeStrFile("data/board/${board.id}.json", board.toJson())

        setLastId(id)

        return id
    }

    fun getLastId(): Int {
        val lastId = readIntFromFile("data/board/lastId.txt", 0)

        return lastId
    }

    private fun setLastId(newLastId: Int) {
        writeIntFile("data/board/lastId.txt", newLastId)
    }

    fun getBoardById(id: Int): Board? {
        for (board in boards) {
            if (board.id == id) {
                return board
            }
        }

        return null
    }
}