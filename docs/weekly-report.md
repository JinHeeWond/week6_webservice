# Weekly Report

> 구현 코드와 실제 오류 해결 내용을 바탕으로 작성한 초안입니다. 학습 내용, 소감과 건의사항은 제출 전 본인의 경험에 맞게 검토합니다.

## Key Learning

1. `@Controller`가 반환한 View 이름에 대응하는 Thymeleaf 템플릿으로 웹 화면을 만들고, `Model`로 서버 데이터를 전달하는 흐름을 학습했습니다.
2. HTML Form의 입력 이름과 DTO 필드를 일치시키고 `@ModelAttribute`로 등록 데이터를 전달하는 방식을 학습했습니다.
3. POST 처리 후 redirect하여 GET으로 목록을 조회하는 PRG 패턴과 계층별 책임 분리를 학습했습니다.

## Web UI Flow

```text
Browser: GET /books
→ BookWebController.list(Model)
→ BookService.findAll()
→ BookRepository.findAll()
→ MemoryBookRepository의 Map 조회
→ Model에 books 저장
→ Thymeleaf books/list.html
→ Browser에 HTML 응답
```

`th:each`는 책 목록을 반복하며, `th:text`는 각 책의 필드를 화면에 출력합니다.

## Form Flow

```text
HTML Form: title, author, price, category, isbn 입력
→ POST /books
→ @ModelAttribute BookRequest 바인딩
→ BookService.create(request)
→ MemoryBookRepository.save(book)
→ ID 부여 및 Map 저장
→ redirect:/books
→ GET /books
→ 등록된 책을 목록에 출력
```

## Problem & Solution

실행 시 `Port 8080 was already in use` 오류가 발생했습니다. 포트 조회 결과 다른 Java 프로세스가 8080을 사용하고 있었습니다. `application.properties`에 `server.port=8081`을 추가하여 포트를 변경했고, Tomcat이 8081에서 정상 시작되는 로그를 확인했습니다.

컴파일 오류도 있었습니다. `com.example.bookwebui` import가 실제 패키지인 `org.example.webui.bookwebui`와 달랐고, Controller의 잘못된 제네릭 선언이 `BookService` 클래스를 가리고 있었습니다. import를 맞추고 제네릭 선언을 제거한 뒤 전체 빌드와 테스트가 통과했습니다.

## Code Review

```java
@PostMapping("/books")
public String create(@ModelAttribute BookRequest request) {
    bookService.create(request);
    return "redirect:/books";
}
```

이 코드는 Form 데이터 바인딩, Service 호출, 등록 후 redirect라는 핵심 흐름을 연결합니다. Controller는 HTTP 요청과 화면 이동을 담당하고 실제 데이터 생성과 저장은 하위 계층에 위임합니다.

## AI Usage

AI를 활용하여 기존 코드의 컴파일 오류를 분석하고 수정했으며, 포트 충돌을 진단하고 목록·등록 통합 테스트를 추가했습니다. README와 이 Weekly Report의 초안 작성에도 AI를 활용했습니다.

확인·수정한 항목은 실제 패키지에 맞는 import, `BookService` 주입 타입, GET/POST URL, Form의 action과 input name, Model 변수 `books`, Thymeleaf 표현식입니다. `./gradlew clean build`로 자동 검증했습니다.

HTML에는 Bootstrap 5를 적용했습니다. 브라우저에서의 최종 화면 확인과 새로고침 중복 등록 확인 결과는 제출 전 직접 확인하여 보완합니다.

## Reflection

PRG 패턴에서 목록 새로고침은 GET 요청이므로 Form POST를 다시 실행하지 않는다는 점을 정리했습니다. 메모리 저장소는 서버가 재시작되면 데이터가 사라지므로, 향후 데이터베이스를 도입했을 때 Repository 구현을 어떻게 교체할 수 있는지 더 학습하고 싶습니다.

## 건의사항

작성 초안: 로컬 실행부터 배포까지 연결되는 예시와 자주 발생하는 포트 충돌 해결 방법이 함께 제공되면 실습에 도움이 될 것 같습니다.
