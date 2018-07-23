
CREATE TABLE ts_user (
  id varchar(32) NOT NULL,
  user_name varchar(32) NOT NULL,  
  user_password varchar(32) NOT NULL,
  name varchar(100) NOT NULL,
  email varchar(128) NOT NULL,
  ip varchar(32) NOT NULL,
  status varchar(1) NOT NULL,  
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE ts_function (
  id varchar(32) NOT NULL,
  name varchar(32) NOT NULL,  
  target varchar(32) NOT NULL,
  data1 varchar(100) NOT NULL,
  url varchar(128) NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE cgform_db (
  id varchar(32) NOT NULL,
  db_type varchar(32) NOT NULL,  
  db_url varchar(128) NOT NULL,
  db_username varchar(32) NOT NULL,
  db_password varchar(32) NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE cgform_head (
  id varchar(32) NOT NULL,
  db_id varchar(32) NOT NULL,
  schema_name varchar(32),
  package_name varchar(200),
  content varchar(200) NOT NULL,
  create_by varchar(32) default NULL,
  create_date datetime default NULL,
  create_name varchar(32) default NULL,
  is_checkbox varchar(5) NOT NULL,
  is_dbsynch varchar(20) NOT NULL,
  is_pagination varchar(5) NOT NULL,
  is_tree varchar(5) NOT NULL,
  jform_pk_sequence varchar(200) default NULL,
  jform_pk_type varchar(100) default NULL,
  jform_type int(11) NOT NULL,
  jform_version varchar(10) NOT NULL,
  querymode varchar(10) NOT NULL,
  relation_type int(11) default NULL,
  sub_table_str varchar(1000) default NULL,
  tab_order int(11) default NULL,
  table_name varchar(50) NOT NULL,
  update_by varchar(32) default NULL,
  update_date datetime default NULL,
  update_name varchar(32) default NULL,
  tree_parentid_fieldname varchar(50) default NULL,
  tree_id_fieldname varchar(50) default NULL,
  tree_fieldname varchar(50) default NULL,
  jform_category varchar(50) NOT NULL default 'bdfl_ptbd',
  form_template varchar(50) default NULL COMMENT '表单模板',
  form_template_mobile varchar(50) default NULL COMMENT '表单模板样式(移动端)',
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE cgform_field (
  id varchar(32) NOT NULL,
  
  db_name varchar(32) NOT NULL,
  db_default varchar(20) default NULL,
  
  content varchar(200) NOT NULL,
  create_by varchar(255) default NULL,
  create_date datetime default NULL,
  create_name varchar(32) default NULL,
  dictionarys varchar(300) default NULL,
  field_href varchar(200) default NULL,
  field_length int(11) default NULL,
  
  field_valid_type varchar(10) default NULL,
  is_key varchar(2) default NULL,
  is_null varchar(5) default NULL,
  is_query varchar(5) default NULL,
  is_show varchar(5) default NULL,
  is_show_list varchar(5) default NULL,
  length int(11) NOT NULL,

  old_field_name varchar(32) default NULL,
  order_num int(11) default NULL,
  point_length int(11) default NULL,
  query_mode varchar(20) default NULL,
  show_type varchar(30) default NULL,
  type varchar(32) NOT NULL,
  java_name  varchar(50) NOT NULL,
  java_type  varchar(100) NOT NULL,
  java_full_type  varchar(200) NOT NULL,
  java_annotation varchar(400) ,
  queryable varchar(1) default NULL,
  
  update_by varchar(32) default NULL,
  update_date datetime default NULL,
  update_name varchar(32) default NULL,
  table_id varchar(32) NOT NULL,
  extend_json varchar(500) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE cgform_index (
  id varchar(36) NOT NULL COMMENT '主键',
  create_name varchar(50) default NULL COMMENT '创建人名称',
  create_by varchar(50) default NULL COMMENT '创建人登录名称',
  create_date datetime default NULL COMMENT '创建日期',
  update_name varchar(50) default NULL COMMENT '更新人名称',
  update_by varchar(50) default NULL COMMENT '更新人登录名称',
  update_date datetime default NULL COMMENT '更新日期',
  index_name varchar(100) default NULL COMMENT '索引名称',
  index_field varchar(500) default NULL COMMENT '索引栏位',
  index_type varchar(32) default NULL COMMENT '索引类型',
  table_id varchar(32) default NULL COMMENT '主表id',
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;