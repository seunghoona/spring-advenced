package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;


public class BasicTest {

    @Test
    @DisplayName("beanA 등록 되어있고, beanB는 등록되어 있지 않다.")
    void whenBeanBThrow() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);
        A beanA = context.getBean("beanA", A.class);
        beanA.helloA();

        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(B.class));
    }

    @Test
    @DisplayName("beanA를 B를 bean으로 등록")
    void changeBean() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);
        B beanA = context.getBean("beanA", B.class);
        beanA.helloB();

        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(A.class));
    }


    @Configuration
    static class BasicConfig {
        @Bean(name= "beanA")
        public A a() {
            return new A();
        }

    }

    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name= "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBPostProcessor aToBPostProcessor() {
            return new AToBPostProcessor();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    @Slf4j
    static class  AToBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {}, bean = {}", beanName, bean);

            if (bean instanceof A ) {
                return new B();
            }
            return bean;
        }
    }
}
