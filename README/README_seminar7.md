#필수과제 1-1
자동 로그인/자동 로그인 해제 구현

**자동 로그인**
1. 자동 로그인 버튼 Selector 생성
```xml
<!--activity_main.xml-->
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/ic_no_check" android:state_selected="false"/>
    <item android:drawable="@drawable/ic_check" android:state_selected="true"/>
</selector>
```
    - 기본 상태는 빈 회색 동그라미, 클릭할 경우 주황색 체크로 채워지도록 selector 생성
    - state_selected로 선택되었는지 확인

```kotlin
//SignInActivity.kt
binding.btnLogincheck.setOnClickListener {
    binding.btnLogincheck.isSelected = !binding.btnLogincheck.isSelected 
}
```
    - 코틀린단에서 select 되었는지 검사 필요

2. SharedPreferences 생성
```kotlin
//SOPTSharedPreferences.kt
object SOPTSharedPreferences {
    //사용하는 key값 상수화
    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"

    fun getSharedPreference(context: Context): SharedPreferences{
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
    }
    
    //파일 읽기 - 현재 자동로그인 중인지 검사
    fun getAutoLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(AUTO_LOGIN, false)
    }

    //파일 쓰기 - 자동 로그인 설정이 되어있지 않다면 자동 로그인 적용
    fun setAutoLogin(context: Context, value: Boolean) {
        getSharedPreference(context)
            .edit()
            .putBoolean(AUTO_LOGIN, value)
            .apply()
    }
    
    //key 값에 해당하는 value 삭제
    fun setLogout(context: Context) {
        getSharedPreference(context)
            .edit()
            .remove(AUTO_LOGIN)
            .clear()
            .apply()
    }
}
```
    - edit() : 파일 작성 -> put자료형(key, value) 형태로 파일에 작성할 수 있음
    - apply() : 작성한 파일 적용 / commit()
    - remove(key) : key에 해당하는 value 삭제
    - clear() : 모든 값을 지울 때 사용

3. 자동 로그인 로직 구현
```kotlin
//SignInActivity.kt
private fun initClickEvent(){
    binding.btnLogincheck.setOnClickListener {
        binding.btnLogincheck.isSelected = !binding.btnLogincheck.isSelected
        SOPTSharedPreferences.setAutoLogin(this@SignInActivity, binding.btnLogincheck.isSelected)
    }
}

private fun isAutoLogin(){
    if(SOPTSharedPreferences.getAutoLogin(this@SignInActivity)){
        Toast.makeText(this, "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        finish()
    }
}
```
    - 저장소에 저장된 값이 true이면 자동로그인 바로 다음 Acitivity로 실행되게 한다.

4. 자동 로그인 해제 로직 구현
```kotlin
//LogoutActivity.kt
private fun logoutBtnClink(){
    binding.ivLogout.setOnClickListener {
        SOPTSharedPreferences.setLogout(this)
        Toast.makeText(this, "자동 로그인이 해제되었습니다.", Toast.LENGTH_SHORT).show()
    }
}
```


-------------------
#성장과제 2-1
온보딩 화면 만들기

**온보딩**
1. Fragment 전환이 이루어질 Activity에 NavHostFragment 지정
```xml
<!--activity_on_boarding.xml-->
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/container_nav"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    app:defaultNavHost="true"
    app:layout_constraintTop_toBottomOf="@id/cl_onboarding"
    app:layout_constraintBottom_toBottomOf="parent"
    app:navGraph="@navigation/nav_onboarding"/>
```
    - name : NavHost 지정을 위한 NavHostFragment 클래스 지정
    - defaultnavHost : NavHostFragment가 백버튼 로직을 가로챌 수 있게 해주는 속성
    - navGraph : NavHostFragment를 Navigation Graph와 연결

2. Fragment 생성
3. Navigation Graph에 작업 명세
   ![navGraph](https://user-images.githubusercontent.com/102457618/173059738-92307a95-37d0-4dbc-b810-301fd2e783d9.JPG)

4. 프래그먼트 전환 로직
```kotlin
//OnBoardingFragment1.kt
binding.btnNext.setOnClickListener {
    findNavController().navigate(R.id.action_onBoardingFragment1_to_onBoardingFragment2)
}
```
    - NavController() : NavHost 내부에서 Fragment나 Activity의 전환을 담당하는 객체


--------------------
#추가 구현
+ 서버 연결 확장함수 사용
```kotlin
//ContextUtil.kt
fun <ResponseType> Call<ResponseType>.enqueueUtil(
   onSuccess: (ResponseType) -> Unit,
   onError: ((stateCode: Int) -> Unit)? = null
) {
   this.enqueue(object : Callback<ResponseType> {
      override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
         if (response.isSuccessful) {
            onSuccess.invoke(response.body() ?: return)
         } else {
            onError?.invoke(response.code())
         }
      }

      override fun onFailure(call: Call<ResponseType>, t: Throwable) {
         Log.d("NetworkTest", "error:$t")
      }

   })
}
```

+ DiffUtil / ListAdapter
```kotlin
//FollowerAdapter.kt
companion object {
   val diffUtil = object : DiffUtil.ItemCallback<ResponseGithubUserFollow>() {
      override fun areItemsTheSame(
         oldItem: ResponseGithubUserFollow,
         newItem: ResponseGithubUserFollow
      ): Boolean {
         return oldItem.userId == newItem.userId || oldItem.avatar_url == newItem.avatar_url
      }

      override fun areContentsTheSame(
         oldItem: ResponseGithubUserFollow,
         newItem: ResponseGithubUserFollow
      ): Boolean {
         return oldItem == newItem
      }
   }
}
```
   - `followerAdapter.submitList(it)` : ListAdapter 부분 업데이트 위해 `submitList()` 사용

+ BaseFragment
```kotlin
//BaseFragment.kt
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<T: ViewBinding>(private val inflate: Inflate<T>):
   Fragment() {
   private var _binding: T? = null
   val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = inflate.invoke(inflater, container, false)
      return binding.root
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }
}
```

+ 패키징