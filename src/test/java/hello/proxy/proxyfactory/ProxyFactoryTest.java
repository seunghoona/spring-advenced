package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceInterface;
import hello.proxy.common.service.ServiceInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    void interfaceProxy() {
        ServiceInterface target = new ServiceInterfaceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("target = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    }

    @Test
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("target = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("Option을 통한 인터페이스를 cglib로 생성")
    void proxyTargetClassOptionUse() {
        ServiceInterface target = new ServiceInterfaceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        proxyFactory.setProxyTargetClass(true);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("target = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    }
}
