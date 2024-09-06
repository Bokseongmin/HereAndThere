# 모피어스 교육용 프로젝트


## Reference

- [Uracle Morpheus Client Wiki](https://wiki.uracle.co.kr/madp/client/api)

## Guide

### 시작페이지 설정 방법

`assets/res/www/Manifest.xml`

```xml
 <startpage>
    <!--가상 디바이스의 경우 PC는 10.0.0.2-->
    <name>${시작페이지 주소}</name>
    <orient>default</orient>
    <orient-tablet>default</orient-tablet>
</startpage>
```

---

### JS 라이브러리

아래의 3개 파일은 같은 폴더에 두어야 한다.

- `mcore.min.js`
- `mcore.extends.js`
- `wnInterface.js`

---

### 앱 이름 변경
`res/values/strings.xml`
```xml
<string name="app_name">${앱 이름}</string>
```

---

### 앱 아이콘 변경

1. 아이콘 생성 / 다운로드

  아래의 사이트에서 아이콘을 생성/다운로드 합니다.
 
  > [아이콘 생성 사이트](https://icon.kitchen/)

2. 다운로드한 아이콘을 `res`폴더에 붙여넣기합니다.

- res/mipmap-hdpi
- res/mipmap-mdpi
- ...

3. AndroidManifest.xml 수정


`<appllcation>` 태그의 `android:icon`속성 값 수정

```xml
<application 
  android:icon="@mipmap/ic_launcher"
></application>
```
