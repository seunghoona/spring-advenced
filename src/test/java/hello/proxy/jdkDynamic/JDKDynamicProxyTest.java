package hello.proxy.jdkDynamic;

import hello.proxy.jdkDynamic.code.AImpl;
import hello.proxy.jdkDynamic.code.AInterface;
import hello.proxy.jdkDynamic.code.TimeInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JDKDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Object o = Proxy.newProxyInstance(AInterface.class.getClassLoader(),
                new Class[]{AInterface.class}, handler);

        AInterface aInterface = (AInterface) o;

        aInterface.call();

        log.info("targetClass= {}", target.getClass());
        log.info("proxyClass= {}", aInterface.getClass());

    }

    @Test
    void dynamicB() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Object o = Proxy.newProxyInstance(AInterface.class.getClassLoader(),
                new Class[]{AInterface.class}, handler);

        AInterface aInterface = (AInterface) o;

        aInterface.call();

        log.info("targetClass= {}", target.getClass());
        log.info("proxyClass= {}", aInterface.getClass());

    }

}
