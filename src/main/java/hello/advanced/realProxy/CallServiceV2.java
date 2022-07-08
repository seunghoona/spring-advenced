package hello.advanced.realProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallServiceV2 {

	private final ApplicationContext applicationContext;
	private final ObjectProvider<CallServiceV2> callServiceV2Provider;


	public void external() {
		log.info("call external");
		// CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
		CallServiceV2 providerObject = callServiceV2Provider.getObject();
		providerObject.internal(); // 내부 메서드 호출(this.internal)
	}

	public void internal() {
		log.info("call internal");
	}
}
