package hello.proxy.jdkDynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        log.info("start");
        String result1 = target.callA();
        log.info("result= {}", result1);

        log.info("start");
        String result2 = target.callB();
        log.info("result= {}", result2);
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        Class<?> clazz = Class.forName("hello.proxy.jdkDynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        
        // callA 메소드 정보
        Method methodCallA = clazz.getMethod("callA");
        // 호출 target의 인스턴스에는 메소드를 호출
        Object invokeA = methodCallA.invoke(target);
        log.info("result1={}", invokeA);
        // --------------------------------------

        Method methodCallB = clazz.getMethod("callB");
        // 호출 target의 인스턴스에는 메소드를 호출
        Object invokeB = methodCallB.invoke(target);
        log.info("result2={}", invokeB);
    }

    @Test
    void reflection2() throws Exception{
        // 클래스 정보
        Class<?> clazz = Class.forName("hello.proxy.jdkDynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        // callA 메소드 정보
        dynamicCall(clazz.getMethod("callA"), target);
        // --------------------------------------
        dynamicCall(clazz.getMethod("callB"), target);
    }

    private void dynamicCall(Method method, Object target) throws Exception{
        log.info("start");
        Object result = method.invoke(target);
        log.info("result2={}", result);
    }

    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }



    @Test
    void reflectionTest() {

        Call<String> a = () -> {
            log.info("CallA");
            return "A";
        };

        Call<String> b = () -> {
            log.info("CallB");
            return "B";
        };

        List<Call<?>> calls = List.of(a, b);

    }
}
