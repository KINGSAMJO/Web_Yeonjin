# 필수과제 1-1
로그인 페이지 만들기(SignInActivity)

*** 아이디, 비밀번호 모두 입력이 되어있을 때만 로그인 버튼 클릭 시 HomeActivity로 이동**
*** 반대로 둘 중 하나라도 비어있다면 "아이디/비밀번호를 확인해주세요"라는 토스트 메시지 출력**
```kotlin
//로그인 버튼 클릭 시
binding.btnLogin.setOnClickListener{
    val intent = Intent(this, HomeActivity::class.java)
    //입력된 아이디와 비밀번호를 가져오는 변수
    val getID = binding.idEdit.text.toString()
    val getPW = binding.pwEdit.text.toString()
    //변수 값이 비어있는지 확인
    if(getID.isEmpty() || getPW.isEmpty()){
        Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
    }else {
        startActivity(intent)
    }
}
```
    - binding : 각 xml 레이아웃 파일마다 binding 클래스를 생성하여 view끼리 상호작용
    - intent : HomeActivity로 화면을 전환하는 객체
    - isEmpty() : 객체가 가진 값이 비어있는지 확인하는 함수
    - Toast : 사용자에게 짧은 메시지 형식으로 정보를 전달하는 팝업


*** 비밀번호 EditTextView는 입력 내용이 가려져야 합니다**
`android:inputType="textPassword"` InputType 속성 사용


*** 모든 EditTextView는 미리보기 글씨가 있어야 합니다**
`android:hint="아이디를 입력해주세요."` hint 속성 사용


*** 회원가입 버튼을 클릭 시 SignUpActivity로 이동**
```kotlin
//회원가입 버튼 클릭 시
binding.btnSignUp.setOnClickListener{
    val intent = Intent(this, SignUpActivity::class.java)
    startActivity(intent)
}
```
    - intent : SignUpActivity로 화면을 전환하는 객체
    - startActivity() : 생성한 intent 객체를 파라미터 값으로 넣어 activity 호출

