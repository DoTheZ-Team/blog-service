<div align="center">

# GLUE: Blog Service

GLUE 서비스의 Blog Service Project에 대해 소개합니다.

[![Static Badge](https://img.shields.io/badge/language-korean-blue)](./README-KR.md) [![Static Badge](https://img.shields.io/badge/language-english-red)](./README.md) [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSinging-voice-conversion%2Fsingtome-model&count_bg=%23E3E30F&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

</div>

<br>

GLUE 서비스의 Blog Service에 방문해주셔서 감사합니다. 해당 Repository는 GLUE 서비스의 블로그와 구독 CRUD 기능에 대한 내용을 담고 있습니다. 


## Contents
1. [Members](#1-members)
2. [Introduction](#2-introduction)
3. [Swagger Page](#3-swagger-page)
4. [Screen Composition](#4-screen-composition)
5. [Used Languages, Libraries, Frameworks, Tools](#5-used-languages-libraries-frameworks-tools)

## 1. Members
| Profile |         Name          |               Role               |                                             Task                                             |
| :---: |:---------------------:|:--------------------------------:|:--------------------------------------------------------------------------------------------:|
| <a href="https://github.com/kylo-dev"><img src="https://avatars.githubusercontent.com/u/103489352?v=4" height="120px"></a> | 김현겸 <br> **kylo-dev** |  DevOps, Architecture, <br/> Backend   | MSA 환경 시스템 아키텍처 설계, <br/> Infra 구축, GitOps 구축, <br/> Blog 서버 API 개발, <br/> ElasticSearch 검색 API 개발 |
| <a href="https://github.com/yeyounging"><img src="https://avatars.githubusercontent.com/u/133792082?v=4" height="120px"></a> | 공예영 <br> **yeyounging** | Frontend(Next.JS), <br/> UI/UX, Design |                                           이러고 저러고                                            |

## **2. Introduction**

Glue 서비스에서 Blog-Server는 블로그와 구독에 관한 데이터를 메인으로 처리합니다. 다른 마이크로 서비스의 데이터나 처리가 필요한 경우
**Open Feign** 라이브러리를 이용하여 다른 서비스와의 통신을 처리하였습니다.

- 회원 가입시, 개인 블로그 페이지 생성
- 블로그 마이 페이지 조회 및 수정
- 블로그 페이지 조회 
  - 최신 피드 4개와 관련 이미지, 해시태그 조회
- 블로그의 전체 게시글 목록 조회 (페이징 조회)
- 게시글 검색과 해당하는 블로그 데이터 조회 
  - ElasticSearch 검색 및 페이징 조회
- 구독 페이지
  - 구독 요청 / 취소
  - 내가 구독한 블로그의 정보와 게시글 정보 목록 조회
  - 나를 구독한 블로그의 정보와 게시글 정보 목록 조회

## **3. Swagger Page**

저희 프로젝트는 MSA 환경 서비스로 **Gateway Server에서 여러 마이크로 서비스의 Swagger를 통합하여 관리**하였습니다.

<img src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/039ab512-0e0a-47f7-b1d4-b41502311a47" width="700">

- 다른 마이크로 서비스와 통신하는 API 경우, **Swagger 'summary'에 'Open Feign'이라 작성하여 프론트엔드가 연동하는 API 구분**하였습니다.
- Blog 서비스의 흐름에 따라 **해당 API를 어느 페이지에 사용하는지, 어떤 DTO를 사용하며, example이 무엇인지 등을 구체적으로 명시**하였습니다.

<details>
    <summary>Swagger 보기</summary>

<img src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/fbb29f37-c756-4e6c-806e-a5345f1daf44" width="700">

<img src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/a20c0b04-16db-4274-b50e-92a1c9e9d0b0" width="700">

</details>


## **4. Screen Composition**

<details>
    <summary>화면 UI 보기</summary>

  1. 블로그 페이지 (Read) <br/>
  <img width="513" alt="image" src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/f4634f4e-4b3a-4196-af9e-3d83e240cfc6">

  2. 블로그 마이 페이지 (Read, Update) <br/>
  <img width="444" alt="image" src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/76184896-40a8-44dc-8380-9a1f5dae6c20">

  3. 구독 페이지 (Read) <br/>
  <img width="499" alt="image" src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/719080bb-0f1b-4c51-b864-7d2569f732a9">

  4. 검색 페이지 (Read) <br/>
  <img width="486" alt="image" src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/c6f8da12-170a-4df4-a364-e28eaaa2fe0b">

  4.1 검색 페이지 (블로그만 보기, 게시글만 보기) <br/>
  <img width="501" alt="image" src="https://github.com/DoTheZ-Team/blog-service/assets/103489352/ea4aa304-f957-4477-b030-59b6c4ce16a8">


</details>

### 4.1 Folder Structure
<details>
    <summary>프로젝트 구조 보기</summary>

```
📦 
.github
└─ ISSUE_TEMPLATE
  └─feature_request.md
  └─ pull_request_template.md
└─ workflows
  └─ blog-server.yaml
.gitignore
Dockerfile
README.md
build.gradle
└─ src
   ├─ main.java.com.justdo.plug
   │  └─ blog
   │     ├─ BlogApplication.java
   │     ├─ domain
   │     │  ├─ blog
   │     │  │  ├─ Blog.java
   │     │  │  ├─ controller
   │     │  │  │  └─ BlogController.java
   │     │  │  ├─ dto
   │     │  │  │  ├─ BlogRequest.java
   │     │  │  │  └─ BlogResponse.java
   │     │  │  ├─ repository
   │     │  │  │  └─ BlogRepository.java
   │     │  │  └─ service
   │     │  │     ├─ BlogCommandService.java
   │     │  │     └─ BlogQueryService.java
   │     │  ├─ common
   │     │  │  └─ BaseTimeEntity.java
   │     │  ├─ member
   │     │  │  ├─ MemberClient.java
   │     │  │  └─ MemberDTO.java
   │     │  ├─ post
   │     │  │  ├─ PostClient.java
   │     │  │  └─ PostResponse.java
   │     │  ├─ recommendation
   │     │  │  ├─ RecommendationClient.java
   │     │  │  └─ RecommendationDTO.java
   │     │  └─ subscription
   │     │     ├─ Subscription.java
   │     │     ├─ controller
   │     │     │  └─ SubscriptionController.java
   │     │     ├─ dto
   │     │     │  ├─ SubscriptionRequest.java
   │     │     │  └─ SubscriptionResponse.java
   │     │     ├─ repository
   │     │     │  └─ SubscriptionRepository.java
   │     │     └─ service
   │     │        ├─ SubscriptionCommandService.java
   │     │        └─ SubscriptionQueryService.java
   │     └─ global
   │        ├─ config
   │        │  ├─ FeignConfig.java
   │        │  └─ SwaggerConfig.java
   │        ├─ exception
   │        │  ├─ ApiException.java
   │        │  └─ ExceptionAdvice.java
   │        ├─ response
   │        │  ├─ ApiResponse.java
   │        │  └─ code
   │        │     ├─ BaseCode.java
   │        │     ├─ BaseErrorCode.java
   │        │     ├─ ErrorReasonDto.java
   │        │     ├─ ReasonDto.java
   │        │     └─ status
   │        │        ├─ ErrorStatus.java
   │        │        └─ SuccessStatus.java
   │        ├─ s3
   │        │  ├─ S3Service.java
   │        │  └─ S3config.java
   │        └─ utils
   │           ├─ JwtProperties.java
   │           └─ JwtProvider.java
   └─ test.java.com.jusdo.plug
      └─ blog
         └─ BlogApplicationTests.java
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)

</details>

The project's folder structure is as follows:
According to MVC pattern of Spring boot. We folderized every contents in to Model, Controllers and Service.
Each domain has own Model Contollers and Services.

## **5. Used Languages, Libraries, Frameworks, Tools**

The languages, libraries, frameworks, and tools used in the project are as follows:

- **Languages**: Java 17
- **Build**: Gradle, Docker
- **CI/CD**: Docker, Github Actions, Argo CD, Kubernetes, Helm
- **Tools**: Intellij, Datagrip, Swagger
- **Libraries and Frameworks**: SpringBoot 3.2.4, JPA
  - spring data jpa, mysql
  - spring web, valiation
  - spring cloud config, open feign
  - lombok
  - spring cloud aws
  - jjwt-api, jackson
  - springdoc-openapi
  - spring boot actuator
