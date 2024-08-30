package groupbee.comment.repository;

import groupbee.comment.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository <BoardEntity,Long> {
}