![1_1(1)](https://user-images.githubusercontent.com/102457618/162617929-48a5e86c-c1e8-4360-aeac-19a57ff3101a.JPG)
![1_1(2)](https://user-images.githubusercontent.com/102457618/162617965-296d728d-9646-4a68-ad2b-cd575700661e.JPG)


# 필수과제 1-2
회원가입 페이지 만들기(SignUpActivity)

*** 이름, 아이디, 비밀번호 모두 입력이 되어있을 때만 회원가입 버튼을 눌렀을 때 다시 SignUpActivity로 이동**
*** 셋 중 하나라도 비어있다면 "입력되지 않은 정보가 있습니다"라는 토스트 메세지 출력
```kotlin
//회원가입 완료 버튼 클릭 시
binding.btnSignUpEnd.setOnClickListener{
    val intent = Intent(this, SignInActivity::class.java)
    //입력된 이름, 아이디, 비밀번호를 가져오는 변수
    val getName = binding.nameEdit.text.toString()
    val getID = binding.idEdit.text.toString()
    val getPW= binding.pwEdit.text.toString()
    //변수 값이 비어있는지 확인
    if(getName.isEmpty() || getID.isEmpty() || getPW.isEmpty()){
        Toast.makeText(this, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
    }else{
        finish()
    }
}
```
    - intent : SignUpActivity로 화면을 전환하는 객체
    - finish() : 쌓였던 액티비티 스택 중 현재 스택을 종료하는 함수, Intent 직전의 액티비티로 돌아감


*** 비밀번호 EditTextView는 입력 내용이 가려져야 합니다 **
`android:inputType="textPassword"` InputType 속성 사용


*** 모든 EditTextView는 미리보기 글씨가 있어야 합니다**
`android:hint="아이디를 입력해주세요."` hint 속성 사용

![1_2(1)](https://user-images.githubusercontent.com/102457618/162617978-1371a077-492c-4202-9fc9-45e02d7e5289.JPG)
![1_2(2)](https://user-images.githubusercontent.com/102457618/162617987-4fc75bdd-b257-42b3-bfed-3a175fb6be5f.JPG)


# 필수과제 1-3
자기소개 페이지 만들기(HomeActivity)

*** ImageView, TextView 활용 **
*** 이름, 나이, MBTI 등 자기소개를 적어주세요 **
    - 이미지를 넣고 싶을 경우 drawable 파일에 넣을 사진을 복사하고 src 속성을 사용하여 불러온다.

![1_3](https://user-images.githubusercontent.com/102457618/162619268-ac480d4b-4add-45d7-8870-afcbd0e2c3fa.JPG)


# 성장과제 2-1
화면이동 + 입력한 데이터 이동

*** 회원가입(SignUpActivity)이 성공한다면 이전 로그인 화면으로 돌아옵니다 **
*** 이때 회원가입에서 입력했던 아이디와 비밀번호가 입력되어 있어야 합니다 **
1. SignInActivity
```kotlin
//회원가입 후 입력한 아이디와 비밀번호를 가져올 launcher
val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    if(it.resultCode == RESULT_OK){
        //전달받을 변수
        val id = it.data?.getStringExtra("Id")?:""
        val password = it.data?.getStringExtra("Password")?:""
        //값을 가져와서 SignInActivity의 id와 password EditText 자리에 입력
        binding.idEdit.setText(id)
        binding.pwEdit.setText(password)
    }
}
```
    - registerForAcitivityResult() : 액티비티의 결과에 대한 콜백을 등록, 다른 액티비티에 보낼 launcher를 생성
    - resultCode : 작업 수행 후 결과가 어떠한지 반환(RESULT_OK, RESULT_CANCELED)
    - getStringExtra() : 값을 String 형태로 받아오는 메소드
    - ? : null safe operator, 앞의 변수가 null이 아닐때만 오른쪽 함수가 수행되고 null이면 null을 반환함

```kotlin
binding.btnSignUp.setOnClickListener{
    val intent = Intent(this, SignUpActivity::class.java)
    activityResultLauncher.launch(intent)
        }
```
    - activityResultLauncher.launch(intent) : 회원가입 버튼을 누를 경우 Intent 객체를 launcher에 실어서 실행
    - SignUpActivity 실행

2. SignUpActivity
```kotlin
//전달할 데이터를 putExtra()에 담음
intent.putExtra("Id", binding.idEdit.text.toString())
intent.putExtra("Password", binding.pwEdit.text.toString())
//결과가 성공인지, 실패인지 설정
setResult(RESULT_OK, intent)
//회원가입 화면을 종료
if(!isFinishing){
    finish()
}
```
    - putExtra() : 전달할 값의 이름, 전달할 값을 인자로 하여 설정한 곳으로 값을 전달하는 메소드
    - setResult() : resultCode, Intent 객체를 인자로 하여 콜백함수 실행

![2_1(1)](https://user-images.githubusercontent.com/102457618/162618015-ff05fa2e-0a87-4373-b3f0-641d1c52afc3.JPG)
![2_1(2)](https://user-images.githubusercontent.com/102457618/162618027-0d37dce0-994e-49f0-8f68-6405e3fccb98.JPG)


# 성장과제 2-2
자기소개 스크롤뷰 + 사진 1:1 비율 삽입
*** 자기소개 스크롤뷰 **
```xml
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="0dp"
    app:layout_constraintTop_toBottomOf="@id/my_address"
    app:layout_constraintBottom_toBottomOf="parent"
    android:layout_margin="20dp">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/introduce"
        android:textSize="20dp"/>
</ScrollView>
```
    - ScrollView는 내부에 한가지의 View만 가질 수 있음
    - 따라서 여러개의 View를 가지고 싶을 때는 내부에 ViewGroup을 새로 만들어야 함


*** 사진 1:1 비율 삽입 **
`app:layout_constraintDimensionRatio="1:1"` 삽입할 사진 View 비율 지정
`app:layout_constraintWidth_percent="0.3"` 사진 자체 크기 비율 지정
`android:scaleType="fitXY"` 뷰 사이즈에 딱 맞게 사진 비율 조정

![2_2](https://user-images.githubusercontent.com/102457618/162619316-4e4d8dc0-e3b3-46ed-b4da-914eb5fc999d.JPG)