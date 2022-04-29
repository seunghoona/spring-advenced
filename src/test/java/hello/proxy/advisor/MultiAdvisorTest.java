package hello.proxy.advisor;

import hello.proxy.common.service.ServiceInterface;
import hello.proxy.common.service.ServiceInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class MultiAdvisorTest {


    @Test
    void multiAdvisorTest1() {
        // client - > proxy(advisor2) - > proxy(advisor1) -> target

        ServiceInterface serviceInterface = new ServiceInterfaceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(serviceInterface);

        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new Advice1()));
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new Advice2()));

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("첫번째 어드바이스");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("두번째 어드바이스");
            return invocation.proceed();
        }
    }
}
