# `spring-project-infras`
Is a core `infras.` component abstraction library for developing Spring [Boot] projects.

[Examples](https://github.com/photowey/spring-project-infras-examples)

## 1.`Usage`

Add this to your `pom.xml`

### 1.1.`Spring Boot` projects.

1.1.1.`Spring Boot 2.x`

```xml
<!-- ${spring-project-infras.version} == ${latest.version} -->
<!-- https://central.sonatype.com/artifact/io.github.photowey/spring-project-infras/versions -->
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>spring-infras-spring-boot-starter</artifactId>
    <version>${spring-project-infras.version}</version>
</dependency>
```

#### 1.1.2.`Spring Boot 3.x`

```xml

<dependency>
  <!-- ... -->
  <artifactId>spring-infras-spring-boot3-starter</artifactId>
  <!-- ... -->
</dependency>
```

### 1.2.`Spring` projects.

```xml
<!-- bean|core|web -->
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>spring-project-infras-web</artifactId>
    <version>${spring-project-infras.version}</version>
</dependency>
```



## 2.`APIs`

### 2.1.`core` module

#### 2.1.1.`dependency`

```xml
<dependency>
    <!-- // ... -->
    <artifactId>spring-project-infras-core</artifactId>
    <!-- // ... -->
</dependency>
```



#### 2.1.2.`StrategySupporter`

> `io.github.photowey.spring.infras.core.example.loader.EmployeeLoader`

- `BeanFactoryStrategySupporter`
- `OrderedBeanFactoryStrategySupporter`
- `ApplicationContextStrategySupporter`
- `OrderedApplicationContextStrategySupporter`



#### 2.1.3.`Getter`

- `BeanFactoryGetter`
- `ApplicationContextGetter`
- `EnvironmentGetter`
- `ObjectMapperGetter`
  - `@since 1.3.0`



#### 2.1.4.`ApplicationContext Holder`

- `ApplicationContextHolder`



#### 2.1.5.`Printer`

[spring-project-infras-core-example](https://github.com/photowey/spring-project-infras-examples)

```java
@SpringBootApplication
public class App {

    /*
----------------------------------------------------------
	Bootstrap: the 'spring-project-infras-core-example Context' is Success!
	Application: 'spring-project-infras-core-example' is running! Access URLs:
	Local: 		http://localhost:7923
	External: 	http://192.168.1.101:7923
	Swagger: 	http://192.168.1.101:7923/swagger-ui/index.html
	Actuator: 	http://192.168.1.101:7923/actuator/health
	Profile(s): dev
----------------------------------------------------------
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        AppPrinter.print(applicationContext);
        // http://192.168.1.101:7923/doc.html
        //AppPrinter.print(applicationContext, AppContext.swaggerAppContext());
        // http://192.168.1.101:7923/swagger-ui/index.html
        //AppPrinter.print(applicationContext, AppContext.swaggerAppContext("swagger-ui/index.html"));
    }
}

```

#### 2.1.6.`JsonConverter`

> `@since 1.3.0`

- `JacksonJsonConverter`
  - `DefaultJacksonJsonConverter`



#### 2.1.7.`@EnvironmentProfile`

> `@since 1.4.0`

- An extender of `@Profile`
  - Support `${a.b.c.....z}` or `!${a.b.c.....z}`



#### 2.1.8.`@ConditionalOnSpEL`

- An alias of Annotation `@EnvironmentProfile`

```java
// @Profile("dev")

@Slf4j
@Component
@EnvironmentProfile("!${io.github.photowey.github.accessor.mock.profiles}")
public class NonMockGithubAccessor implements GithubAccessor {}
```

```java
@Slf4j
@Component
@ConditionalOnSpEL("${io.github.photowey.github.accessor.mock.profiles}")
public class MockGithubAccessor implements GithubAccessor {}
```



#### 2.1.9.`ResourceReader`

> `@since 1.4.0`
>
> Support read `FileSystem` and `Classpath` resource

#### 2.1.10.`RemoteResourceReader`

> `RemoteResourceReader` extends `ResourceReader`
>
> - Support read Remote resource, `e.g.`: `https://xxx/yyy/zzz`



### 2.2.`bean` module

[spring-project-infras-bean-example](https://github.com/photowey/spring-project-infras-examples)

#### 2.2.1.`dependency`

```xml
<dependency>
    <!-- // ... -->
    <artifactId>spring-project-infras-bean</artifactId>
    <!-- // ... -->
</dependency>
```



#### 2.2.2.`NotifyCenter`

- `publishEvent`
- `publishAsyncEvent`



#### 2.2.3.`Engine`

> `io.github.photowey.spring.infras.bean.example.engine.InfrasBeanEngine`
>
> All Service or Repository components will be held by Engine, which has an obvious advantage: 
>
> it reduces the number of components injected into beans through `@Autowired` or constructors, thereby making the code cleaner. 
>
> There is also a disadvantage: when using component methods, the call chain will increase by at least two nodes. For example: 
>
> `this.infrasEngine.serviceEngine().employeeService.add(...)`.

- `Engine`
- `AbstractEngine`
- `EngineAware`
- `EngineAwareBeanPostProcessor`
- `AbstractEngineAwareBeanPostProcessor`
- `built-in`
  - `NotifyEngine`
  - `NotifyEngineAware`
  - `NotifyEngineImpl`
  - `NotifyEngineAwareBeanPostProcessor`



### 2.3.`web` module

[spring-project-infras-web-example](https://github.com/photowey/spring-project-infras-examples)

#### 2.3.1.`dependency`

```xml
<dependency>
    <!-- // ... -->
    <artifactId>spring-project-infras-web</artifactId>
    <!-- // ... -->
</dependency>
```



#### 2.3.2.`Factory`

- `UrlPathHelperFactory`
- `AntPathMatcherFactory`



### 2.4.`starter` module

[spring-project-infras-starter-example](https://github.com/photowey/spring-project-infras-examples)

#### 2.4.1.`dependency`

```xml
<dependency>
    <!-- // ... -->
    <artifactId>spring-infras-spring-boot-starter</artifactId>
    <!-- // ... -->
</dependency>
```



#### 2.4.2.`Binder`

- `PropertyBinders`



#### 2.4.3.`Property`

- `SpringInfrasProperties`



#### 2.4.4.`NotifyExecuter`

- `TaskExecutorConfigure`
  - Auto configuration `ThreadPoolTaskExecutor` named `notifyAsyncExecutor`



#### 2.4.5.`AutoConfiguration`

- `SpringInfrasConfigure`
  - Spring Boot < `2.7.x`
- `SpringInfrasAutoConfigure`
  - Spring Boot >= `2.7.x`

### 2.5.`common`  module

> `@since 1.3.0`

#### 2.5.1.`Lambda Consumer`

- `DoubleConsumer`
- `TripleConsumer`
- `QuadraConsumer`
- `PentaConsumer`
- `NoOpsConsumer`
  - `@since 1.4.0`
- `NoOpsDoubleConsumer`
  - `@since 1.4.0`

#### 2.5.2.`Lambda Function`

- `DoubleFunction`
- `TripleFunction`
- `QuadraFunction`
- `PentaFunction`
- `SerializableFunction`

#### 2.5.3.`Future`

- `Futures`
- `Sleepers`

#### 2.5.4.`json`

- `JSON`
  - `Enum`
  - `Jackson`
  - `Fastjson`
    - `Unsupported now`
  - `Gson`
    - `Unsupported now`
- `Jackson`
  - `final class`

#### 2.5.5.`threadpool`

> - `scheduleWithFixedDelay`
> - `scheduleAtFixedRate`
>
> Ensure that when the thread pool is executing a task, the task throws an uncontrollable exception and causes task
> scheduling to be blocked.

- `scheduled`
  - `SafeScheduledThreadPoolExecutor`
    - extends `ScheduledThreadPoolExecutor`
    - ext. Methods
      - `scheduleWithFixedDelay`
      - `scheduleAtFixedRate`