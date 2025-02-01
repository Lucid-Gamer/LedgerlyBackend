package com.apptrove.ledgerlyBackend.payload;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionMakerModel {

	private Integer transactionId;
	
	private Date transactionDate;
	
	private Integer aptmntId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String transactionType;
	
	private Long transactionAmnt;
	
	private Integer makerCd;
	
	private Date makerDt;
	
	private String makerRmrks;
}
