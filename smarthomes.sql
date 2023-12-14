create database smarthomes;

use smarthomes;

create table Registration(username varchar(40),password varchar(40),repassword varchar(40),
usertype varchar(40));


Create table CustomerOrders
(
OrderId integer,
userName varchar(40),
orderName varchar(40),
orderPrice double,
userAddress varchar(40),
creditCardNo varchar(40),
dayDate date,
Primary key(OrderId,userName,orderName)
);

Create table Productdetails
(
ProductType varchar(20),
Id varchar(20),
productName varchar(40),
productPrice double,
productImage varchar(40),
productManufacturer varchar(40),
productCondition varchar(40),
productDiscount double,
productOnSale varchar(40),
productQuantity INT,
Primary key(Id)
);


Create table Productlist
(
ProductType varchar(20),
Id varchar(20),
productName varchar(40),
productPrice double,
productImage varchar(40),
productManufacturer varchar(40),
productCondition varchar(40),
productDiscount double,
Primary key(Id)
);

CREATE TABLE Product_accessories (
    productName varchar(20),
    accessoriesName  varchar(20),
    
    
    FOREIGN KEY (productName) REFERENCES Productdetails(Id) ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (accessoriesName) REFERENCES Productdetails(Id) ON DELETE SET NULL
        ON UPDATE CASCADE    
);