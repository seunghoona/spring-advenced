package hello.advanced.jpa.orders.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_orderProduct_orders"))
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_orderProduct_product"))
    private Product product;

    public static OrderProduct of(Orders orders, Product product) {
        return of(null, orders, product);
    }

    public static OrderProduct of(Long id, Orders orders, Product product) {
        return new OrderProduct(id, orders, product);
    }


}
