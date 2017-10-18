package com.areatak.gazette.dao;
        
        import com.areatak.gazette.entities.CoinPayment;
        import com.areatak.gazette.metadata.CoinType;
        import org.springframework.data.querydsl.QueryDslPredicateExecutor;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;
        
        /**
  * Created by alirezaghias on 3/12/2017 AD.
  */
        @Repository
 public interface CoinPaymentRepo extends CrudRepository<CoinPayment, String>, QueryDslPredicateExecutor<CoinPayment> {
     CoinPayment findByAddressAndCoinType(String address, CoinType coinType);
 }