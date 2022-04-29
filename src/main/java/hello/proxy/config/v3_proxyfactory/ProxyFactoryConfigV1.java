package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        var proxyFactory = new ProxyFactory(orderControllerV1);
        proxyFactory.addAdvisor(getAdvisor(logTrace));
        log.info("ProxyFactory proxy = {}, target = {}", proxyFactory, orderControllerV1.getClass());
        return (OrderControllerV1) proxyFactory.getProxy();
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        var proxyFactory = new ProxyFactory(orderServiceV1);
        proxyFactory.addAdvisor(getAdvisor(logTrace));
        log.info("ProxyFactory proxy = {}, target = {}", proxyFactory, orderServiceV1.getClass());
        return (OrderServiceV1) proxyFactory.getProxy();
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryV1 = new OrderRepositoryV1Impl();
        var proxyFactory = new ProxyFactory(orderRepositoryV1);

        proxyFactory.addAdvisor(getAdvisor(logTrace));
        log.info("ProxyFactory proxy = {}, target = {}", proxyFactory, orderRepositoryV1.getClass());
        return (OrderRepositoryV1) proxyFactory.getProxy();
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedNames("request*", "order*", "save*");
        return new DefaultPointcutAdvisor(nameMatchMethodPointcut, new LogTraceAdvice(logTrace));
    }
}
