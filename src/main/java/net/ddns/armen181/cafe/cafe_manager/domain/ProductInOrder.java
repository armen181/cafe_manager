package net.ddns.armen181.cafe.cafe_manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(exclude = {"tableOrder","product"})
@Table(name = "productInOrder")
public class ProductInOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount",nullable = false)
    private int amount;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductInOrderStatus status;

    @Column(name = "orderName")
    private String orderName;

    @ManyToOne
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    private TableOrder tableOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    public ProductInOrder addProduct(Product product) {
        //product.addProductInOrder(this);
        this.product=product;
        return this;
    }
}
