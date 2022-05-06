# 필수과제 1-1
###과제에 폰트 적용하기

>1.사용할 폰트 다운로드
>2.font 폴더 생성 후 다운 받은 폰트 import
>3.fontFamily 파일 만들고 fontWeight 적용
>4.폰트를 사용할 xml 파일의 fontFamily 속성에 파일 위치 입력

```xml
<!--nanum_gothic_kr.xml-->
<font-family xmlns:android="http://schemas.android.com/apk/res/android">
    <font
        android:font="@font/nanum_gothic"
        android:fontWeight="200"/>
</font-family>
```

![1_1](https://user-images.githubusercontent.com/102457618/167153076-cfff948c-0a97-4e7d-8a5b-e419f49500ba.JPG)


-------------
# 필수과제 1-2
###ProfileFragment 만들기

**ProfileFragment**
```kotlin
//ProfileFragment.kt
childFragmentManager.beginTransaction()
    .add(R.id.main_fragment, followerfragment)
    .commit()
```
    - supportFragmentManager : **액티비티**에서 하위 프래그먼트를 관리할 때 사용
    - childeFragmentManager : **프래그먼트**에서 하위 프래그먼트를 관리할 때 사용
    - parentFragmentManager : **프래그먼트**에서 내 상위 프래그먼트의 FragmentManager에 접근할 때 사용


**Button에 Selector 활용하기**
1. 클릭되었을 때
```xml
<!--shape_clicked.xml-->
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <stroke
        android:width="2dp"
        android:color="@color/layout"/>
    <solid android:color="@color/layout_light"/>
    <corners android:radius="5dp"/>
</shape>
```
    - stroke : 도형의 테두리
    - solid : 도형의 색깔
    - corners : 도형의 굴곡

2. 클릭되지 않았을 때
```xml
<!--shape_unclicked.xml-->
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <stroke
        android:width="2dp"
        android:color="@color/notchoose"/>
    <solid android:color="@color/white"/>
    <corners android:radius="5dp"/>
</shape>
```

3. 적용
```xml
<!--selector_profile_button.xml-->
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/shape_clicked" android:state_selected="true"/>
    <item android:drawable="@drawable/shape_unclicked" android:state_selected="false"/>
</selector>
```
    - 주의사항 : selector에서 내가 만든 도형을 사용할 때 사용할 도형의 xml 파일과 selector 파일이 같은 폴더 안에 있어야지만 적용이 가능하다.
    - state_selected : 현재 선택되었는지 유무

```kotlin
//profileFragment.kt
//isSelected로 버튼이 선택되었는지 확인해주어야 xml 파일의 state_selected가 적용된다.
binding.btnFollower.isSelected = true
```

![1_2(1)](https://user-images.githubusercontent.com/102457618/167153223-7577ee9c-d78b-43a4-bff8-99c15f1008df.JPG)



**이미지 원형으로 표시되게 만들기**
```kotlin
//ProfileFragment.kt
private fun initImage(){
    Glide.with(this)
        .load(R.drawable.pic)
        .circleCrop()
        .into(binding.myImage)
}
```
    - with() : 어느 곳에 (View/Context/Activity/Fragment)
    - load() : 어떤 사진을
    - circleCrop() : 동그랗게 잘라서
    - into() : 어느 View에 넣을 것인가
    - R.drawable.pic : R 클래스에서 발생하는 리소스 ID로 액세스 가능

![1_2(2)](https://user-images.githubusercontent.com/102457618/167153290-ec58b112-2016-431b-9b26-d69358f02ec9.JPG)


**아이콘은 svg 혹은 이미지(png, jpg)등으로 export해서 사용**
**Activity 하단에 BottomNavigation 넣어주기(HomeActivity)**
```xml
<!--menu_bottom_navi.xml-->
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/menu_profile"
        android:icon="@drawable/user"
        android:title="@string/profile"/>
</menu>
```
    - png 파일은 9-Patch 사용
    - bottomnavi에서 보여줄 menu 먼저 생성

```xml
<!--selector_bottom_navi.xml-->
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="@color/bottomnavi" android:state_checked="true"/>
    <item android:color="@color/notchoose" android:state_checked="false" />
</selector>
```
    - 메뉴 아이콘이 체크되었을 때, 체크되지 않았을 때 색깔을 selector로 구현

```xml
<!--activity_home.xml-->
<com.google.android.material.bottomnavigation.BottomNavigationView
    android:id="@+id/bnv_main"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/white"
    app:itemIconTint="@color/selector_bottom_navi"
    app:itemRippleColor="@color/bottomnavi"
    app:itemTextColor="@color/selector_bottom_navi"
    app:layout_constraintBottom_toBottomOf="parent"
    app:menu="@menu/menu_bottom_navi"/>
```
    - itemIconTint : 탭의 아이콘 색상
    - itemTextColor : 탭의 타이틀 색상
    - itemRippleColor : 탭 선택 시 탭에서 퍼져나가는 물결 효과의 색상
    - menu 속성에 미리 만들어두었던 메뉴 xml 파일을 삽입

```kotlin
//HomeActivity.kt
private fun initBottomNavi(){
    //ViewPager2 연동
    binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            //페이지 중 하나가 선택되면 그에 대응하는 하단 탭 체크
            binding.bnvMain.menu.getItem(position).isChecked = true
        }
    })
    //아이템이 선택될 때마다 창 이동
    binding.bnvMain.setOnItemSelectedListener {
        when(it.itemId){
            R.id.menu_profile -> {
                binding.vpMain.currentItem = FIRST_FRAGMENT
                return@setOnItemSelectedListener true
            }
            R.id.menu_home -> {
                binding.vpMain.currentItem = SECOND_FRAGMENT
                return@setOnItemSelectedListener true
            }
            else -> {
                binding.vpMain.currentItem = THIRD_FRAGMENT
                return@setOnItemSelectedListener true
            }
        }
    }
}
```
    - (register)OnPageChangeCallback : ViewPager2의 화면 전환을 감시하는 **추상 클래스**
    - setOnItemSelectedListener : Item들이 선택되었을 때 호출되는 리스너, 선택되는 item의 id에 따라 지정된 페이지로 이동

![1_2(3)](https://user-images.githubusercontent.com/102457618/167153301-9b4eb4a8-7c05-4fb2-b91e-0ec18d921a63.JPG)


-------------
# 필수과제 1-3
###HomeFragment 만들기
**TabLayout + ViewPager2**
```xml
<!--fragment_home.xml-->
<!--xml 파일에 TabLayout과, 연동시킬 ViewPaper2 추가-->
<com.google.android.material.tabs.TabLayout
    android:id="@+id/tab_home"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toBottomOf="@id/title_home"
    android:layout_marginTop="15dp"
    app:tabIndicatorColor="@color/layout"/>

<androidx.viewpager2.widget.ViewPager2
    android:id="@+id/vp_home"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

```kotlin
//MainViewPagerAdapter.kt
//FragmentStateAdapter를 상속받는 ViewPager2 Adapter
class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
```
    - FragmentStateAdapter : RecyclerView.Adapter를 상속받는 어댑터
    - getItemCount() : Adapter가 가지고 있는 data set 안에서의 전체 아이템 수를 리턴하는 메소드
    - createFragment() : 특정 포지션에 연결된 새로운 Fragment를 제공하는 기능을 가진 메소드

```kotlin
//HomeFragment.kt
//ViewPager2와 Adapter 연결
private fun initAdapter(){     
    val fragmentList = listOf(FollowingTabFragment(), FollowerTabFragment())
    followingTabViewPagerAdapter = FollowTabViewPagerAdapter(this)
    followingTabViewPagerAdapter.fragments.addAll(fragmentList)
    binding.vpHome.adapter = followingTabViewPagerAdapter
}
```

```kotlin
//HomeFragment.kt
//ViewPager2와 TabLayout 연동
private fun initTabLayout() {
    val tabLabel = listOf("팔로잉", "팔로워")
    TabLayoutMediator(binding.tabHome, binding.vpHome) { tab, position ->
        tab.text = tabLabel[position]
    }.attach()
}
```
    - tabLabel : Tab의 이름
    - TabLayoutMediator : ViewPager2와 TabLayout을 연동할 때 사용하는 클래스
    - attach를 호출할 때 populateTabsFromPagerAdapter()를 호출하고, tabLayout.removeAllTabs()로 기존 탭들을 지워버리고 새로운 탭 생성


**TabLayout에 텍스트, 인디케이터 색상 설정**
`app:tabSelectedTextColor="@color/layout"` 텍스트 색상 설정
`app:tabIndicatorColor="@color/layout"` 인디케이터 색상 설정


![1_3](https://user-images.githubusercontent.com/102457618/167153404-c383bbd6-4a10-4569-9e8c-d6a69a0908fe.JPG)


-------------
# 성장과제 2-1
###ViewPager2 중첩 스크롤 문제 해결하기
**NestedScrollableHost**
자식 뷰가 우선적으로 스크롤을 인식할 수 있도록 하기 위해 사용하는 것

>1.프로젝트에 NestedScrollableHost.kt 추가 [NestedScrollableHost](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt)
>2.중첩되는 자식 View를 <NestedScrollableHost> 태그로 감싸준다

```xml
<com.example.soptseminar1.NestedScrollableHost
    android:layout_width="match_parent"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintTop_toBottomOf="@id/tab_home">
    
    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/vp_home"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    
</com.example.soptseminar1.NestedScrollableHost>
```
    - 해당 요소는 ViewPager2의 바로 아래에 위치한 유일한 자식일 것


-------------
#도전과제 3-1
###갤러리에서 받아온 이미지(Uri)를 Glide를 사용해서 화면에 띄워보기

```xml
<!--AndroidManifest.xml-->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
갤러리의 접근 권한을 요청하기 위해 추가해주어야 할 문구

```kotlin
//CameraFragment.kt
private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
        if(isGranted) { 
            navigateGallery()
        } else {
            Toast.makeText(requireContext(), "갤러리 접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
```
    - 접근 권한을 요청할 Launcher 생성
    - registerForActivityResult : 액티비티의 결과에 대한 콜백을 등록, 다른 액티비티에 보낼 launcher를 생성
    - RequestPermission() : 권한 요청
    - 요청이 허용될 경우 navigateGallery() 메소드 호출, 요청이 거부될 경우 토스트 메세지 출력

```kotlin
private val galleryLauncher: ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK && it.data != null) {
            val galleryImage = it.data?.data
            Glide.with(this)
                .load(galleryImage)
                .into(binding.imgGallery)
        } else if (it.resultCode == RESULT_CANCELED){
            Toast.makeText(requireContext(), "사진 선택이 취소되었습니다", Toast.LENGTH_SHORT).show()
        }
    }
```
    - 갤러리에서 사진 가져오는 것을 요청할 Launcher 생성
    - intent의 resultCode가 ok이고 data가 null이 아니면 이미지를 가져와 정한 위치에 binding
    - 아니라면 취소 토스트 메세지 출력

```kotlin
private fun navigateGallery(){
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = MediaStore.Images.Media.CONTENT_TYPE
    galleryLauncher.launch(intent)
}
```
    - 선택한 사진을 Intent 객체에 싣고 intent 객체의 타입 지정
    - galleryLauncher.launch(intent) : Intent 객체를 launcher에 실어서 실행

```kotlin
private fun changeGalleryImage(){
    binding.btnAttached.setOnClickListener{
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                navigateGallery()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showInContextUI()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}
```
    - Fragment일 경우 ContextCompat, Activity일 경우 ActivityCompat 사용
    - checkSelfPermission()으로 해당하는 권한을 가지고 있는지 확인
    - 권한이 있으면(Granted) 갤러리 진입
    - 권한이 없으면 ShouldShowRequestPermissionRationale()이라는 교육용 UI를 통해 권한을 승인할 것인지 거부할 것인지 여부를 묻고, 승인이면 권한을 줌

![3_1(1)](https://user-images.githubusercontent.com/102457618/167153455-503d0c02-619c-4466-9aca-5f71ad21736d.JPG)
![3_1(2)](https://user-images.githubusercontent.com/102457618/167153458-f9edf3ca-82ca-4aeb-9ad6-349d323d34d5.JPG)
