![CodeRabbit Pull Request Reviews](https://img.shields.io/coderabbit/prs/github/su6net/websocket-chatting?utm_source=oss&utm_medium=github&utm_campaign=su6net%2Fwebsocket-chatting&labelColor=171717&color=FF570A&link=https%3A%2F%2Fcoderabbit.ai&label=CodeRabbit+Reviews)

# 채널 기반 실시간 채팅 API

이 프로젝트는 Slack, Discord와 유사한 채널 기반 실시간 채팅 API 프로젝트입니다. Spring Boot를 기반으로 한 백엔드와 React 프론트엔드가 REST API와 WebSocket을 통해 통신하며 실시간 채팅 기능을 제공합니다.

## 아키텍처 & 기술 스택

- **백엔드 프레임워크:** Spring Boot (Java)
- **실시간 통신:** WebSocket + STOMP  
- **데이터베이스:** PostgreSQL / MySQL (RDBMS)
- **캐시 및 메시지 브로커:** Redis (in-memory cache + Pub/Sub)
- **빌드 도구:** Gradle
- **보안 및 ORM:** Spring Security, JPA (Hibernate), Lombok

프론트엔드는 **React**로 구현되며, REST API(회원가입, 로그인, 채널 목록 등)와 WebSocket(실시간 메시지 전송)을 통해 백엔드와 상호작용합니다.

## 주요 기능

- **실시간 메시징:**  
  - WebSocket + STOMP를 통한 양방향 실시간 채팅
  - 채널별 토픽 구독 방식으로 메시지 전송 및 수신 보장
  
- **확장 가능한 아키텍처:**  
  - 무상태(stateless) 설계로 수평 확장이 용이
  - 도메인별 모듈화로 향후 마이크로서비스 전환 가능
  
- **데이터 관리:**  
  - 사용자, 채널, 메시지 및 채널-사용자 매핑 등 잘 구성된 데이터베이스 스키마
  - 인덱싱 및 파티셔닝 등 대용량 데이터 처리를 위한 설계 고려
  
- **보안:**  
  - JWT 기반 인증으로 REST API와 WebSocket 보안 처리
  - 채널 권한 관리 및 역할 기반 인가 적용
  
- **CI/CD 및 클라우드 배포:**  
  - Docker를 이용한 컨테이너화로 일관된 배포 환경 제공
  - AWS ECS, RDS, ElastiCache 등 클라우드 서비스와 GitHub Actions 기반 CI/CD 파이프라인 구성

## 프로젝트 구조

- **`/src/main/java`**: 도메인 별 모듈(예: 사용자, 채팅, 채널)로 구성된 Spring Boot 애플리케이션
- **`/src/main/resources`**: 애플리케이션 설정 파일 및 Swagger/OpenAPI 문서
- **Dockerfile & docker-compose.yml**: 컨테이너 빌드 및 배포를 위한 설정 파일
- **`.github/workflows/`**: GitHub Actions를 이용한 CI/CD 파이프라인 구성

## API 문서

- **REST API:** Swagger UI를 통해 확인 가능 (`http://localhost:8080/swagger-ui`)
- **WebSocket:** 별도의 문서와 위키에 사용 가이드 제공

## 테스트 전략

- **단위 테스트:** JUnit5와 Mockito를 이용한 서비스 및 유틸리티 테스트
- **통합 테스트:** Spring Boot Test, H2 인메모리 DB 또는 Testcontainers 활용
- **시나리오 테스트:** 실제 사용자 시나리오 기반 End-to-End 테스트
- **부하 테스트:** JMeter를 사용하여 성능 검증

## 향후 개선 사항

- [ ] **알림 시스템:** 브라우저 푸시/이메일 알림 기능 추가
- [ ] **파일 공유:** 이미지, 문서 등 파일 업로드 및 공유 기능 지원
- [ ] **채널 초대 링크:** 고유 초대 링크를 통한 간편 채널 가입
- [ ] **메시지 편집/삭제:** 사용자 메시지 수정 및 삭제 기능
- [ ] **AI 봇 통합:** 대화 요약 및 지능형 응답 기능 추가
