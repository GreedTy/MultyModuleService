# Micro Service Server
- API
- Batch

# domain / dao / core / test
- domain : Service Layer 모듈
- dao : Data Access 모듈
- core : dao, domain, controller 에 사용되는 공통모듈관리
- test : test 코드에 사용되는 유틸 클래스메소드 관리
- 모듈 별 상하관계 
  - API, Batch > domain > dao > core 
  - test 는 test코드에 사용되므로 testImplementation 으로 import
