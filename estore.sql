create database estore;
use estore;

create table book(
  id int auto_increment,  #主键
  name varchar(100),       #书名
  price float,             #价格
  author varchar(50),     #作者
  publish varchar(50),    #出版社
  isbn varchar(20),       #书号
  page int,               #页码
  type varchar(50),       #类型
  desc_url varchar(100),     #简介地址
  pic_url varchar(100),      #图片地址
  constraint book_id_pk primary key (id)
);
create table customer(
  id int auto_increment,   #主键
  name varchar(40),         #用户名
  password varchar(50),    #密码
  zip varchar(20),          #邮编
  address1 varchar(120),    #地址
  address2 varchar(120),
  address3 varchar(120),
  home_phone varchar(20),   #家庭电话
  office_phone varchar(20), #办公电话
  mobile_phone varchar(20), #手机
  email varchar(50),          #电子邮件
  constraint customer_id_pk primary key (id)
);

create table orderform(
  id int auto_increment,  #主键
  cost float,              #总额
  orderdate date,         #订单日期
  customerid int,         #用户编号
  status varchar(20),     #支付状态
  payway varchar(20),     #支付方式
  constraint orderform_id_pk primary key (id),
  constraint orderform_customerid_fk foreign key (customerid) references customer(id)
);
create table orderline(
  id int,       #主键
  num int,      #数量
  orderid int,  #订单编号
  bookid int,   #书本编号
  constraint orderline_id_pk primary key (id),
  constraint orderline_orderid_fk foreign key (orderid) references orderform(id),
  constraint orderline_bookid_fk foreign key (bookid) references book(id)
);

create table cartline(
  id int auto_increment,  #主键
  num int,                 #数量
  customerid int,         #用户编号
  bookid int,             #书本编号
  constraint cartline_id_pk primary key (id),
  constraint cartline_customerid_fk foreign key (customerid) references customer(id),
  constraint cartline_bookid_fk foreign key (bookid) references book(id)
);

insert into book values
  (1,'精通Hibernate：Java对象持久化技术详解',59.0,'孙卫琴','电子工业出版社','9787121093739',600,'软件与程序设计','1.txt','zcover2.gif');
insert into book values
  (2,'Effective Java中文版',39.0,'Joshua Bloch','机械工业出版社','9787111255833',287,'软件与程序设计','2.txt','zcover4.gif');
insert into book values
  (3,'精通Spring',39.0,'罗时飞','电子工业出版社','9787121072987',521,'软件与程序设计','3.txt','zcover8.gif');
insert into book values
  (4,'深入浅出Hibernate',59.0,'夏昕','电子工业出版社','9787121006708',543,'软件与程序设计','4.txt','zcover6.gif');
insert into book values
  (5,'JAVA编程思想：第3版',95.0,'Bruce Eckel','机械工业出版社','711116220x',796,'软件与程序设计','5.txt','zcover.gif');
insert into book values
  (6,'Java 2核心技术（第6版） 卷I：基础知识',75.0,'叶乃文','机械工业出版社','7111185234',691,'软件与程序设计','6.txt','zcover3.gif');
insert into book values
  (7,'Tomcat与Java Web开发技术详解',45.0,'孙卫琴','电子工业出版社','9787121072970',734,'软件与程序设计','7.txt','zcover7.gif');
insert into book values
  (8,'Java与模式',88.0,'阎宏','电子工业出版社','9787505380004',1024,'软件与程序设计','8.txt','zcover5.gif');

commit ;