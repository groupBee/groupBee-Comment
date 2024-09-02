package groupbee.comment.dao;

import groupbee.comment.dto.BoardDto;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BoardDao {
    private BoardRepository repository;

    //글 작성
    public BoardEntity save(BoardEntity entity){
        return repository.save(entity);
    }
    //글 조회
    //전체글
    public List<BoardEntity> findAll(){
        return repository.findAll();
    }
    // 게시물 하나 조회
    public Optional<BoardEntity> findById(Long id){
        return repository.findById(id);
    }
    // 글 수정
    public BoardEntity update(BoardEntity entity){
        return repository.save(entity);
    }
    // 글 삭제
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<BoardDto> findAllByIdWithCommentCount() {
        return repository.findAllByIdWithCommentCount();
    }
}
