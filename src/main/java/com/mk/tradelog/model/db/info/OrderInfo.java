package com.mk.tradelog.model.db.info;

import com.mk.tradelog.model.common.Strategy;
import com.mk.tradelog.model.db.orders.Order;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "orderinfo")
@Data
@ToString(callSuper = true)
public class OrderInfo {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "info")
    @PrimaryKeyJoinColumn
    private Order order;

    @Enumerated(EnumType.STRING)
    private Strategy strategy;

}
