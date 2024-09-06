package groupbee.comment.service.board;

import feign.FeignException;
import groupbee.comment.dao.CommentDao;
import groupbee.comment.dto.CommentDto;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.entity.CommentEntity;
import groupbee.comment.repository.BoardRepository;
import groupbee.comment.service.feign.FeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentDao commentDao;
    private final FeignClient feignClient;
    private final BoardRepository boardRepository;

    public void save(CommentDto commentDto){
        try {
            BoardEntity boardEntity = boardRepository.findById(commentDto.getBoardId())
                    .orElseThrow(() -> new RuntimeException("Board not found"));

            CommentEntity commentEntity;
            if (commentDto.getId() != null) {
                // 기존 댓글 업데이트
                commentEntity = commentRepository.findById(commentDto.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                commentEntity.setContent(commentDto.getContent());
                commentEntity.setUpdateDate(LocalDateTime.now());
            } else {
                // 새로운 댓글 추가
                commentEntity = new CommentEntity();
                Map<String, Object> response = feignClient.getUserInfo(); 
                commentEntity.setMemberId((String) response.get("potalId"));
                commentEntity.setWriter((String) response.get("name"));
                commentEntity.setUpdateDate(LocalDateTime.now());
                commentEntity.setContent(commentDto.getContent());
                commentEntity.setBoard(boardEntity);
            } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 게시글의 댓글 조회
    public List<CommentEntity> findByBoardId(Long boardId) {
        return commentDao.findByBoardId(boardId);
    }
    //삭제
    public boolean delete(Long id){
        if (commentDao.existsById(id)) {
            commentDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
