package com.mk.tradelog.model.db.orders;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "BALANCE")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Balance extends AbstractOrder {

    private BigDecimal profit;
    private String comment;

}
