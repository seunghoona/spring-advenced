package hello.advanced.jpa.domain;

import hello.advanced.jpa.orders.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrdersRepoTest {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private ProductRepo productRepo;

    private Product 상품1;
    private Product 상품2;
    private Product 상품3;

    @BeforeEach
    void beforeAll() {
        Product product = new Product();
        product.setName("상품1");

        Product product2 = new Product();
        product2.setName("상품2");

        Product product3 = new Product();
        product3.setName("상품3");

        상품1 = productRepo.save(product);
        상품2 = productRepo.save(product2);
        상품3 = productRepo.save(product3);

        // when
        Orders orders = new Orders();
        orders.changeName("주문1");
        orders.addProduct(상품1, 상품2, 상품3);

        ordersRepo.save(orders);

        Orders orders2 = new Orders();
        orders2.changeName("주문2");
        orders2.addProduct(상품1, 상품2, 상품3);

        ordersRepo.save(orders2);
    }

    @Test
    void
    상품_주문() {

        // then
        List<Orders> all = ordersRepo.findAll();

        all.stream().forEach(s->s.getOrderProducts().stream().forEach(OrderProduct::getOrders));
    }

    @Test
    void 맵_사용법() {
        // then
        List<String> names = List.of("나승후", "김민호", "강아지");

        String s = names.get(0);
    }

}