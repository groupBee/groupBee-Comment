# 게시판 시스템 📝

## 프로젝트 개요
이 프로젝트는 **사용자 친화적인 게시판 시스템**을 구축하여, 게시글 작성 및 댓글 관리를 원활하게 처리할 수 있는 플랫폼을 제공합니다.  
**Spring Boot**와 **JPA**, **PostgreSQL**을 기반으로 하여 안정적이고 효율적인 CRUD 작업을 수행하며, API를 통해 게시글과 댓글을 관리할 수 있도록 설계되었습니다.  
이 시스템은 직관적인 인터페이스와 유연한 데이터 관리를 제공하여, 사용자들이 쉽게 게시글을 작성하고 조회할 수 있는 환경을 제공합니다. 또한, **Swagger**를 통해 API 문서화 및 테스트를 용이하게 하였습니다.

---

## 기술 스택

- **Spring Boot**: 백엔드 애플리케이션 프레임워크로, 효율적인 개발 환경을 제공합니다.
- **JPA (Hibernate)**: 데이터베이스와의 매끄러운 상호작용을 지원하는 ORM(Object Relational Mapping) 기술.
- **PostgreSQL**: 관계형 데이터베이스로, 안전하고 확장성 있는 데이터 저장을 위해 사용되었습니다.
- **Swagger**: API 문서화 및 테스트를 위한 도구로, 직관적인 인터페이스를 제공합니다.

---

## 주요 기능

### 게시글 관리

1. **게시글 작성**
   - API: `POST /api/board/insert`
   - 게시글과 파일을 첨부하여 새로운 게시물을 작성할 수 있습니다.

2. **전체 게시글 조회**
   - API: `GET /api/board/list`
   - 모든 게시글을 조회하여, 작성일자와 조회수 등을 포함한 리스트를 제공합니다.

3. **특정 게시글 조회**
   - API: `GET /api/board/{id}`
   - 특정 게시글의 상세 내용을 조회합니다.

4. **게시글 수정**
   - API: `PUT /api/board/update/{id}`
   - 게시글의 제목, 내용 등을 수정할 수 있습니다.

5. **게시글 삭제**
   - API: `DELETE /api/board/delete/{id}`
   - 특정 게시글을 삭제할 수 있습니다.

---

### 댓글 관리

1. **댓글 작성**
   - API: `POST /api/comment/insert`
   - 특정 게시글에 댓글을 작성합니다.

2. **게시글별 댓글 조회**
   - API: `GET /api/comment/list?boardId={boardId}`
   - 특정 게시글에 달린 댓글을 조회합니다.

3. **댓글 삭제**
   - API: `DELETE /api/comment/delete/{id}`
   - 특정 댓글을 삭제합니다.

---

## 프로젝트 구조

### 주요 컨트롤러

1. **BoardController**
   - 게시글과 관련된 CRUD 작업을 처리하며, 파일 업로드 기능을 포함하여 다양한 게시글 관리를 지원합니다.

2. **CommentController**
   - 댓글과 관련된 CRUD 작업을 담당하며, 각 게시글에 달린 댓글을 관리합니다.

---

## 결론
이 게시판 시스템은 **안정적이고 효율적인 데이터 처리**를 지원하며, 게시글과 댓글 관리에 필요한 다양한 기능을 직관적으로 제공합니다.  
**Spring Boot**와 **JPA**, **PostgreSQL**을 기반으로 설계된 이 시스템은 확장성과 안정성을 고려하여 구축되었으며, **Swagger**를 통해 API 문서화 및 테스트를 손쉽게 진행할 수 있습니다.
