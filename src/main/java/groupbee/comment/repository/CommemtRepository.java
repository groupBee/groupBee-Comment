package groupbee.comment.repository;

import groupbee.comment.entity.BoardEntity;
import groupbee.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommemtRepository extends JpaRepository <CommentEntity,Long> {
    // 특정 게시글의 댓글 조회
    List<CommentEntity> findByBoardId(Long boardId);

}
