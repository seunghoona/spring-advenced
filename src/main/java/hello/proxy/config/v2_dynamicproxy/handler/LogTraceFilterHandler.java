package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        var methodName = method.getName();
        if (isPatternMatch(method, args, methodName)) {
            return method.invoke(target, args);
        }

        var message = method.getDeclaringClass().getSimpleName() + "." + methodName + "()";
        var status = logTrace.begin(message);

        try {
            var result = method.invoke(target, args);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    private boolean isPatternMatch(Method method, Object[] args, String methodName) throws IllegalAccessException, InvocationTargetException {
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return true;
        }
        return false;
    }
}
