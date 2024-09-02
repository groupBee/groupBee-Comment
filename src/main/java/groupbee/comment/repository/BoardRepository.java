package groupbee.comment.repository;

import groupbee.comment.dto.BoardDto;
import groupbee.comment.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository <BoardEntity,Long> {
    @Query("SELECT new groupbee.comment.dto.BoardDto(b, COUNT(c.id)) " +
            "FROM BoardEntity b LEFT JOIN b.comments c " +
            "GROUP BY b.id")
    List<BoardDto> findAllByIdWithCommentCount();
}
