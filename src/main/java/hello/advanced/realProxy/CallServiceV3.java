package hello.advanced.realProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *  구조를 변경하는 것
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CallServiceV3 {

	private InternalService internalService;

	public void external() {
		log.info("call external");
		internalService.internal(); // 내부 메서드 호출(this.internal)
	}

}
