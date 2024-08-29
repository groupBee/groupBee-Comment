package groupbee.comment.service.board;

import groupbee.comment.dao.CommentDao;
import groupbee.comment.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentDao commentDao;

    public void save(CommentEntity commentEntity){
        commentDao.save(commentEntity);
    }

    // 특정 게시글의 댓글 조회
    public List<CommentEntity> findByBoardId(Long boardId) {
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
