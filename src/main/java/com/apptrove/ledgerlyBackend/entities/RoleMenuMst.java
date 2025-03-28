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
@Table(name = "com_ldgr_role_menu_mst")
@Data
public class RoleMenuMst {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "menu_map")
	private String menuMap;
	
	@Column(name = "maker_cd")
	private Integer makerCd;
	
	@Column(name = "maker_dt")
	private Date makerDt;
	
	@Column(name = "author_cd")
	private Integer authorCd;
	
	@Column(name = "author_dt")
	private Date authorDt;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
}
