package groupbee.comment.service.board;


import groupbee.comment.dao.BoardDao;
import groupbee.comment.entity.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardDao boardDao;

    //글 작성
    public void save(BoardEntity entity){
        boardDao.save(entity);
    }

    //글 조회
    //전체글
    public List<BoardEntity> findAll(){
        return boardDao.findAll();
    }
    //게시물 하나 조회
    public BoardEntity findById(Long id){
        return boardDao.findById(id);
    }
    //글 수정
    public void update(BoardEntity entity){
        boardDao.save(entity);
    }

    //글 삭제
    public void delete(Long id){
        boardDao.delete(id);
    }

}
