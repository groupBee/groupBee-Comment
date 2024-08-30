package groupbee.comment.controller.board;

import groupbee.comment.entity.BoardEntity;
import groupbee.comment.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시물 작성
    @PostMapping("/create")
    public ResponseEntity<Void> save(@RequestBody BoardEntity boardEntity) {
        boardService.save(boardEntity);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }

    // 전체 게시물 조회
    @GetMapping("/list")
    public List<BoardEntity> list() {
        return  boardService.findAll();// HTTP 200 OK
    }

    // 게시물 상세 조회
    @GetMapping("/detail")
    public ResponseEntity<BoardEntity> detail(@RequestParam("id") Long id) {
        BoardEntity board = boardService.findById(id);
        if (board != null) {
            return ResponseEntity.ok(board);  // HTTP 200 OK
        } else {
            return ResponseEntity.notFound().build();  // HTTP 404 Not Found
        }
    }

    // 게시물 수정
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody BoardEntity boardEntity) {
        boardService.update(boardEntity);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }

    // 게시물 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        boardService.delete(id);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }
}
