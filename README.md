# `spring-project-infras`
Is a core `infras.` component abstraction library for developing Spring [Boot] projects.

[Examples](https://github.com/photowey/spring-project-infras-examples)

## 1.`Usage`

Add this to your `pom.xml`

### 1.1.`Spring Boot` projects.

```xml
<!-- ${spring-project-infras.version} == ${latest.version} -->
<!-- https://central.sonatype.com/artifact/io.github.photowey/spring-project-infras/versions -->
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>spring-infras-spring-boot-starter</artifactId>
    <version>${spring-project-infras.version}</version>
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
> `this.InfrasEngine.serviceEngine().employeeService.add(...)`.

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