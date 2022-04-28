package hello.proxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;

@Slf4j
public class TimeDecorator implements Component {

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();
        String operation = component.operation();
        long endTime = System.currentTimeMillis();

        log.info("TimeDecorator 종료 = {}ms", endTime - startTime);
        return operation;
    }
}
