package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j
public class RealSubject implements Subject{
    @Override
    public String operation()  {
        log.info("실제 객체 호출");
        sleep(1000);
        return "ok";
    }

    private void sleep(int million) {
        try {
            Thread.sleep(million);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
