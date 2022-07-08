package hello.advanced.jpa.member;

import hello.advanced.jpa.member.annotation.ClassAop;
import hello.advanced.jpa.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

	@Override
	@MethodAop("test value")
	public String hello(String name) {
		return "ok";
	}

	public String internal(String param) {
		return "ok";
	}
}
