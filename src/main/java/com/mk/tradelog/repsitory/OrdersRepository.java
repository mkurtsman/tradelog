package com.mk.tradelog.repsitory;

import com.mk.tradelog.model.db.orders.AbstractOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends CrudRepository<AbstractOrder, Long> {
}
