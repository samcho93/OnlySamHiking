# OnlySam 등산

GPS 기반 등산 기록 앱으로, 산행 경로 추적, 사진 촬영, 워터마크 생성, GPX 내보내기/가져오기 등 등산에 필요한 모든 기능을 제공합니다.

---

## 개요

OnlySam 등산은 Android 전용 등산 기록 앱입니다. 네이버 지도와 구글 지도를 지원하며, 산행 중 실시간으로 경로, 고도, 거리, 속도, 칼로리 등을 기록합니다. 산 봉우리 근처에서 음성 알림을 제공하고, 촬영한 사진에 산행 데이터를 워터마크로 합성하여 저장할 수 있습니다. GPX 형식으로 내보내기/가져오기를 지원하여 Relive 등 외부 앱과 연동할 수 있습니다.

---

## 주요 기능

### 실시간 GPS 산행 추적
- 포그라운드 서비스 기반으로 앱이 백그라운드에 있어도 안정적으로 GPS 기록
- 실시간 거리, 고도, 속도, 칼로리, 누적 상승/하강 고도 표시
- 일시정지/재개 기능으로 휴식 시간 제외 가능

### 듀얼 맵 지원
- **네이버 지도** (기본): 국내 산악 지형에 최적화
- **구글 지도**: 전환 버튼으로 즉시 변경 가능
- 나침반 모드 (진행방향/고정방향 전환)
- 전체화면 지도 보기

### 사진 기록
- 산행 중 카메라 버튼으로 현재 위치에 사진 촬영 및 저장
- EXIF GPS 데이터 자동 추출 및 지도/차트에 썸네일 표시
- 하나의 산행 기록에 여러 장의 사진 저장 가능
- 기본 사진 폴더에도 자동 저장

### 워터마크 사진 생성
- 대표 사진에 산행 데이터를 워터마크로 합성
- 워터마크 정보: 산 이름, 날짜, 거리, 시간, 최고 고도, 칼로리, 누적 상승/하강, 고도 그래프
- 썸네일 그리드에서 사진 선택 후 생성
- 생성된 워터마크 사진은 사진 리스트 맨 앞에 표시
- 갤러리에도 별도 저장 가능

### 산 봉우리 알림
- 한국 주요 산 봉우리 데이터베이스 내장 (mountains.json)
- 봉우리 반경 50m 진입 시 TTS 음성 알림: "근처에 산 봉우리가 있습니다. {산 이름}, 해발 {높이}미터"

### 산행 기록 관리
- 나의 산행 리스트에서 전체 기록 열람
- 탭 구분: 앱 기록 / 사용자 등록 기록
- 상세 페이지에서 경로 지도, 고도 그래프, 사진, 통계 정보 한 페이지에 표시
- 메모 작성 및 수정 기능
- 기록 삭제 기능

### 고도 그래프
- MPAndroidChart 기반 고도 변화 그래프
- 거리(km) 기준 X축, 고도(m) 기준 Y축
- 사진 촬영 위치에 썸네일 오버레이 표시
- 부드러운 베지어 곡선 렌더링

### GPX 내보내기
- GPX 1.1 형식으로 산행 데이터 내보내기
- Relive 앱과 호환되는 형식
- 파일 저장 또는 외부 앱으로 공유 선택 가능

### GPX 가져오기 (사용자 등록)
- GPX 파일을 가져와서 산행 기록으로 등록
- GPX 파일 선택 시 `.gpx` 확장자 필터링
- 가져오기 후 자동으로 스마트폰 사진 검색
  - GPX 시간 범위 전후 1시간 내 촬영된 사진 탐색
  - GPS 좌표가 GPX 경로 500m 이내인 사진 자동 매칭
  - Content URI + EXIF 폴백으로 Android 10+ 호환
  - 매칭된 사진 썸네일 목록에서 선택/해제 가능
- 수동 사진 추가도 가능
- 경로 지도 및 고도 그래프 미리보기

### 산 지도
- 방문한 산을 지도에 마커로 표시
- 방문 횟수 및 통계 확인

---

## 기술 스택

### 플랫폼
| 항목 | 내용 |
|------|------|
| 플랫폼 | Android |
| 언어 | Kotlin 2.0.21 |
| 최소 SDK | API 26 (Android 8.0 Oreo) |
| 타겟 SDK | API 36 |
| JDK | Java 17 |
| 빌드 도구 | Gradle 8.9 (Kotlin DSL) |
| AGP | 8.7.3 |

