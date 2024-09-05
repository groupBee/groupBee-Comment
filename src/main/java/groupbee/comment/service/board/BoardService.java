package groupbee.comment.service.board;

import feign.FeignException;
import groupbee.comment.dao.BoardDao;
import groupbee.comment.dto.BoardDto;
import groupbee.comment.entity.BoardEntity;
import groupbee.comment.service.feign.FeignClient;
import groupbee.comment.service.minio.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardDao boardDao;
    private final FeignClient feignClient;
    private final MinioService minioService;

    // 글 작성
    public ResponseEntity<BoardEntity> save(BoardEntity boardEntity, List<MultipartFile> files) {
        try {
            // 파일 리스트 처리
            if (files != null && !files.isEmpty()) {
                List<String> originalFileNames = new ArrayList<>();
                List<String> fileNames = new ArrayList<>();

                // 각 파일 업로드 및 파일명 저장
                for (MultipartFile file : files) {
                    String originalFileName = file.getOriginalFilename();
                    String fileName = minioService.uploadFile("groupbee", "board", file);

                    originalFileNames.add(originalFileName);
                    fileNames.add(fileName);
                }

                boardEntity.setOriginalFileNames(originalFileNames);
                boardEntity.setFiles(fileNames);
            }

            // 사용자 정보 가져오기
            Map<String, Object> response = feignClient.getUserInfo();
            String potalId = (String) response.get("potalId");
            String writer = (String) response.get("name");

            boardEntity.setMemberId(potalId);
            boardEntity.setWriter(writer);
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

    // 게시물 상세 조회
    public ResponseEntity<BoardEntity> findById(Long id) {
        try {
            Optional<BoardEntity> optionalBoard = boardDao.findById(id);
            if (optionalBoard.isPresent()) {
                BoardEntity boardEntity = optionalBoard.get();
                boardEntity.setReadCount(boardEntity.getReadCount() + 1); // 조회수 증가
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

    // 글 수정
    public ResponseEntity<BoardEntity> update(Long id, BoardEntity entity, List<MultipartFile> files) {
        try {
            BoardEntity boardEntity = boardDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Board not found"));

            entity.setId(id);
            if (files != null && !files.isEmpty()) {
                List<String> originalFileNames = new ArrayList<>();
                List<String> fileNames = new ArrayList<>();

                // 기존 파일 삭제
                if (boardEntity.getFiles() != null) {
                    for (String fileName : boardEntity.getFiles()) {
                        minioService.deleteFile("groupbee", "board", fileName);
                    }
                }

                // 새 파일 업로드
                for (MultipartFile file : files) {
                    String originalFileName = file.getOriginalFilename();
                    String fileName = minioService.uploadFile("groupbee", "board", file);
                    originalFileNames.add(originalFileName);
                    fileNames.add(fileName);
                }

                entity.setOriginalFileNames(originalFileNames);
                entity.setFiles(fileNames);
            } else {
                // 파일 변경이 없으면 기존 파일 정보 유지
                entity.setFiles(boardEntity.getFiles());
                entity.setOriginalFileNames(boardEntity.getOriginalFileNames());
            }

            // 업데이트 시간 설정
            entity.setUpdateDate(LocalDateTime.now());
            entity.setTitle(entity.getTitle() != null ? entity.getTitle() : boardEntity.getTitle());
            entity.setContent(entity.getContent() != null ? entity.getContent() : boardEntity.getContent());
            entity.setMustRead(entity.getMustRead() != null ? entity.getMustRead() : boardEntity.getMustRead());
            entity.setMustMustRead(entity.getMustMustRead() != null ? entity.getMustMustRead() : boardEntity.getMustMustRead());
            entity.setCreateDate(boardEntity.getCreateDate());
            entity.setMemberId(boardEntity.getMemberId());
            entity.setWriter(boardEntity.getWriter());
            entity.setReadCount(boardEntity.getReadCount());

            BoardEntity updateEntity = boardDao.update(entity);
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

    // 글 삭제
    public boolean deleteById(Long id) {
        Optional<BoardEntity> entity = boardDao.findById(id);
        if (entity.isPresent()) {
            BoardEntity boardEntity = entity.get();
            if (boardEntity.getFiles() != null) {
                for (String fileName : boardEntity.getFiles()) {
                    minioService.deleteFile("groupbee", "board", fileName);
                }
            }
            boardDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    // 전체 게시물 조회 - 댓글 수 포함
    public ResponseEntity<List<BoardDto>> findBoardByIdWithCommentCount() {
        try {
            List<BoardDto> boardList = boardDao.findAllByIdWithCommentCount();
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
