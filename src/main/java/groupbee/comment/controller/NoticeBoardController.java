package groupbee.comment.controller;

import groupbee.comment.dto.NoticeBoardDto;
import groupbee.comment.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice-boards")
@RequiredArgsConstructor
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    @GetMapping
    public ResponseEntity<List<NoticeBoardDto>> getAllNoticeBoards() {
        List<NoticeBoardDto> noticeBoardDTOs = noticeBoardService.getAllNoticeBoards();
        return ResponseEntity.ok(noticeBoardDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardDto> getNoticeBoard(@PathVariable Long id) {
        NoticeBoardDto noticeBoardDTO = noticeBoardService.getNoticeBoard(id);
        return ResponseEntity.ok(noticeBoardDTO);
    }
    @GetMapping("/search")
    public ResponseEntity<List<NoticeBoardDto>> getNoticeboardsByTitle(@RequestParam String title) {
        List<NoticeBoardDto> noticeBoardDTOs = noticeBoardService.getNoticeboardsByTitle(title);
        return ResponseEntity.ok(noticeBoardDTOs);
    }

    @PostMapping
    public ResponseEntity<NoticeBoardDto> createNoticeBoard(@RequestBody NoticeBoardDto noticeBoardDTO) {
        NoticeBoardDto createdDTO = noticeBoardService.createNoticeBoard(noticeBoardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeBoardDto> updateNoticeBoard(@PathVariable Long id, @RequestBody NoticeBoardDto noticeBoardDTO) {
        NoticeBoardDto updatedDTO = noticeBoardService.updateNoticeBoard(id, noticeBoardDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        noticeBoardService.deleteNoticeBoard(id);
        return ResponseEntity.noContent().build();
    }
}