### 핵심 라이브러리
| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| AndroidX Core KTX | 1.15.0 | Kotlin 확장 함수 |
| AndroidX AppCompat | 1.7.0 | 하위 호환성 |
| Material Components | 1.12.0 | Material Design UI |
| ConstraintLayout | 2.2.0 | 반응형 레이아웃 |
| Room Database | 2.6.1 | SQLite ORM (로컬 데이터 저장) |
| Lifecycle | 2.8.7 | ViewModel, LiveData, Service |
| Naver Maps SDK | 3.21.0 | 네이버 지도 |
| Google Maps | 19.0.0 | 구글 지도 |
| Google Maps Utils | 3.8.2 | 지도 유틸리티 |
| Play Services Location | 21.3.0 | FusedLocationProvider GPS |
| Glide | 4.16.0 | 이미지 로딩 및 캐싱 |
| MPAndroidChart | 3.1.0 | 고도 그래프 차트 |
| Kotlinx Coroutines | 1.8.1 | 비동기 처리 |
| ExifInterface | 1.3.7 | 사진 EXIF 메타데이터 |

---

## 프로젝트 구조

```
OnlySamHiking/
├── app/
│   ├── build.gradle.kts              # 앱 모듈 빌드 설정
│   ├── proguard-rules.pro            # ProGuard 난독화 규칙
│   └── src/main/
│       ├── AndroidManifest.xml       # 앱 매니페스트
│       ├── assets/
│       │   └── mountains.json        # 한국 산 봉우리 데이터
│       ├── java/com/onlysamhiking/app/
│       │   ├── OnlySamHikingApp.kt   # Application 클래스
│       │   │
│       │   ├── data/                 # 데이터 계층
│       │   │   ├── db/               # Room 데이터베이스
│       │   │   │   ├── HikingDatabase.kt      # DB 정의 (v3)
│       │   │   │   ├── HikingRecordDao.kt     # 산행 기록 DAO
│       │   │   │   ├── TrackPointDao.kt       # GPS 경로점 DAO
│       │   │   │   └── HikingPhotoDao.kt      # 사진 DAO
│       │   │   ├── model/            # 데이터 모델
│       │   │   │   ├── HikingRecord.kt        # 산행 기록 엔티티
│       │   │   │   ├── TrackPoint.kt          # GPS 경로점 엔티티
│       │   │   │   ├── HikingPhoto.kt         # 사진 엔티티
│       │   │   │   ├── Mountain.kt            # 산 봉우리 모델
│       │   │   │   └── MapProvider.kt         # 지도 제공자 enum
│       │   │   └── repository/       # 리포지토리
│       │   │       ├── HikingRepository.kt    # 산행 데이터 리포지토리
│       │   │       └── MountainRepository.kt  # 산 데이터 리포지토리
│       │   │
│       │   ├── service/              # 서비스 계층
│       │   │   └── HikingTrackingService.kt   # GPS 추적 포그라운드 서비스
│       │   │
│       │   ├── ui/                   # UI 계층
│       │   │   ├── main/             # 메인 화면
│       │   │   │   ├── MainActivity.kt        # 런처 (권한 요청)
│       │   │   │   └── MapActivity.kt         # 지도 + 산행 추적 UI
│       │   │   ├── history/          # 산행 기록 목록
│       │   │   │   ├── HistoryActivity.kt     # 기록 목록 화면
│       │   │   │   └── HistoryAdapter.kt      # 기록 리스트 어댑터
│       │   │   ├── detail/           # 산행 상세
│       │   │   │   ├── HikingDetailActivity.kt    # 상세 페이지
│       │   │   │   └── FullscreenMapActivity.kt   # 전체화면 지도
│       │   │   ├── photo/            # 사진 뷰어
│       │   │   │   └── PhotoViewerActivity.kt     # 전체화면 사진 + 워터마크
│       │   │   ├── mountain/         # 산 지도
│       │   │   │   └── MountainMapActivity.kt     # 방문 산 지도
│       │   │   ├── import/           # GPX 가져오기
│       │   │   │   └── ImportGpxActivity.kt       # GPX 등록 화면
│       │   │   └── map/              # 지도 관리
│       │   │       ├── MapManagerInterface.kt     # 지도 추상 인터페이스
│       │   │       ├── NaverMapManager.kt         # 네이버 지도 구현체
│       │   │       └── GoogleMapManager.kt        # 구글 지도 구현체
│       │   │
│       │   └── util/                 # 유틸리티
│       │       ├── LocationUtils.kt       # 거리 계산 (Haversine)
│       │       ├── CalorieCalculator.kt   # 칼로리 계산 (MET 기반)
│       │       ├── ExifHelper.kt          # EXIF GPS 추출
│       │       ├── GpxExporter.kt         # GPX 내보내기
│       │       ├── GpxImporter.kt         # GPX 가져오기/파싱
│       │       ├── WatermarkHelper.kt     # 워터마크 사진 생성
│       │       └── PermissionHelper.kt    # 런타임 권한 관리
│       │
│       └── res/                      # 리소스
│           ├── layout/               # 레이아웃 XML (13개)
│           ├── drawable/             # 아이콘, 배경 (18개)
│           ├── values/
│           │   ├── strings.xml       # 문자열 (한국어)
│           │   ├── colors.xml        # 색상 정의
│           │   ├── themes.xml        # 테마 (Material3)
│           │   └── dimens.xml        # 치수 상수
│           └── xml/
│               └── file_paths.xml    # FileProvider 경로 설정
│
├── build.gradle.kts                  # 루트 프로젝트 빌드 설정
├── settings.gradle.kts               # 프로젝트 설정 (저장소 정의)
├── gradle.properties                 # Gradle 속성 (API 키 포함)
└── CLAUDE.md                         # Claude 개발 지침
```

