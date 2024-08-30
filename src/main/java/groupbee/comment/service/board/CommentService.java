package groupbee.comment.service.board;

import feign.FeignException;
import groupbee.comment.dao.CommentDao;
import groupbee.comment.dto.CommentDto;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.entity.CommentEntity;
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

    public void save(CommentDto commentDto){
        try {
            CommentEntity commentEntity = new CommentEntity();
            Map<String, Object> response = feignClient.getUserInfo();
            commentEntity.setMemberId((String) response.get("potalId"));
            commentEntity.setUpdateDate(LocalDateTime.now());
            commentEntity.setContent(commentDto.getContent());

            commentDao.save(commentEntity);
            ResponseEntity.ok().build();
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
    public List<CommentEntity> findByBoardId(BoardEntity boardId) {
        return commentDao.findByBoardId(boardId);
    }

    //수정
    public void update(CommentEntity commentEntity){
        commentDao.update(commentEntity);
    }

    //삭제
    public void delete(Long id){
        commentDao.delete(id);
    }

}
