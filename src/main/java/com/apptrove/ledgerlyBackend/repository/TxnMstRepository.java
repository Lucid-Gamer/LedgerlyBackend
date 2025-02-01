package com.apptrove.ledgerlyBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrove.ledgerlyBackend.entities.TransactionMst;

public interface TxnMstRepository extends JpaRepository<TransactionMst, Integer> {

}
