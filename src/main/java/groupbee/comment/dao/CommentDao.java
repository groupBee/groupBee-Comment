package groupbee.comment.dao;

import groupbee.comment.entity.BoardEntity;
import groupbee.comment.entity.CommentEntity;
import groupbee.comment.repository.CommemtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CommentDao {
    private CommemtRepository repository;

    //작성
    public void save(CommentEntity commentEntity) {
        repository.save(commentEntity);
    }
    // 특정 게시글의 댓글 조회
    public List<CommentEntity> findByBoardId(BoardEntity boardId) {
        return repository.findByBoardId(boardId);
    }
    // 댓글 수정
    public void update(CommentEntity commentEntity) {
        repository.save(commentEntity);
    }
    // 댓글 삭제
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
