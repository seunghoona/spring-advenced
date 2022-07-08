package hello.advanced.realProxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CallServiceV0 {


	public void external() {
		log.info("call external");
		this.internal(); // 내부 메서드 호출(this.internal)
	}

	public void internal() {
		log.info("call internal");
	}
}
