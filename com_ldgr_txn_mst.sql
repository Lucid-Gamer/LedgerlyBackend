create table com_ldgr_txn_records (
	txn_id int primary key auto_increment, 
	txn_date datetime, 
	start_dt date, 
	end_dt date,
	maker_cd int not null, 
	maker_dt datetime, 
	maker_rmrks varchar(500) not null, 
	author_cd int, 
	author_dt datetime, 
	author_rmrks varchar(500)
);

alter table com_ldgr_txn_records add column txn_type varchar(5) not null after end_dt;

alter table com_ldgr_txn_records add column txn_amnt bigint not null after txn_type;

alter table com_ldgr_txn_records add column aptmnt_id int not null after txn_date;

alter table com_ldgr_txn_records add column auth_status tinyint(1) default 0 after txn_amnt;

create table com_ldgr_txn_mst (
	mst_id int primary key auto_increment, 
	mst_param_name varchar(30) unique not null, 
	mst_param_value varchar(256), 
	is_active tinyint(1) default 0, 
	maker_cd int, 
	maker_dt datetime not null, 
	maker_rmrks varchar(200) not null, 
	author_cd int, 
	author_dt datetime, 
	author_rmrks varchar(200)
);