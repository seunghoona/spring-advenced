package hello.advanced.jpa.orders.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @BatchSize(size = 5)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addProduct(@NonNull Product... products) {
        if (alreadyProduct(products)) return;
        this.orderProducts = Arrays.stream(products)
                .map(product -> OrderProduct.of(this, product))
                .collect(Collectors.toList());
    }

    private boolean alreadyProduct(Product[] products) {
        return Arrays.stream(products)
                .anyMatch(product -> orderProducts.contains(product));
    }

    public List<OrderProduct> getOrderProducts() {
        return Collections.unmodifiableList(orderProducts);
    }

    public void changeName(String name) {
        this.name = name;
    }
}
