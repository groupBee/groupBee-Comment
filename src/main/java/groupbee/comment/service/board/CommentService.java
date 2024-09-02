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

            CommentEntity commentEntity = new CommentEntity();

            Map<String, Object> response = feignClient.getUserInfo(); // feign 을 통해 UserInfo 가져오기
            commentEntity.setMemberId((String) response.get("potalId")); // UserInfo 에서 potalId 가져오기
            commentEntity.setWriter((String) response.get("name")); // UserInfo 에서 name 가져오기
            commentEntity.setUpdateDate(LocalDateTime.now()); // 업데이트 시간 설정
            commentEntity.setContent(commentDto.getContent()); // 댓글 내용 설정
            commentEntity.setBoard(boardEntity); // 게시글 ID 설정

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
