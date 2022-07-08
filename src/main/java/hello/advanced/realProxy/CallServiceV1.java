package hello.advanced.realProxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CallServiceV1 {

	CallServiceV1 callServiceV1;

	@Autowired
	public void setCallServiceV1(CallServiceV1 callServiceV1) {
		this.callServiceV1 = callServiceV1;
	}

	public void external() {
		log.info("call external");
		callServiceV1.internal(); // 내부 메서드 호출(this.internal)
	}

	public void internal() {
		log.info("call internal");
	}
}
