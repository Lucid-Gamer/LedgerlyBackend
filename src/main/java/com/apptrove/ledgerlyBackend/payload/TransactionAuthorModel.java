package com.apptrove.ledgerlyBackend.payload;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionAuthorModel {

	private Integer transactionId;
	
	private Date authorDt;
	
	private Integer authorCd;
	
	private Integer authStatus;
	
	private String authorRmrks;
	
}
