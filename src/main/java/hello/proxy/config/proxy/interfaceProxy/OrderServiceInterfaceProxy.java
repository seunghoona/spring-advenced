package hello.proxy.config.proxy.interfaceProxy;

import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1 {

    private final OrderServiceV1 target;
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {

        var status = logTrace.begin("OrderService.orderItem()");
        try {
            target.orderItem(itemId);
            logTrace.end(status);
        }catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
