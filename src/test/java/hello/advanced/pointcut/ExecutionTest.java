package hello.advanced.pointcut;

import hello.advanced.member.MemberServiceImpl;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		log.info("helloMethod={}", helloMethod);
	}

	@Test
	@DisplayName("해당 메소드가 AOP 적용이 되고 있는지 확인")
	void exactMatch() {
		pointcut.setExpression("execution(public String hello.advanced.member.MemberServiceImpl.hello(String))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	/**
	 *  접근 제어자 생략 public ?
	 *  반환타입 생략
	 *  execution(public? * *(..))
	 */
	@Test
	@DisplayName("생략 가능한 포인트 컷 ")
	void allMatch() {
		pointcut.setExpression("execution(* *(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStar1() {
		pointcut.setExpression("execution(* hel*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStar2() {
		pointcut.setExpression("execution(* *el*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchFalse() {
		pointcut.setExpression("execution(* *no*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	@DisplayName("패키지 안에 있는 모든 메소드는 AOP")
	void packageMatch1() {
		pointcut.setExpression("execution(* hello.advanced.member.*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	
	@Test
	@DisplayName("현재 패키지와 하위 패키지 까지 적용")
	void packageMatchSub() {
		pointcut.setExpression("execution(* hello.advanced..*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeExactMatch() {
		pointcut.setExpression("execution(* hello.advanced.member.MemberServiceImpl.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
	@Test
	@DisplayName("부모타입으로 매칭이 가능한가? 가능하다.")
	void typeMatchSuperTypeIsFalse() {
		pointcut.setExpression("execution(* hello.advanced.member.MemberService.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("부모타입으로 매칭은 가능하지 부모타입에 있는 메서드만 적용이 가능하게 된다. \n 그렇기에 실패한다.")
	void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
		pointcut.setExpression("execution(* hello.advanced..MemberService.*(..))");
		Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
		Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeMatchInternal() {
		pointcut.setExpression("execution(* hello.advanced.member.MemberService.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}


}
