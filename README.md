TODO List API 서버
===

# 📝 프로젝트 소개

- Spring Framework 기반으로 할 일 카드를 관리하는 REST API 서버입니다.

<h2>목차</h2>

> - [📚 API 명세서](#API-명세서)
> - [☁️ ERD Cloud](#erd-cloud)
> - [📦 패키지 구조](#패키지-구조)
> - [🔄 통신 흐름](#통신-흐름)
> - [⚙️ 주요 기능](#주요-기능)
> - [💻 개발 환경](#개발-환경)

# [API 명세서](https://mobzz.notion.site/1c3b0b6d379f4d5aa93d4ebc058ecd12?v=3c5b824e364e4112b4865b03a336dd05&pvs=74)

<details><summary>Use case diagram</summary>

![Use case diagram](Use_Case_Diagram.drawio.svg)

</details>

## 일부 요약

![api_spec_summary.png](api_spec_summary.png)

# [ERD Cloud](https://www.erdcloud.com/d/8JmKdknxC3JsBpzEe)

## 요약

![erd_cloud.png](erd_cloud.png)

# 패키지 구조

```
org.example.todolistserverchapter4
└── api.v1
    └── domain
        ├── todo
        │   ├── controller
        │   ├── dto
        │   ├── model
        │   ├── query
        │   ├── repository
        │   └── service
        ├── user
        │   ├── controller
        │   ├── dto
        │   ├── model
        │   ├── repository
        │   └── service
    ├── ApiV1MappingConfig
    └── exception
    └── infra.swagger
    └── util
└── security
```

- dto 패키지는 클라이언트-서버 API 통신에 필요한 데이터 전송 객체를 담고 있습니다.<br/>
- model 패키지는 DB와 매핑되는 Entity 객체를 담고 있습니다.<br/>
- controller 패키지는 클라이언트의 요청을 받아 처리하는 Rest API 컨트롤러를 담고 있습니다.
- repository 패키지는 DB와 직접적으로 통신하는 JpaRepository 인터페이스를 담고 있습니다.
- service 패키지는 비즈니스 로직을 처리하는 서비스 인터페이스와 구현체를 담고 있습니다.
- query 패키지는 Query parameter에 사용하는 enum을 담고 있습니다.
- exception 패키지는 예외 처리를 위한 커스텀 예외 클래스를 담고 있습니다.
- swagger 패키지는 Swagger 설정을 담고 있습니다.
- util 패키지는 유틸리티 클래스를 담고 있습니다.
- security 패키지는 Spring security에 관련된 클래스를 담고 있습니다.

## [/api/v1 package로 이동](src/main/kotlin/org/example/todolistserverchapter4/api/v1)

# 통신 흐름

DDD 설계에 의거해 작성되었습니다.

- **Controller**: 클라이언트의 요청을 받아 DTO로 변환하고 비즈니스 로직을 수행할 적절한 Service에 요청을 보냅니다.
- **Service**: 필요한 데이터를 Repository에 요청하여 가져와 비즈니스 로직을 처리하고 요청에 맞는 Dto를 반환합니다.
- **Repository**: DB와 통신해 Entity를 관리하며 Service의 요청에 맞는 데이터를 가져와 반환합니다.

다른 Aggregate에 대해서는 Repository는 참조하지 않고 service를 의존합니다.

```plaintext
     Client
       |
       v
+--------------+
|  Controller  |
+--------------+
       |
       v
+--------------+
|   Service    |
+--------------+
       |
       v
+--------------+
|  Repository  |
+--------------+
       |
       v
+--------------+
|   Database   |
+--------------+
```

# 주요 기능

- Todo 카드 생성, 조회, 수정, 삭제, 진행 상태 변경
- Todo 카드에 댓글 생성(익명 포함), 조회, 수정, 삭제
- 유저 생성, 프로필 조회, 프로필 수정, 삭제

<details><summary>Todo Controller 예시</summary>

```kotlin
@RestController
@RequestMapping("/todos")
class TodoController(
    private val todoService: TodoService
) : ApiV1MappingConfig() {

    @GetMapping
    fun getTodoList(
        @RequestParam(defaultValue = "created_at_asc") sort: TodoSort,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) userIds: List<Long>? = null,
    ): ResponseEntity<Page<TodoDto>> {
        val pageable: Pageable = PageRequest.of(page, size, sort.convertToSort())

        return /*...*/
    }

    /*...*/

    @PostMapping
    fun createTodo(
        @Valid @RequestBody request: TodoCreateDto,
        @ModelAttribute("userId") userId: Long?
    ): ResponseEntity<TodoDto> {
        if (userId == null) {
            throw NotAuthorizedException()
        }

        return /*...*/
    }

    /*...*/
}
```

</details>
<br/>
<details><summary>Todo Service 예시</summary>

```kotlin
@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
    val commentRepository: CommentRepository,

    val userService: UserService
) : TodoService {
    
    override fun getTodoList(userIds: List<Long>?, pageable: Pageable): Page<TodoDto> {
        val todos = if (userIds != null) {
            todoRepository.findByUserIdIn(userIds, pageable)
        } else {
            todoRepository.findAll(pageable)
        }

        val userDtos = todos.map { it.userId }.distinct().let { userService.getUserProfiles(it) }

        return todos.map { DtoConverter.convertToTodoDto(todo = it, userDto = userDtos[it.userId.toInt()]) }
    }
    
    /*...*/

    @Transactional
    override fun createTodo(userId: Long, request: TodoCreateDto): TodoDto {
        val todo = todoRepository.save(
            Todo.fromDto(
                request = request,
                userId = userId
            )
        )

        val userDto = userService.getUserProfile(todo.userId)

        return DtoConverter.convertToTodoDto(todo = todo, userDto = userDto)
    }
    
    /*...*/
}
```

</details>

<br/>

<details><summary>Todo Repository 예시</summary>

```kotlin
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByUserIdIn(userIds: List<Long>, pageable: Pageable = Pageable.unpaged()): Page<Todo>
}
```

</details>

<br/>

<details><summary>Todo Entity 예시</summary>

```kotlin
@Entity
@Table(name = "todo")
@SQLRestriction("status != 'Deleted'")
@SQLDelete(sql = "UPDATE todo SET status = 'Deleted', deleted_at = NOW() WHERE id = ?")
class Todo(
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: TodoStatus = TodoStatus.Alive,

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status")
    var cardStatus: TodoCardStatus = TodoCardStatus.NotStarted,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @Column(name = "user_id")
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun hasPermission(user: UserDto): Boolean {
        return UserRole.valueOf(user.role) == UserRole.Admin || this.userId == user.id
    }

    private fun validate() {
        require(title.isNotBlank()) { "Title cannot be blank" }
        require(title.length <= 100) { "Title must be 100 characters or less" }
        require(this.description != null && this.description!!.length <= 1000) { "Description must be 1000 characters or less" }
    }

    companion object {
        fun fromDto(request: TodoCreateDto, userId: Long): Todo {
            return Todo(
                title = request.title,
                description = request.description,
                userId = userId
            ).apply { this.validate() }
        }
    }
}
```

</details>

# 개발 환경

- 개발 언어: Kotlin 1.9.23, JDK 21
- IDE: IntelliJ IDEA 2024.1
- Build tool: Gradle
- 프레임워크: Spring Boot 3.2.5
- 라이브러리: Springdoc 2.5.0
- 데이터베이스: PostgresQL 14.1 with Supabase