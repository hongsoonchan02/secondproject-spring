# HR Management System (근태 & 인사 관리 시스템)


![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Hibernate-59666C?logo=hibernate&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?logo=springsecurity&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-Annotation-red)
![Validation](https://img.shields.io/badge/Validation-Jakarta%20Validation-blue)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?logo=swagger&logoColor=black)
![JavaMailSender](https://img.shields.io/badge/JavaMailSender-SMTP-007396)
![spring-dotenv](https://img.shields.io/badge/spring--dotenv-env%20config-3EAAAF)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)
![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20S3%20%7C%20CloudFront-232F3E?logo=amazonaws)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-blue?logo=githubactions)

---

## 프로젝트 소개

실무 환경을 고려하여 설계한 **근태 및 인사 관리 시스템**

- 프론트/백엔드 분리 구조
- AWS 기반 인프라
- CI/CD (OIDC 기반 배포 파이프라인)

---

## 시스템 아키텍처
![제목 없는 다이어그램](https://github.com/user-attachments/assets/7b892743-d296-4c54-8212-dfbb147f3199)

---

## ERD

<img width="1290" height="883" alt="image" src="https://github.com/user-attachments/assets/cd440776-f46e-40b4-8afc-2c6f767b71b7" />

---

## 주요 기능

- 로그인 / 인증
- 직원 관리
- 부서 관리
- 근태 관리
- 휴가 관리

---

## 트러블 슛팅 기록


---

## 기술 스택

### Backend
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- Lombok
- Validation
- Swagger
- JavaMailSender
- dotenv

### Infra
- AWS EC2
- AWS S3
- AWS CloudFront
- MySQL
  
---

## 팀원 및 역할
| 이름 | 역할 | 기능 구현 |
|------|------|------|
| 홍순찬 ([hongsoonchan02](https://github.com/hongsoonchan02)) | 팀장 | CICD 파이프라인 구축, 부서 관리 기능 구현 |
| 고은별 ([KoreaNirsa](https://github.com/채워주세요)) | 팀원 | AWS EC2(DB) 구축, 직원 관리 기능 구현 |
| 이소정 ([BeanCan0235](https://github.com/BeanCan0235)) | 팀원 | AWS EC2(WAS) 구축, 로그인 & 비밀번호 찾기 & 대시보드 기능 구현 |
| 임수현 ([soo97](https://github.com/soo97)) | 팀원 | S3 + CloudFront 구축, 근태 관리 & 휴가 관리 기능 구현 |
