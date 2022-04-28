package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {


    @Test
    @DisplayName("변하지 않는 데이터 이지만 캐싱되지 않음 총 3초가 걸리는 데이터")
    void noProxyTest() {
        ProxyPatternClient patternClient = new ProxyPatternClient(new RealSubject());
        patternClient.execute();
        patternClient.execute();
        patternClient.execute();
    }
}
