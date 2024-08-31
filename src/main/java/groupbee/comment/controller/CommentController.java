package groupbee.comment.controller;

import groupbee.comment.dto.CommentDto;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.entity.CommentEntity;
import groupbee.comment.repository.CommemtRepository;
import groupbee.comment.service.board.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "게시글의 댓글 입력",
            description = "{\n" +
                    "    \"content\":\"댓글내용\",\n" +
                    "    \"boardId\":\"게시물번호\"\n" +
                    "}"
    )
    @PostMapping("/insert")
    public ResponseEntity<Void> save(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);
        return ResponseEntity.ok().build();
    }

    // 특정 게시글의 댓글 조회
    @Operation(
            summary = "특정게시글의 댓글 출력",
            description = "@RequestParam(\"boardId\") Long boardId"
    )
    @GetMapping("/list")
    public ResponseEntity<List<CommentEntity>> list(@RequestParam(value = "boardId", required = false) Long boardId) {
        if (boardId == null) {
            // 기본 동작 (예: 전체 리스트를 반환하거나 오류를 발생시킬 수 있음)
            return ResponseEntity.badRequest().build();  // 또는 기본 처리
        }
        List<CommentEntity> comments = commentService.findByBoardId(boardId);
        return ResponseEntity.ok(comments);  // HTTP 200 OK
    }

    // 댓글 삭제
    @Operation(
            summary = "게시글의 댓글 삭제",
            description = "@PathVariable(\"id\") Long id"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean result = commentService.delete(id);
        if (result){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
