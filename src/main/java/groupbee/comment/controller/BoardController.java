package groupbee.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시물 작성
    @Operation(
            summary = "게시물 작성",
            description = "boardData : {\n" +
                    "    \"content\" : \"내용\",\n" +
                    "    \"title\" : \"제목\",\n" +
                    "    \"readCount\":\"0\",\n" +
                    "    \"mustRead\":\"false\",\n" +
                    "    \"mustMustRead\":\"false\"\n" +
                    "}, file : {file}"
    )
    @PostMapping("/insert")
    public ResponseEntity<BoardEntity> save(@RequestPart("boardData") String boardDataJson,
                                            @RequestPart(value = "file", required = false) MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardEntity boardEntity;
        try {
            boardEntity = objectMapper.readValue(boardDataJson, BoardEntity.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return boardService.save(boardEntity, file);
    }

    // 전체 게시물 조회
    @Operation(
            summary = "전체 게시물 조회"
    )
    @GetMapping("/list")
    public ResponseEntity<List<BoardEntity>> list() {
        return  boardService.findAll();
    }

    @Operation(
            summary = "게시물 상세 조회",
            description = "@PathVariable(\"id\") Long id"
    )
    @GetMapping("/list/{id}")
    public ResponseEntity<BoardEntity> detail(@PathVariable("id") Long id) {
        return boardService.findById(id);
    }

    @Operation(
            summary = "게시물 수정",
            description = "{\n" +
                    "    \"content\" : \"수정 내용\",\n" +
                    "    \"title\" : \"수정 제목\",\n" +
                    "    \"mustRead\":\"true\",\n" +
                    "\"mustMustRead\":\"false\"\n" +
                    "}, file : {file}"
    )
    @PatchMapping("/update/{id}")
    public ResponseEntity<BoardEntity> update(@PathVariable("id") Long id,
                                              @RequestPart("boardData") String boardDataJson,
                                              @RequestPart(value = "file", required = false) MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardEntity boardEntity;
        try {
            boardEntity = objectMapper.readValue(boardDataJson, BoardEntity.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return boardService.update(id, boardEntity, file);
    }

    @Operation(
            summary = "게시물 삭제",
            description = "@PathVariable(\"id\") Long id"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean result = boardService.deleteById(id);
        if(result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