---

## 데이터베이스 구조

Room Database v3 기반으로 3개 엔티티를 사용합니다.

### HikingRecord (산행 기록)
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long (PK) | 자동 생성 |
| mountainName | String | 산/봉우리 이름 |
| startTime | Long | 시작 시간 (ms) |
| endTime | Long | 종료 시간 (ms) |
| distance | Double | 총 이동 거리 (m) |
| maxAltitude | Double | 최고 고도 (m) |
| minAltitude | Double | 최저 고도 (m) |
| elevationGain | Double | 누적 상승 고도 (m) |
| elevationLoss | Double | 누적 하강 고도 (m) |
| avgSpeed | Double | 평균 속도 (km/h) |
| maxSpeed | Double | 최고 속도 (km/h) |
| calories | Int | 소모 칼로리 (kcal) |
| startLat/Lng | Double | 출발 좌표 |
| endLat/Lng | Double | 도착 좌표 |
| memo | String | 메모 |
| isUserImported | Boolean | 사용자 등록 여부 |

### TrackPoint (GPS 경로점)
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long (PK) | 자동 생성 |
| recordId | Long (FK) | 산행 기록 ID (CASCADE) |
| latitude | Double | 위도 |
| longitude | Double | 경도 |
| altitude | Double | 해발 고도 (m) |
| speed | Float | 이동 속도 (m/s) |
| accuracy | Float | GPS 정확도 (m) |
| timestamp | Long | 기록 시간 (ms) |

### HikingPhoto (사진)
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long (PK) | 자동 생성 |
| recordId | Long (FK) | 산행 기록 ID (CASCADE) |
| filePath | String | 파일 경로 |
| latitude | Double | 촬영 위도 |
| longitude | Double | 촬영 경도 |
| altitude | Double | 촬영 고도 |
| timestamp | Long | 촬영 시간 (ms) |

---

## 화면 구성

### 1. 메인 화면 (MainActivity)
- 앱 시작 시 위치, 카메라, 알림 권한 요청
- 권한 승인 후 자동으로 지도 화면으로 이동

### 2. 지도 화면 (MapActivity)
- 네이버/구글 지도 전환 버튼
- 나침반 모드 토글 (진행방향/고정)
- **시작** 버튼: 산행 기록 시작 (포그라운드 서비스)
- **일시정지/재개** 버튼: 휴식 시 일시정지
- **사진** 버튼: 현재 위치에 사진 촬영
- **종료** 버튼: 산행 기록 종료 및 저장
- 실시간 통계 패널: 거리, 시간, 고도, 속도, 칼로리
- 하단 메뉴: 나의 산행, 산 지도

### 3. 산행 기록 목록 (HistoryActivity)
- 탭 구분: **앱 기록** / **사용자 등록**
- 산행 카드 목록 (산 이름, 날짜, 거리, 시간)
- 카드 클릭 시 상세 페이지로 이동
- 스와이프 삭제 지원
- **+** 버튼으로 GPX 파일 가져오기

### 4. 산행 상세 (HikingDetailActivity)
- **경로 지도**: 네이버 지도에 산행 경로 표시
- **고도 그래프**: 거리 기반 고도 변화 차트 (사진 위치 썸네일 포함)
- **통계**: 거리, 시간, 최고 고도, 누적 상승/하강, 칼로리 등
- **사진 갤러리**: 가로 스크롤 썸네일 (워터마크 사진 맨 앞 정렬)
- **메모**: 산행에 대한 메모 작성/수정
- **버튼**: GPX 내보내기, 워터마크 저장, 전체화면 지도, 기록 삭제

### 5. 전체화면 지도 (FullscreenMapActivity)
- 전체 화면에서 산행 경로와 사진 마커 확인
- 네이버 지도 기반

### 6. 사진 뷰어 (PhotoViewerActivity)
- 사진 전체화면 보기
- 워터마크 추가 버튼

