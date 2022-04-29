package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceInterface;
import hello.proxy.common.service.ServiceInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceInterfaceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()));
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();

    }

    @Test
    @DisplayName("직접 만드는 포인트컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceInterfaceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice()));
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointCut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    @Slf4j
    static class  MyMethodMatcher implements MethodMatcher {

        private String save = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            log.info("포인트 컷 호출 method = {}, targetClass = {}", method.getName(), targetClass);
            return  method.getName().equals(save);
        }

        @Override
        // 이 값이 참이면 matches 메소드를 호출한다.
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            throw new UnsupportedOperationException();
        }
    }

}
