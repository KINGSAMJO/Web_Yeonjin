# 필수과제 1-1
FollowerRecyclerView, RepositoryRecyclerView 만들기(HomeActivity)

**각각의 RecyclerView 담고있는 Fragment 2개 만들기**
```xml
<!--fragment_follower.xml-->
<!--fragment의 xml 파일에 RecyclerView 생성-->
<!--코틀린 파일은 2차 세미나때 만들었던 그대로 생성함-->
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context=".FollowerFragment">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_follower"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        tools:itemCount="4"
        tools:listitem="@layout/follower_layout"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```


**각각의 버튼 눌렀을 때 알맞은 Fragment로 전환되게 하기**
```kotlin
//HomeActivity.kt
private fun initTransactionEvent(){
    //객체 생성
    val fragment1 = FollowerFragment()
    val fragment2 = RepositoryFragment()
    
    //fragment 추가
    supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragment1).commit()
    
    binding.btnFollower.setOnClickListener{
        //supportFragmentManager가 FragmentManager 호출
        val transaction = supportFragmentManager.beginTransaction()
        when (position) {
            //현재 화면에 표시된 fragment가 repository일 때 follower로 변경
            REPOSITORY -> {
                //fragment 변경
                transaction.replace(R.id.main_fragment, fragment1)
                position = FOLLOWER
            }
        }
        transaction.commit()
    }

    binding.btnRepo.setOnClickListener{
        val transaction = supportFragmentManager.beginTransaction()
        when (position) {
            //현재 화면에 표시된 fragment가 follower일 때 repository로 변경
            FOLLOWER -> {
                transaction.replace(R.id.main_fragment, fragment2)
                position = REPOSITORY
            }
        }
        transaction.commit()
    }
}
```
    - add() : 프래그먼트 추가
    - replace() : 이전 프래그먼트를 제거 후 교체
    - beginTransaction() : 트랜잭션 작업(추가/교체/삭제) 생성
    - commit() : 작업 수행

