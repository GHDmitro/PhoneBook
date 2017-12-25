For boot this project you must have java on PC

To run application by docker

   git clone https://github.com/GHDmitro/phonebook.git
   cd phonebook
   
   mvn clean package 
   
   docker:build
   
   docker run -it phonebook:1.0
   
   <!--docker run --name demo-mysql -e MYSQL_ROOT_PASSWORD=password -e-->
    <!--MYSQL_DATABASE=demo -e MYSQL_USER=demo_user -e -->
    <!--MYSQL_PASSWORD=demo_pass -d mysql:5.6-->
   
   
   docker logs demo-mysql
   
   
   docker run -p 8080:8080 --name phonebookapp --link demo-mysql:mysql -d dtimosh96/phonebook
   
   docker logs phonebookapp
   
   <!--./mvnw install dockerfile:build-->
   

To run this application by java in terminal:
    java -jar target/phonebook-1.jar
    
To run by maven: 
    1. install maven on you pc
    2. cd ~/Phonebook 
    3. mvn spring-boot:run  


____________________________________________________________________________________________________________

I have created phone book application. It based on Spring boot framework. I used to next technologies:
    1. Spring Boot, Spring Data Jpa, Spring Boot Security, MySql database ;
    2. Bootstrap, HTML, CSS, JS, jQuery, Ajax (Json objects, json forms) ;
    3. Spring Boot Test, JUnit, Mockito ;
____________________________________________________________________________________________________________



CREATE DATABASE IF NOT EXISTS bookphone;

alter table account_phone_book drop foreign key FK4mlhr6ocit674rapk0ojagodb;
alter table account_phone_book drop foreign key FK7ylxhv1ir64601b4upqakikop;
alter table record drop foreign key FKckk7kosgilv2f2k5b5djrfvqy;

-- this lines for CREATE-DROP tables, to avoid this, comment those lines
drop table if exists account;
drop table if exists account_phone_book;
drop table if exists record;

create table account (
  id bigint not null auto_increment,
  login varchar(255),
  password varchar(255),
  confirm_password varchar(255),
  name varchar(255),
  surname varchar(255),
  lastname varchar(255),
  primary key (id)
);

create table account_phone_book (
  account_id bigint not null,
  phone_book_id bigint not null,
  primary key (account_id, phone_book_id)
);

create table record (
  id bigint not null auto_increment,
  address varchar(255),
  email varchar(255),
  home_number varchar(255),
  lastname varchar(255),
  mobile_number varchar(255),
  name varchar(255),
  surname varchar(255),
  account_id bigint,
  primary key (id)
);

alter table account_phone_book
  add constraint UK_h7ov28f6oc7cq4b7tbenomyue unique (phone_book_id);

alter table account_phone_book
  add constraint FK4mlhr6ocit674rapk0ojagodb foreign key (phone_book_id) references record (id);

alter table account_phone_book
  add constraint FK7ylxhv1ir64601b4upqakikop foreign key (account_id) references account (id);

alter table record
  add constraint FKckk7kosgilv2f2k5b5djrfvqy foreign key (account_id) references account (id);

