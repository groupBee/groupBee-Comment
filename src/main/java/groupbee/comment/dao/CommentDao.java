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
    public List<CommentEntity> findByBoardId(Long boardId) {
        return repository.findByBoardId(boardId);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
