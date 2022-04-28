package hello.proxy.pureproxy.concreteProxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();
        String operation = concreteLogic.operation();
        long endTime = System.currentTimeMillis();

        log.info("TimeDecorator 종료 = {}ms", endTime - startTime);
        return operation;
    }
}
