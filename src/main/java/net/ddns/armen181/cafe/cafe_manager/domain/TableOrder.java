package net.ddns.armen181.cafe.cafe_manager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"cafeTable","productInOrders"})
@Table(name = "tableOrder")
public class TableOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cafeTableName")
    private String cafeTableName;

    @Column(name = "orderStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    private CafeTable cafeTable;

  @OneToMany(mappedBy = "tableOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductInOrder> productInOrders = new HashSet<>();


    public TableOrder addProductInOrder(ProductInOrder productInOrder) {
        productInOrder.setTableOrder(this);
        productInOrder.setOrderName(this.name);
        this.productInOrders.add(productInOrder);
        return this;
    }

}
