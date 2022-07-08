package hello.advanced.realProxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CallServiceV3Test {
	@Autowired
	private CallServiceV3 callServiceV3;

	/**
	 * 자기 자신의 내부호출은 프록시를 사용하지 못한다.
	 */
	@Test
	void external() {
		callServiceV3.external();
	}
}