![1_1(1)](https://user-images.githubusercontent.com/102457618/164720409-58b8d0a2-9923-4d86-b13d-4cf04ef8e519.JPG)
![1_1(2)](https://user-images.githubusercontent.com/102457618/164720454-5bf7599f-29af-4ca6-a238-c20573af8529.JPG)


**FollowerRecyclerView, RepositoryRecyclerView 만들기**
```kotlin
//FollowerRecyclerView.kt
class FollowerRecyclerView : RecyclerView.Adapter<FollowerRecyclerView.FollowerViewHolder>() {
    val followerList = mutableListOf<FollowerData>()
    
    //ViewHolder를 생성하고 ItemLayout의 Binding 객체를 만들어 ViewHolder의 생성자로 넘겨주는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding = FollowerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }
    
    //ViewHolder와 position의 데이터를 결합시키는 함수
    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])
    }
    
    //RecyclerView로 보여줄 전체 데이터의 개수 반환
    override fun getItemCount(): Int = followerList.size
    
    class FollowerViewHolder(
        private val binding: FollowerLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        //ViewHolder가 가진 View에 Adapter로부터 전닯다은 데이터를 붙여주는 함수
        fun onBind(data: FollowerData){
            binding.profName.text = data.name
            binding.profIntroduce.text = data.introduction
        }
    }
}
```
    - ViewHolder의 역할 : ItemLayout을 붙잡고 관리 / adapter에서 전달받은 데이터를 ItemLayout에 Bind
    - Adapter의 역할 : ViewHolder 생성 / Itemlayout과 Data를 ViewHolder에 전달
    - Adapter에서 꼭 오버라이드해야하는 함수 onCreateViewHoler, onBindViewHolder, getItemCount
    - .from() : 어떤 레이아웃에 접근해야할지 알려주기 위해 context(정보를 담는 곳)를 인자로 전달하여 inflate


**설명이 너무 길어서 글씨가 길어지면 뒤에 ...으로 표시되게 하기**
`android:maxLines="1"` 줄이 내려가지 않도록 maxLines 속성 사용
`android:ellipsize="end"` 뒤에 ...으로 표시되도록 ellipsize 속성 사용

![1_1(3)](https://user-images.githubusercontent.com/102457618/164720521-c41239ad-4251-4593-9d68-fd22e0147138.JPG)
![1_1(4)](https://user-images.githubusercontent.com/102457618/164720554-fcafc2f1-896f-4a73-8267-4dfbc18ffc53.JPG)


# 필수과제 1-2
둘 중 하나의 RecyclerView는 GridLayout으로 만들기

**GridLayout**
```xml
<!--fragment_repository.xml-->
<androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_follower"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
        app:spanCount="2"
        tools:itemCount="6"
        tools:listitem="@layout/repo_layout" />
```
    - layoutManager : Item의 배치 규칙을 관리하는 클래스, 격자식으로 나타내기 위해 GridLayoutManager 선택
    - spanCount : xml 상에서 가로로 몇 칸을 보여줄지 설정하는 속성


# 성장과제 2-1
아이템 클릭 시 상세 설명을 보여주는 Activity로 이동하기(DetailActivity)

**아이템 클릭 시 해당 아이템의 이름과 설명 값을 DetailActivity에서 보여주기**
```kotlin
//FollowerRecyclerView.kt
//조장님 블로그 참고했습니다(__)
//람다 이용
class FollowerRecyclerView(private val itemClick: (FollowerData) -> (Unit)) : RecyclerView.Adapter<FollowerRecyclerView.FollowerViewHolder>()
```
    - input으로 FollowerData(이름, 설명)이 주어지면 output으로 return이 없는 Unit이 됨

```kotlin
//FollowerRecyclerView.kt
//어댑터 내부에 있는 FollowerViewHolder의 onBind 함수 내에 클릭리스너 생성
fun onBind(data: FollowerData){
    binding.profName.text = data.name
    binding.profIntroduce.text = data.introduction
    binding.root.setOnClickListener {
        itemClick(data)
    }
}
```
    - 클릭 시 itemClick을 통해 data(=FollowerData)가 인자로 전달됨

```kotlin
//FollowerFragment.kt
followerRecyclerView = FollowerRecyclerView{
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("name", it.name)
            intent.putExtra("introduction", it.introduction)
            startActivity(intent)
        }
```
    - itemClick으로 전달된 인자를 intent객체로 DetailActivity에 전달
    - requireContext() : notnull한 context 반환

```kotlin
//DetailActivity.kt
//intent로 보낸 data 받아서 setText
private fun information(){
    val name = intent.getStringExtra("name")
    val introduction = intent.getStringExtra("introduction")
    binding.detailName.setText(name)
    binding.detailIntroduction.setText(introduction)
}
```
    - getStringExtra()를 이용하여 intent 객체로 전달한 데이터를 가져옴

![2_1(1)](https://user-images.githubusercontent.com/102457618/164720641-64276b80-60f5-4530-8002-a59f50a682de.JPG)


# 성장과제 2-2
ItemDecoration 활용해서 리스트 간 간격 또는 구분선 주기(MyItemDecoration)

**ItemDecoration을 활용해서 구분선 or 아이템 간 간격(margin)주기**
1. 구분선
```kotlin
//MyItemDecoration.kt
override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(c, parent, state)
    //구분선 두께
    val dividerWidth = 5
    //구분선 색깔
    val paint = Paint()
    paint.color = Color.GRAY
    
    //parent == recyclerView에 등록되어있는 item들의 테두리 위치 계산
    val left = parent.paddingStart.toFloat()
    val right = (parent.width - parent.paddingEnd).toFloat()

    for(i in 0 until parent.childCount){
        val child = parent.getChildAt(i)
        val param = child.layoutParams as RecyclerView.LayoutParams
        //구분선을 아이템 bottom에서 20dp 떨어진 곳에 위치시킴
        val dividerTop = child.bottom + param.bottomMargin + 20
        val dividerBottom = dividerTop + dividerWidth
        c.drawRect(left, dividerTop.toFloat(), right, dividerBottom.toFloat(), paint)
    }
}
```

2. 아이템 간 간격
```kotlin
//MyItemDecoration.kt
super.getItemOffsets(outRect, view, parent, state) 
val offset = 20
outRect.set(offset, offset, offset, offset)
```
    - offset을 두고 start, top, end, bottom에 같은 크기의 간격을 주었음
    - 기회가 된다면 아이템 위치에 따른 간격의 차이를 두고싶다..!

3. ItemDecoration 사용 시
```kotlin
//FollowerFragment.kt
binding.rvFollower.addItemDecoration(MyItemDecoration())
```

*****


# 성장과제 2-3
~~추가 예정~~
