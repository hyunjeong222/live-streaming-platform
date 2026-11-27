# live-streaming-platform

라이브 방송 플랫폼 백엔드 개인 프로젝트다.

## 프로젝트 정보

- 기술: Spring Boot 4.2 / Java 25 / Gradle(Kotlin 문법)
  - 웹: Spring Web MVC, Validation
  - DB: MySQL, Spring Data JPA, Flyway(`src/main/resources/db/migration`)
  - 기타: Lombok, DevTools
  - 시험: JUnit 5, Testcontainers(MySQL)
- 기본 패키지: `com.livestream`
- 설정 파일: `src/main/resources/application.yaml`
- 도메인: `docs/decisions/README.md`의 폴더 목록을 따른다.

### 노션 정리

- 페이지: 노션 "방송 플랫폼 개인 프로젝트" (https://app.notion.com/p/425af63e32458039901bda2412c3d11d)
- 표: 페이지 맨 앞의 인라인 표. 표 한 행이 문서 하나다.
- 표 이름: 정하지 않음
- 열과 적을 내용: 정하지 않음

## 규칙

- 주석, 커밋 메시지, 문서는 한국어로 쓴다. 클래스·변수·메서드 이름은 영어로 짓는다.
- 브랜치, 커밋 메시지, PR은 `docs/CONVENTION.md`를 따른다. 파일을 고치기 전에 이슈를 만들고 `dev`에서 `<유형>/<이슈번호>` 브랜치부터 딴다.
- 커밋과 PR에는 AI 표기(`Co-Authored-By`, `Generated with`)를 넣지 않는다.
- 엔티티를 추가하거나 고치면 Flyway 마이그레이션을 함께 추가한다(`migration` 스킬). 이미 적용된 마이그레이션은 고치지 않는다.
- `.env`, 비밀번호, 토큰은 커밋하지 않는다. 환경변수를 추가하거나 바꾸면 `.env.example`도 같이 고친다.
- Docker 이미지 같은 외부 버전은 번호로 고정하고, 로컬과 시험은 같은 버전을 쓴다.

## 규칙과 스킬 위치

- 협업 규칙 원문: `docs/CONVENTION.md` (브랜치, 커밋, 이슈·PR, 시험, 기록, 코드와 설정)
- 설계 결정 기록(ADR): `docs/decisions/README.md`
- 스킬
  - `commit`: 커밋 메시지 작성과 커밋
  - `pr`: PR 전 `/code-review` 점검, PR 초안 작성과 생성
  - `migration`: Flyway 마이그레이션 파일 작성
  - `session-summary`: 작업의 설계 결정을 ADR로 남김
  - `session-context`: 이전 ADR을 찾아 대화에 불러옴
  - `notion-summary`: 함께 정한 중요한 것들을 노션 표에 정리
- ADR 문서는 쓰기 전에 훅(`.claude/hooks/scan-adr-before-write.sh`)이 민감정보를 검사한다. `jq`가 필요하다.

## 작업 흐름

GitHub이나 노션에 남는 행동(이슈, 댓글, 커밋, push, PR, 머지, 노션 쓰기)은 매번 승인을 받는다. 그 밖의 결정은 되묻지 않고 기본값으로 진행한 뒤 어떤 가정을 했는지 알린다.

1. **이슈 먼저.** 이슈 없이 코드를 고치지 않는다. 제목은 `[도메인] 설명`, 본문은 이슈 템플릿 양식대로 쓴다. 초안과 붙일 `type:` 라벨을 보여주고 승인받아 올린 뒤, `gh issue view`로 다시 읽어 확인하고 보고한다. 작업 중 범위가 달라지면 승인받아 이슈 본문도 고친다.
2. **브랜치.** 최신 `dev`를 받아 `git switch --no-track -c <유형>/<이슈번호> origin/dev`로 만든다. `main`, `dev`에는 직접 커밋하지 않는다.
3. **작업.** 버그 수정은 재현 시험을 먼저 만들어 고치기 전 코드에서 실패하는 것을 보인 뒤 고친다. 시험은 커버리지 100%가 아니라 주요 흐름과 권한 경우를 확인한다.
4. **결과 보고.** 바뀐 코드를 diff 블록으로 보여주고 시험 결과를 알린다.
5. **커밋.** `commit` 스킬대로 파일 목록과 메시지를 먼저 보여주고 승인받는다.
6. **기록.** 남길 만한 설계 결정이 있으면 `session-summary`로 ADR 초안을, 함께 정한 중요한 것이 있으면 `notion-summary`로 노션 초안을 보여준다. 없으면 없다고만 말한다. `/clear`나 `/compact` 전에 이 단계를 먼저 한다.
7. **PR.** push 전에 `./gradlew test`가 통과하는지 확인한다. `pr` 스킬대로 `/code-review`로 점검해 머지를 막는 문제만 고치고, 초안을 보여준 뒤 승인받아 push와 PR 생성을 한다. 올린 뒤 `gh pr view`로 다시 읽어 확인하고 보고한다.
8. **머지.** 내가 말할 때만 squash merge로 합친다. `dev` → `main`은 일반 병합이다.

## 로컬 실행

1. `.env.example`을 `.env`로 복사하고 값을 채운다.
2. `docker compose up -d`로 MySQL을 띄운다(계정과 DB는 첫 실행 때 자동 생성된다).
3. 애플리케이션을 실행한다. 시험(`./gradlew test`)은 Testcontainers를 쓰므로 Docker만 켜져 있으면 된다.
