package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandle;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        Class<OrderControllerV1> clazz = OrderControllerV1.class;
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new LogTraceFilterHandler(orderControllerV1, logTrace, PATTERNS));

        return (OrderControllerV1) o;
    }
    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        Class<OrderServiceV1> clazz = OrderServiceV1.class;
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new LogTraceFilterHandler(orderServiceV1, logTrace, PATTERNS));
        return (OrderServiceV1) o;
    }


    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryV1 = new OrderRepositoryV1Impl();
        Class<OrderRepositoryV1> clazz = OrderRepositoryV1.class;
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new LogTraceFilterHandler(orderRepositoryV1, logTrace, PATTERNS)
        );
        return (OrderRepositoryV1) o;
    }
}
