package net.ddns.armen181.cafe.cafe_manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"tableOrders","user"})
@Table(name = "cafeTable")
public class CafeTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "userName")
    private String userName;


    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cafeTable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TableOrder> tableOrders = new HashSet<>();



    public CafeTable addTableOrder(TableOrder order) {
        order.setCafeTable(this);
        order.setCafeTableName(this.name);
        this.tableOrders.add(order);
        return this;
    }
}
