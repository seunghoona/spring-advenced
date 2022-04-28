# 예제 프로젝트 V1


## V1 - 인터페이스와 구현 클래스 - 스프링 빈으로 등록

`@Configuration` 자동 대상이 되지 않도록 하기위해서 별도의 패키지 등록

```java
    @SpringBootApplication(scanBasePackages = "hello.proxy.app")
    public class MainApplication {
        
    }
```
