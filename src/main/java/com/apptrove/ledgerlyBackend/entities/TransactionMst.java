package com.apptrove.ledgerlyBackend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "com_ldgr_txn_mst")
@Data
public class TransactionMst {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mst_id")
	private Integer mstId;
	
	@Column(name = "mst_param_name")
	private String paramName;
	
	@Column(name = "mst_param_value")
	private String paramValue;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "maker_cd")
	private Integer makerCd;
	
	@Column(name = "maker_dt")
	private Date makerDt;
	
	@Column(name = "maker_rmrks")
	private String makerRmrks;
	
	@Column(name = "author_cd")
	private Integer authorCd;
	
	@Column(name = "author_dt")
	private Date authorDt;
	
	@Column(name = "author_rmrks")
	private String authorRmrks;
}
