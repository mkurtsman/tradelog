package com.mk.tradelog.repsitory;

import com.mk.tradelog.model.common.Strategy;
import com.mk.tradelog.model.db.orders.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MarketRepository extends CrudRepository<Order, Long> {

    @Query("select o from Order  as o join o.info as info where (:account = null or o.account = :account ) and " +
            "(:startDate = null or  o.closeDate >= :startDate) and" +
            "(:endDate = null or o.closeDate <= :endDate ) and " +
            "(:ticker = null or o.ticker <= :ticker  ) and  " +
            "(:strategy = null or info.strategy = :strategy  )"
    )
    Iterable<Order> findByParams(@Param("account") String account,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate")LocalDateTime endDate,
                                 @Param("ticker") String ticker,
                                 @Param("strategy")Strategy strategy);
}

