## Yesterday's Trip

### 기능
#### 지역 팔로잉
<img src="시도_팔로잉.gif" width="600" height="400"/>

<img src="관리자_컨텐츠_추가_알람.gif" width="600" height="400"/>

관심있는 지역을 팔로우 할 수 있으며, SSE(Server Sent Event)를 활용해서 팔로우한 시도의 새 컨텐츠의 알람 조회

#### 회원가입

<img src="회원가입.gif" width="600" height="400"/>

회원 가입 검증 절차
- 이메일 중복 검증
- 해당 이메일로 발송된 인증 코드 검증

#### 로그인
<img src="로그인.gif" width="600" height="400"/>

#### 임시비밀번호 발급
<img src="임시_비밀번호_발급.gif" width="600" height="400"/>

비밀번호 분실시, 이메일로 발송된 임시 비밀번호로 로그인

#### 컨텐츠 조회, 리뷰 작성

<img src="베스트여행지_클러스터링_리뷰.gif" width="600" height="400"/>

지도 페이지에서는 '시도, 구군, 컨텐츠' or 키워드 조회
조회한 컨텐츠에 대한 리뷰 작성

<img src="리뷰_작성.gif" width="600" height="400"/>

### 기획
네이버 지도, 구석구석(축제 일정 제공 서비스)를 사용하며 더 필요하다고 느낀 것
1. 관광지, 축제에 대한 리뷰가 부족
2. 가려고 하는 관광지 근처에 묶어서 갈만한 여행지도 직접 검색 필요
3. 관심있는 지역, 관광지, 축제에 대한 새로운 이벤트도 직접 검색 필요

보완이 필요한 부분을 아래와 같이 해결
1. 네이버 맵과 함께 관광지, 축제에 대한 리뷰 작성 및 조회
2. 지역별 평점이 높고 가까운 관광지 리스트 추천
3. 사용자는 관심있는 지역 팔로우, 지역 관리자가 새로운 관광지 추가시 팔로워들에게 실시간 알림 제공

### 사용 기술
<img src="img.png" width="900" height="600"/>

#### 1. back
- spring boot
- spring security
- mysql
- redis
- jwt
- docker
- ec2
- s3

#### 2. front
- vue.js
- pinia
- axios
- scss

### ERD
<img src="yesterday_erd.PNG" width="800" height="800"/>
