package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceInterface;
import hello.proxy.common.service.ServiceInterfaceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

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
}
