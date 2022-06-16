#필수과제 1-1
로그인/회원가입 API 연동

**로그인 및 회원가입 서버 통신 구현**
1. 서버 Request / Response 객체 설계
```kotlin
//RequestSignUp.kt
data class RequestSignUp(
    val name: String,
    @SerializedName("email")
    val id: String,
    val password: String
)
```
    - SerializedName : Json의 키 값과 클래스의 변수명이 다른 경우 이름표를 붙여야 맵핑이 가능

```kotlin
//ResponseSignUp.kt
data class ResponseSignUp(
    val status: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int
    )
}
```


2. Retrofit Interface 설계
```kotlin
//SoptService.kt
interface SoptService {
    //로그인
    @POST("auth/signin")
    fun postLogin(
        @Body body: RequestSignIn
    ): Call<ResponseSignIn>

    //회원가입
    @POST("auth/signup")
    fun postSignUp(
        @Body body: RequestSignUp
    ): Call<ResponseSignUp>
}
```
    - @POST : http method 설정, 어떤 URL를 식별할 것인가
    - @Body : 요청 보낼 정보를 담는 곳
    - Call<type> : 동기적 / 비동기적으로 type을 받아오는 객체
    - CallBack<type> : type 객체를 받아왔을 때 프로그래머의 행동

```kotlin
//ServiceCreator.kt
object ServiceCreator {
    private const val BASE_URL = "http://13.124.62.236/"
    
    //Retrofit 객체 생성
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    //인터페이스 객체를 create
    val soptService: SoptService = retrofit.create(SoptService::class.java)
}
```
    - Retrofit.Builder() : 레트로핏 빌더 생성 (생성자 호출)
    - baseUrl(BASE_URL) : 빌더 객체의 baseUrl 호출, 서버의 메인 Url 전달
    - addConverterFactory(GsonConverterFactory.create()) : gson 컨버터 연동
    - gson? JAVA에서 Json을 파싱하고 생성하기 위해 사용되는 오픈소스
    - build() : Retrofit 객체 반환


3. Callback 등록하여 통신 요청
```kotlin
//SignUpActivity.kt
private fun signUpNetwork(){
    //서버에 요청을 보내기 위한 RequestData 생성
    val requestSignUp = RequestSignUp(
        name = binding.nameEdit.text.toString(),
        id = binding.idEdit.text.toString(),
        password = binding.pwEdit.text.toString()
    )
    
    //Retrofit이 만들어준 interface 구현체에 접근하여 Call 객체를 받아옴
    val call: Call<ResponseSignUp> = ServiceCreator.soptService.postSignUp(requestSignUp)
    
    //call 객체에 enqueue를 호출하여 실제 서버 통신을 비동기적으로 요청
    call.enqueue(object : Callback<ResponseSignUp>{
        override fun onResponse(
            call: Call<ResponseSignUp>,
            response: Response<ResponseSignUp>
        ) {
            //status code가 200~300 사이일 때
            if(response.isSuccessful){
                //분기 처리
                when(response.code()){
                    201 -> Toast.makeText(this@SignUpActivity,"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.putExtra("edit_id", binding.idEdit.text.toString())
                intent.putExtra("edit_pw", binding.pwEdit.text.toString())
                setResult(RESULT_OK, intent)
                finish()
                
            } else { //status code가 400~ 일 때
                when(response.code()){
                    409 -> Toast.makeText(this@SignUpActivity, "이미 존재하는 유저입니다.", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@SignUpActivity, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        //비동기 통신 중 발생한 에러 처리
        override fun onFailure(call: Call<ResponseSignUp>, t: Throwable) {
            Log.e("NetworkTest", "error:$t")
        }
    })
}
```

![postman](https://user-images.githubusercontent.com/102457618/168279589-bc27273f-2fe4-41f4-921f-86d20eab7986.JPG)
![toast](https://user-images.githubusercontent.com/102457618/168279967-5d257cf5-44f1-4237-8f04-098d791cdd98.gif)


-----------------
# 성장과제 2-1
Github API 연동해서 리스트로 띄우기

**Follower 리스트 연동하기**
1. 서버 연결 위한 객체 설계
```kotlin
//ResponseGithubUserFollow.kt
data class ResponseGithubUserFollow(
    @SerializedName("login")
    val userId : String,
    val avatar_url : String
)
```
    - follower 리스트는 get으로 받아오기 때문에 따로 request 객체가 필요하지 않다.
    - userId : 유저의 아이디
    - avatar_url : 유저의 프로필 사진 링크

2. Retrofit Interface 설계
```kotlin
//GithubApiService.kt
interface GithubApiService {
    @GET("/users/yeoncheong/followers")
    fun fetchGithubFollowers(
    ): Call<List<ResponseGithubUserFollow>>
}
```
    - @GET : 정보 조회 용도로 READ
    - url이 /users/{username}/followers 이므로 사이에 내 깃허브 아이디를 넣어준다.
    - 유저 Follower 정보는 객체가 아닌 리스트로 날아오기 때문에 꼭 List로 받아줘야 한다.

```kotlin
//GithubApiCreator.kt
object GithubApiCreator {
    private const val BASE_URL = "https://api.github.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubApiService : GithubApiService = retrofit.create(GithubApiService::class.java)
}
```

3. Callback 등록하여 통신 요청
```kotlin
//FollowerFragment.kt
private fun followingNetwork() {
    val call: Call<List<ResponseGithubUserFollow>> =
        GithubApiCreator.githubApiService.fetchGithubFollowers()

    call.enqueueUtil(
        onSuccess = {
            it.let {
                followerAdapter.submitList(it)
            }
        },
        onError = {
            when (it) {
                304 -> Toast.makeText(requireContext(), "Not modified", Toast.LENGTH_SHORT).show()
                401 -> Toast.makeText(requireContext(), "Requires authentication", Toast.LENGTH_SHORT).show()
                403 -> Toast.makeText(requireContext(), "Forbidden", Toast.LENGTH_SHORT).show()
            }
        }
    )
}
```
    - 보낼 것이 없기 때문에 서버에 요청을 보내기 위한 RequestData 생성할 필요 X
    - Call 객체 사용 시 꼭 List에 담아서 보낼 것
    - 유저 정보와 레포지토리 받아올 때도 같은 방식으로 하면 된다.