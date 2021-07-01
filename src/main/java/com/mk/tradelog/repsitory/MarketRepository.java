package com.mk.tradelog.repsitory;

import com.mk.tradelog.model.orders.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MarketRepository extends CrudRepository<Order, Long> {

    @Query("select o from Order o where (:account = null or o.account = :account ) and " +
            "(:startDate = null or  o.closeDate >= :startDate) and" +
            "(:endDate = null or o.closeDate <= :endDate ) and " +
            "(:ignoreTicker = 1 or :equals = 1 and o.ticker = :ticker or :equals = 0 and o.ticker <> :ticker )"
    )
    Iterable<Order> findByParams(@Param("account") String account,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate")LocalDateTime endDate,
                                 @Param("ignoreTicker") Integer ignoreTicker,
                                 @Param("equals") Integer equal,
                                 @Param("ticker") String ticker);
}

