package groupbee.comment.dao;

import groupbee.comment.entity.BoardEntity;
import groupbee.comment.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BoardDao {

    private BoardRepository repository;

    //글 작성
    public void save(BoardEntity entity){
        repository.save(entity);
    }

    //글 조회
    //전체글
    public List<BoardEntity> findAll(){
        return repository.findAll();
    }
    //게시물 하나 조회
    public BoardEntity findById(Long id){
        return repository.getReferenceById(id);
    }
    //글 수정
    public void update(BoardEntity entity){
        repository.save(entity);
    }

    //글 삭제
    public void delete(Long id){
        repository.deleteById(id);
    }
}
