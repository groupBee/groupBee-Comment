package groupbee.comment.controller.board;

import groupbee.comment.entity.CommentEntity;
import groupbee.comment.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/create")
    public ResponseEntity<Void> save(@RequestBody CommentEntity commentEntity) {
        commentService.save(commentEntity);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }

    // 특정 게시글의 댓글 조회
    @GetMapping("/list")
    public ResponseEntity<List<CommentEntity>> list(@RequestParam("boardId") Long boardId) {
        List<CommentEntity> comments = commentService.findByBoardId(boardId);
        return ResponseEntity.ok(comments);  // HTTP 200 OK
    }

    // 댓글 수정
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody CommentEntity commentEntity) {
        commentService.update(commentEntity);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }

    // 댓글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }
}