### 7. 산 지도 (MountainMapActivity)
- 방문한 산을 지도에 마커로 표시
- 방문 횟수 및 산행 통계

### 8. GPX 가져오기 (ImportGpxActivity)
- GPX 파일 선택 (`.gpx` 확장자 필터)
- 경로 지도 및 고도 그래프 미리보기
- 자동 사진 검색 (시간/위치 매칭)
- 수동 사진 추가
- 산행 이름 편집 및 저장

---

## 앱 권한

| 권한 | 용도 |
|------|------|
| `ACCESS_FINE_LOCATION` | GPS 위치 추적 |
| `ACCESS_COARSE_LOCATION` | 대략적 위치 |
| `ACCESS_BACKGROUND_LOCATION` | 백그라운드 GPS 추적 |
| `FOREGROUND_SERVICE` | 산행 추적 포그라운드 서비스 |
| `FOREGROUND_SERVICE_LOCATION` | 위치 기반 포그라운드 서비스 |
| `CAMERA` | 사진 촬영 |
| `READ_MEDIA_IMAGES` | 갤러리 사진 접근 (Android 13+) |
| `READ_EXTERNAL_STORAGE` | 갤러리 사진 접근 (Android 12 이하) |
| `WRITE_EXTERNAL_STORAGE` | 사진 저장 (Android 9 이하) |
| `ACCESS_MEDIA_LOCATION` | 사진 GPS 메타데이터 접근 |
| `POST_NOTIFICATIONS` | 산행 추적 알림 |
| `INTERNET` | 지도 타일 로딩 |
| `WAKE_LOCK` | 화면 꺼짐 시 GPS 유지 |
| `VIBRATE` | 진동 알림 |

---

## 설정 및 빌드

### 사전 준비
1. Android Studio (최소 Hedgehog 이상)
2. JDK 17
3. Android SDK API 36

### API 키 설정
`gradle.properties` 파일에 API 키를 추가합니다:
```properties
NAVER_MAP_CLIENT_ID=<네이버 지도 클라이언트 ID>
GOOGLE_MAPS_API_KEY=<구글 지도 API 키>
```

### 빌드
```bash
# 디버그 빌드
./gradlew assembleDebug

# 릴리즈 빌드
./gradlew assembleRelease
```

### 설치
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 사용법

### 산행 기록하기
1. 앱 실행 후 권한 승인
2. 지도 화면에서 **시작** 버튼 클릭
3. 산행 중 **사진** 버튼으로 현재 위치에 사진 촬영
4. 필요 시 **일시정지/재개** 사용
5. 산행 완료 후 **종료** 버튼 클릭 → 자동 저장

### 워터마크 사진 만들기
1. **나의 산행** → 기록 선택 → 상세 페이지
2. **워터마크 저장** 버튼 클릭
3. 썸네일 그리드에서 원하는 사진 선택
4. 워터마크가 합성된 사진이 자동 저장 및 갤러리 등록

### GPX 내보내기
1. 산행 상세 페이지에서 **GPX 내보내기** 버튼 클릭
2. **파일로 저장** 또는 **외부 앱으로 연결** 선택
3. Relive 등 외부 앱에서 바로 사용 가능

### GPX 가져오기 (사용자 등록)
1. **나의 산행** → **사용자 등록** 탭 → **+** 버튼
2. GPX 파일 선택
3. 경로/고도 확인 후 자동 검색된 사진 선택/해제
4. 산행 이름 편집 후 **저장**

### 지도 전환
- 지도 화면에서 **지도 전환** 버튼으로 네이버/구글 지도 교체
- **나침반** 버튼으로 진행방향 모드 전환

---

## 핵심 알고리즘

### 거리 계산
Haversine 공식을 사용하여 두 GPS 좌표 간 직선 거리를 계산합니다.

### 칼로리 계산
MET(Metabolic Equivalent of Task) 기반으로 경사도와 체중을 고려한 소모 칼로리를 산출합니다.

### 봉우리 근접 감지
현재 GPS 위치와 산 봉우리 데이터베이스(mountains.json)의 좌표를 비교하여 반경 50m 이내 진입 시 TTS 음성 알림을 제공합니다.

### 사진 자동 매칭 (GPX 가져오기)
1. GPX 시간 범위 ±1시간 내 촬영된 사진을 MediaStore에서 조회
2. Content URI를 통한 EXIF GPS 좌표 추출 (Android 10+ 호환)
3. 사진 GPS와 GPX 경로점 간 거리 계산 (500m 이내 매칭)
4. 경로점 ~200개 샘플링으로 성능 최적화

---

## 라이선스

이 프로젝트는 개인 사용 목적으로 개발되었습니다.
