package com.areatak.gazette.dao;

import com.areatak.gazette.entities.Transaction;
import com.areatak.gazette.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
@Repository
public interface TransactionRepo extends CrudRepository<Transaction, String>, QueryDslPredicateExecutor<Transaction>, PagingAndSortingRepository<Transaction, String> {
	Transaction findByDataHash(String dataHash);
	
	Transaction findByUserAndDataHash(User user, String dataHash);
	
	@Query("select t from Transaction t where t.user = ?1 and t.paymentStatus = ?2 and (lower(t.txId) like lower(concat('%', lower(?3), '%')) or lower(t.desc) like lower(concat('%', lower(?4), '%')))")
	List<Transaction> findWithParameter(User user, Transaction.PaymentStatus paymentStatus, String txId, String desc, Sort sort);
	
	@Query("select t from Transaction t where t.user = ?1 and t.paymentStatus = ?2 and t.created between ?5 and ?6 and (lower(t.txId) like lower(concat('%', lower(?3), '%')) or lower(t.desc) like lower(concat('%', lower(?4), '%')))")
	List<Transaction> findWithParameter(User user, Transaction.PaymentStatus paymentStatus, String txId, String desc, Date from, Date to, Sort sort);
	
	List<Transaction> findByUserAndPaymentStatusAndTxIdContainingIgnoreCase(User user, Transaction.PaymentStatus paymentStatus, String txId, Sort sort);
	
	List<Transaction> findByUserAndPaymentStatusAndTxIdContainingIgnoreCaseAndCreatedBetween(User user, Transaction.PaymentStatus paymentStatus, String txId, Date from, Date to, Sort sort);
	
	List<Transaction> findByUserAndPaymentStatusAndDescContainingIgnoreCase(User user, Transaction.PaymentStatus paymentStatus, String desc, Sort sort);
	
	List<Transaction> findByUserAndPaymentStatusAndDescContainingIgnoreCaseAndCreatedBetween(User user, Transaction.PaymentStatus paymentStatus, String desc, Date from, Date to, Sort sort);
	
	List<Transaction> findByUserAndCreatedGreaterThanEqual(User user, Date created);
	
	List<Transaction> findByUserAndPaymentStatusAndCreatedGreaterThanEqual(User user, Transaction.PaymentStatus paymentStatus, Date created, Sort sort);
	
	Transaction findByTxId(String txId);
	
	List<Transaction> findAllByConfirmations(int confirmations);
	
	List<Transaction> findByUserAndPaymentStatusAndCreatedBetween(User user, Transaction.PaymentStatus paymentStatus, Date from, Date to, Sort sort);
	
	Transaction findByIaaId(UUID iaaId);
	
	Transaction findById(String id);
	
}
