CREATE TABLE mysql.idus_user (
	number bigint auto_increment PRIMARY KEY,
	name varchar(20) NOT NULL,
	nick_name varchar(30) UNIQUE NOT NULL,
	password varchar(300) NOT NULL,
	phone varchar(20) NOT NULL,
	email varchar(100) UNIQUE NOT NULL,
	gender varchar(10) NULL,
	create_date datetime NULL,
	update_date datetime NULL,
	delete_date datetime NULL,
	role varchar(1) DEFAULT 'U',
	use_yn varchar(1) DEFAULT 'Y'

)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

