package com.apptrove.ledgerlyBackend.entities;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "com_ldgr_txn_records")
@Data
public class TransactionRecords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "txn_id")
	private Integer transactionId;
	
	@DateTimeFormat
	@Column(name = "txn_date")
	private Date transactionDate;
	
	@Column(name = "aptmnt_id")
	private Integer aptmntId;
	
	@Column(name = "start_dt")
	private Date startDate;
	
	@Column(name = "end_dt")
	private Date endDate;
	
	@Column(name = "txn_type")
	private String transactionType;
	
	@Column(name = "txn_amnt")
	private Long transactionAmnt;
	
	@Column(name = "maker_cd")
	private Integer makerCd;
	
	@DateTimeFormat
	@Column(name = "maker_dt")
	private Date makerDt;
	
	@Column(name = "maker_rmrks")
	private String makerRmrks;
	
	@Column(name = "auth_status")
	private Integer authStatus;
	
	@Column(name = "author_cd")
	private Integer authorCd;
	
	@DateTimeFormat
	@Column(name = "author_dt")
	private Date authorDt;
	
	@Column(name = "author_rmrks")
	private String authorRmrks;
}
