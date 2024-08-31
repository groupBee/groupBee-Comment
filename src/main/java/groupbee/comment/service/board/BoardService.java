package groupbee.comment.service.board;


import feign.FeignException;
import groupbee.comment.dao.BoardDao;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.repository.BoardRepository;
import groupbee.comment.service.feign.FeignClient;
import groupbee.comment.service.minio.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardDao boardDao;
    private final FeignClient feignClient;
    private final MinioService minioService;

    //글 작성
    public ResponseEntity<BoardEntity> save(BoardEntity boardEntity, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                String fileName = minioService.uploadFile("groupbee", "board", file);

                // 파일 이름 설정만으로 충분함 (경로 검사 제거)
                boardEntity.setFile(fileName);
                boardEntity.setOriginalFileName(originalFileName);
            }

            // 사용자 정보 가져오기
            Map<String, Object> response = feignClient.getUserInfo();
            String potalId = (String) response.get("potalId");

            boardEntity.setMemberId(potalId);
            boardEntity.setUpdateDate(LocalDateTime.now());

            // 엔티티 저장
            BoardEntity saveEntity = boardDao.save(boardEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveEntity);

        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();

        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //전체글
    public ResponseEntity<List<BoardEntity>> findAll() {
        try {
            return ResponseEntity.ok(boardDao.findAll());
        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //게시물 상세 조회
    public ResponseEntity<BoardEntity> findById(Long id) {
        try {
            Optional<BoardEntity> optionalBoard = boardDao.findById(id);
            if (optionalBoard.isPresent()) {
                BoardEntity boardEntity = optionalBoard.get();
                boardEntity.setReadCount(optionalBoard.get().getReadCount() + 1); // 조회수 증가
                boardDao.save(boardEntity);
                return ResponseEntity.status(HttpStatus.OK).body(boardEntity);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //글 수정
    public ResponseEntity<BoardEntity> update(Long id, BoardEntity entity, MultipartFile file) {
        try {
            BoardEntity boardEntity = boardRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Board not found"));

            entity.setId(id);
            if (file != null && !file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                // 기존 파일 삭제
                if (boardEntity.getFile() != null) {
                    minioService.deleteFile("groupbee", "board", boardEntity.getFile());
                }
                // 새 파일 업로드
                String fileName = minioService.uploadFile("groupbee", "board", file);
                entity.setOriginalFileName(originalFileName);
                entity.setFile(fileName);
            } else {
                // 파일 변경이 없으면 기존 파일 정보 유지
                entity.setFile(boardEntity.getFile());
                entity.setOriginalFileName(boardEntity.getOriginalFileName());
            }
            // 업데이트 시간 설정
            entity.setUpdateDate(LocalDateTime.now());
            entity.setTitle(entity.getTitle() != null ? entity.getTitle() : boardEntity.getTitle());
            entity.setContent(entity.getContent() != null ? entity.getContent() : boardEntity.getContent());
            entity.setMustRead(entity.getMustRead() != null ? entity.getMustRead() : boardEntity.getMustRead());
            entity.setMustMustRead(entity.getMustMustRead() != null ? entity.getMustMustRead() : boardEntity.getMustMustRead());
            entity.setCreateDate(boardEntity.getCreateDate());
            entity.setMemberId(boardEntity.getMemberId());
            entity.setReadCount(boardEntity.getReadCount());

            BoardEntity updateEntity = boardDao.save(entity);
            return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //글 삭제
    public boolean deleteById(Long id) {
        if (boardDao.findById(id).isPresent()) {
            Optional<BoardEntity> entity = boardDao.findById(id);
            if (entity.get().getFile() != null) {
                minioService.deleteFile("groupbee", "board", entity.get().getFile());
            }
            boardDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
