package groupbee.comment.repository;

import groupbee.comment.entity.NoticeBoardReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardReplyRepository extends JpaRepository<NoticeBoardReplyEntity, Long> {

}