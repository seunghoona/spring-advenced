package hello.proxy.config.proxy.concrete_proxy;

import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private OrderServiceV2 target;
    private LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

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
