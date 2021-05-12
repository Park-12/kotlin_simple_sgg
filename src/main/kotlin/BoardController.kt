class BoardController {
    fun list(rq: Rq) {
        println("번호 / 생성날짜 / 이름 / 코드")

        val boards = boardRepository.getFilteredBoards()

        for(board in boards) {
            println("${board.id} / ${board.regDate} / ${board.name} / ${board.code}")
        }
    }

    fun add(rq: Rq) {
        print("게시판 이름 : ")
         val name = readLineTrim()

         val boardName = boardRepository.getBoardByName(name)

         if(boardName != null) {
             println("'$name'은/는 이미 존재하는 게시판 이름입니다.")
             return
        }
        print("게시판 코드 : ")
        val code = readLineTrim()

        val boardCode = boardRepository.getBoardByCode(code)

        if(boardCode != null) {
            println("'$code'은/는 이미 존재하는 게시판 코드입니다.")
            return
        }

        val id = boardRepository.makeBoard(name, code)
        println("${id}번 게시판이 생성되었습니다.")
    }
}