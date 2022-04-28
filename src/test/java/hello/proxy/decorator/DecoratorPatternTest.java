package hello.proxy.decorator;

import hello.proxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component component = new RealComponent();
        var decoratorPatternClient = new DecoratorPatternClient(component);
        decoratorPatternClient.execute();
    }

    @Test
    void decorator1() {
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(new MessageDecorator(new RealComponent()));
        decoratorPatternClient.execute();
    }

    @Test
    void decorator2() {
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(new TimeDecorator(new MessageDecorator(new RealComponent())));
        decoratorPatternClient.execute();
    }
}
