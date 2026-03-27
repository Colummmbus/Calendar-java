# Calendar-java

## 프로젝트 소개
Java Swing을 활용하여 구현한 데스크톱 기반 일정 관리 프로그램입니다.  
사용자는 날짜별로 일정을 추가하고 조회할 수 있으며, 간단한 GUI를 통해 직관적으로 일정을 관리할 수 있습니다.

## 주요 기능
- 날짜별 일정 조회
- 일정 추가 (제목, 시간, 내용)
- 일정 삭제
- 일정 리스트 관리
- GUI 기반 인터페이스 (Java Swing)

## 기술 스택

- **Language**: Java
- **GUI**: Java Swing
- **IDE**: IntelliJ IDEA


## 프로젝트 구조
- src
  - CalendarGUI.java  # 메인 GUI 및 이벤트 처리
  - ScheduleManager.java  # 일정 관리 로직
  - HumanScheduleManager.java  # 사용자 일정 관리 확장
  - ScheduleEvent.java  # 일정 데이터 객체

## 핵심 설계
- 일정 데이터를 객체(ScheduleEvent)로 분리하여 관리하도록 설계했습니다.
- 일정 추가, 삭제 등의 로직을 ScheduleManager에서 처리하여 GUI와 로직을 분리했습니다.
- 사용자 인터페이스와 데이터 처리 로직을 분리하여 유지보수성을 고려했습니다.
