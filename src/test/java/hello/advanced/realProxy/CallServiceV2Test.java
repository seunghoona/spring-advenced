package hello.advanced.realProxy;

import hello.advanced.realProxy.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV2Test {
	@Autowired
	private CallServiceV2 callServiceV2;

	/**
	 * 자기 자신의 내부호출은 프록시를 사용하지 못한다.
	 */
	@Test
	void external() {
		callServiceV2.external();
	}
	/**
	 * 자기 자신의 프록시를 사용할 수 있다.
	 */
	@Test
	void internal() {
		callServiceV2.internal();
	}

}