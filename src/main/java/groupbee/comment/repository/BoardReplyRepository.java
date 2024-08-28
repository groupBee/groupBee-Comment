package groupbee.comment.repository;

import groupbee.comment.entity.BoardReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Long> {
}
