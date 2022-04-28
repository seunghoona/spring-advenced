package hello.proxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{


    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("Message decorator 실행");
        var s = component.operation() + "와우 새롭게 꾸미기 적용";
        log.info("Message decorator 실행");
        return s;
    }
}
