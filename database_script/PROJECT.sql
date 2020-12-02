create database project_dbms character set = 'utf8';

use project_dbms;

-- Khi lỗi gì thì xóa hết làm lại từ đầu
-- drop table order_details;
-- drop table orders;
-- drop table products;
-- drop table customers;

-- Tạo bảng
create table Customers(
	ID int primary key AUTO_INCREMENT,
    fullName varchar(50) not null unique,
    phoneNumber varchar(11) not null unique check (phoneNumber regexp '^[0-9]{10}$'),
    email varchar(50) unique check (email regexp '^[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9][a-zA-Z0-9._-]*\\.[a-zA-Z]{2,4}$'),
    address varchar(200),
    dateCreate datetime not null default now()
);

create table Products(
	ID int primary key AUTO_INCREMENT,
    productName varchar(200) not null,
    inStock int check (inStock >= 0),
    unitPrice float not null,
    sale float
);

create table Orders(
	ID int primary key AUTO_INCREMENT,
    dateCreate datetime not null default now(),
    customerID int,
	checkout char(20) default 'Chưa thanh toán' check (checkout in ('Đã thanh toán', 'Chưa thanh toán')),
    foreign key (customerID) references Customers(ID)
);

create table Order_Details(
	ID int primary key AUTO_INCREMENT,
    productID int not null,
    orderID int not null,
    quantity int not null default 1,
    sale float,
    foreign key (productID) references Products(ID),
    foreign key (orderID) references Orders(ID)
);

-- Thêm dữ liệu
insert into Customers (fullName, phoneNumber, email, address) values ('guest', '0000000000', 'xxx@xxx.com', 'xxx');
insert into Customers (fullName, phoneNumber, email, address) values ('Mai Phuoc Vinh', '0907890278', 'maiphuocvinh143@gmail.com', 'Long Hòa, Bình Thủy, Cần Thơ');
insert into Customers (fullName, phoneNumber, email, address) values ('Mai Duc Phat', '0939750469', 'phuocvinh1143@gmail.com', 'Long Hòa, Bình Thủy, Cần Thơ');
insert into Customers (fullName, phoneNumber, email, address) values ('Ngo Hong Quoc Bao', '0123456789', 'baobao@gmail.com', 'Hưng Phú, Ninh Kiều, Cần Thơ');
insert into Customers (fullName, phoneNumber, email, address) values ('Huynh Quan Nhat Hao', '0123456788', 'haohao@gmail.com', 'Ô Môn, Cần Thơ');
insert into Customers (fullName, phoneNumber, email, address) values ('Bui Quoc Trong', '0123456777', 'trongbui@gmail.com', 'Ô MÔN');
insert into Customers (fullName, phoneNumber, email, address) values ('Duong Dang Khoa', '0123456778', 'khoanon@gmail.com', 'SÓC TRĂNG');
insert into Customers (fullName, phoneNumber, email, address) values ('Le Chanh Nhut', '0123456779', 'nhutle@gmail.com', 'GIÒNG RIỀNG');
insert into Customers (fullName, phoneNumber, email, address) values ('Truong Hoang Thuan', '0123456798', 'thuanbui@gmail.com', 'NGÃ 6');

insert into Products (productName, inStock, unitPrice, sale) values ('Camera IP Wifi Ezviz C6N 1080p', 5, 2400000, 0.57);
insert into Products (productName, inStock, unitPrice, sale) values ('Camera Sony DSC H300 - 20.1 Megapixel, Zoom 35x', 10, 500000, 0.24);
insert into Products (productName, inStock, unitPrice, sale) values ('Flycam', 7, 550000, 0.33);
insert into Products (productName, inStock, unitPrice, sale) values ('Tai Nghe Bluetooth Apple AirPods Pro True Wireless - MWP22', 10, 8100000, 0.41);
insert into Products (productName, inStock, unitPrice, sale) values ('Apple Macbook Air 2020 - 13 Inchs (i3-10th/ 8GB/ 256GB)', 10, 28990000, 0.15);
insert into Products (productName, inStock, unitPrice, sale) values ('Apple Macbook Pro 2020 - 13 Inchs (i5-8th/ 8GB/ 256GB)', 5, 39990000, 0.17);
insert into Products (productName, inStock, unitPrice, sale) values ('Dien Thoai iPhone 11 64GB', 20, 21990000, 0.23);
insert into Products (productName, inStock, unitPrice, sale) values ('Dien Thoai iPhone 11 Pro Max 64GB', 25, 29500000, 0.2);
insert into Products (productName, inStock, unitPrice, sale) values ('Dien Thoai iPhone 12 Pro Max 512GB', 25, 40000000, 0.2);
insert into Products (productName, inStock, unitPrice, sale) values ('Dien Thoai iPhone 12 Mini 256GB', 10, 20000000, 0.2);
insert into Products (productName, inStock, unitPrice, sale) values ('iPad Pro 256GB', 10, 30000000, 0.3);
insert into Products (productName, inStock, unitPrice, sale) values ('Samsung Galaxy Note 10', 10, 20000000, 0.1);
insert into Products (productName, inStock, unitPrice, sale) values ('Chuot Logitech G502', 10, 1000000, 0.1);
insert into Products (productName, inStock, unitPrice, sale) values ('Chuot Logitech G Pro Wireless', 50, 3000000, 0.2);
insert into Products (productName, inStock, unitPrice, sale) values ('Laptop Gaming ...', 10, 30000000, 0.1);
insert into Products (productName, inStock, unitPrice, sale) values ('Apple iMac 2019 MRR12 27 inch 5K', 10, 69990000, 0.28);
insert into Products (productName, inStock, unitPrice, sale) values ('iPad Mini 5 Wi-Fi 64GB', 6, 8990000, 0.25);
insert into Products (productName, inStock, unitPrice, sale) values ('iPad 10.2 Inch WiFi 32GB (Gen 8) New 2020', 10, 10990000, 0.23);
insert into Products (productName, inStock, unitPrice, sale) values ('iPad Pro 12.9 inch (2020) Wifi', 10, 10990000, 0.23);
insert into Products (productName, inStock, unitPrice, sale) values ('Loa Vi Tinh Logitech Z906 5.1 1000W', 15, 6820000, 0.13);
insert into Products (productName, inStock, unitPrice, sale) values ('Gopro Hero 9 Black', 15, 11990000, 0.08);

insert into Orders (customerID, checkout) values (1, 'Đã thanh toán');
insert into Orders (customerID, checkout) values (2, 'Chưa thanh toán');
insert into Orders (customerID, checkout) values (3, 'Đã thanh toán');

insert into Order_Details (productID, orderID, sale) values (1, 1, 0.57);
insert into Order_Details (productID, orderID, sale) values (2, 1, 0.24);
insert into Order_Details (productID, orderID, sale) values (3, 1, 0.33);
insert into Order_Details (productID, orderID, sale) values (4, 1, 0.41); 
insert into Order_Details (productID, orderID, sale) values (5, 2, 0.15); 
insert into Order_Details (productID, orderID, sale) values (6, 2, 0.17);
insert into Order_Details (productID, orderID, sale) values (7, 2, 0.23);
insert into Order_Details (productID, orderID, sale) values (8, 2, 0.2);
insert into Order_Details (productID, orderID, sale) values (5, 3, 0.15);
insert into Order_Details (productID, orderID, sale) values (6, 3, 0.17);
insert into Order_Details (productID, orderID, sale) values (7, 3, 0.23);
insert into Order_Details (productID, orderID, sale) values (8, 3, 0.2);

select * from Orders;

-- Tạo khách hàng mới với tham số tên KH, sđt, email và địa chỉ
DELIMITER //
create procedure p_createCustomer (p_fullName varchar(50), p_phoneNumber varchar(11), p_email varchar(50), p_address varchar(200))
begin
	insert into Customers (fullName, phoneNumber, email, address) values (p_fullName, p_phoneNumber, p_email, p_address);
end//

-- Xóa Khách hàng (ko sử dụng)
DELIMITER //
create procedure p_deleteCustomer (p_ID int)
begin
	delete from Customers where ID = p_ID;
end//

-- Thêm hàng hóa mới vào kho với tham số tên hàng, số lượng nhập kho, giá và khuyến mãi.
-- Nếu đã có trong kho thì cập nhật inStock, đơn giá và sale
DELIMITER //
create procedure p_addProduct (p_productName varchar(200), p_inStock int, p_unitPrice float, p_sale float)
begin
    if exists(select * from Products where productName = p_productName) then
        update Products 
        set inStock = inStock + p_inStock, unitPrice = p_unitPrice, sale = p_sale
        where productName = p_productName;

        else insert into Products (productName, inStock, unitPrice, sale) values (p_productName, p_inStock, p_unitPrice, p_sale);
    end if;    
end//

-- Cập nhật giá sản phẩm bằng cách chỉnh mức độ sale sp. Tham số đầu vào là ID sản phẩm và giá mới muốn cập nhật
DELIMITER //
create procedure p_modifyPrice (p_productID int, p_newUnitPrice float)
begin
	declare _unitPrice float;
    select unitPrice into _unitPrice from Products where ID = p_productID;
	update Products set sale = round((unitPrice - p_newUnitPrice) / unitPrice, 2) where ID = p_productID;
end//

-- Tạo đơn hàng mới với tham số tên Khách Hàng
DELIMITER //
create procedure p_createOrder (p_customerName varchar(50))
begin
	declare cusID int;
	select ID into cusID from Customers where fullName = p_customerName;
    insert into Orders (customerID) values (cusID);
end//

DELIMITER //
create procedure p_deleteOrder (p_orderID int)
begin
	delete from Order_Details where orderID = p_orderID;
	delete from Orders where ID = p_orderID;
end//

-- Đánh dấu là đơn hàng đã thanh toán với tham số ID đơn hàng, trừ số lượng trong kho
DELIMITER //
create procedure p_checkOut (p_orderID int)
begin
	update Orders set checkout = 'Đã thanh toán' where ID = p_orderID;
end//

-- Giảm số lượng sản phẩm trong kho khi thanh toán với tham số là id sản phẩm
DELIMITER //
create procedure p_decrease_product_instock (p_productName varchar(200), p_quantity int)
begin
	update Products set inStock = inStock - p_quantity where productName = p_productName;
end//

-- Thêm vào sản phẩm vào hóa đơn (nếu đã có sản phẩm rồi thì tăng số lượng ngược lại thì thêm sản phẩm vào với số lượng là 1) 
-- Tham số là ID hóa đơn và ID sản phẩm
DELIMITER //
create procedure p_add2Cart (p_orderID int, p_productID int)
begin
	declare v_sale float;
    select sale into v_sale from Products where ID = p_productID;
	if exists(select * from Order_Details where orderID = p_orderID and productID = p_productID) then
		update Order_Details set quantity = quantity + 1 where orderID = p_orderID and productID = p_productID;
		else insert into Order_Details (productID, orderID, sale) values (p_productID, p_orderID, v_sale);
	end if;
end//

-- Xóa sản phẩm ra khỏi đơn hàng với tham số lần lượt là ID hóa đơn và ID sản phẩm
DELIMITER //
create procedure p_popFromCart (p_orderID int, p_productID int)
begin
	delete from Order_Details where orderID = p_orderID and productID = p_productID;
end//

SET GLOBAL log_bin_trust_function_creators = 1//

-- Tính tổng giá trị đơn hàng theo ID hóa đơn
DELIMITER //
create function f_sumCart (p_orderID int)
returns float
begin
	declare ans float;
	select round(sum(quantity*unitPrice*(1 - od.sale)), 3) into ans from Order_Details od join Products p on od.productID = p.ID 
    where od.orderId = p_orderID
    group by od.orderID;
    
    return ans;
end//

DELIMITER //
create procedure p_sumCart (p_orderID int)
begin
	declare ans float;
	select round(sum(quantity*unitPrice*(1 - od.sale)), 3) into ans from Order_Details od join Products p on od.productID = p.ID 
    where od.orderId = p_orderID
    group by od.orderID;
end//

-- Liệt kê toàn bộ thông tin một bảng với tham số sẽ là tên bảng muốn liệt kê
DELIMITER //
create procedure p_listSomething (p_tableName varchar(20))
begin
	if p_tableName = 'Products' then
		select * from Products; end if;
	if p_tableName = 'Customers' then
        select * from Customers; end if;
	if p_tableName = 'Orders' then
        select o.ID, o.dateCreate, c.fullName as customerName, f_sumCart(o.ID) as sumCart, checkout 
        from Orders o join Customers c on o.customerID = c.ID; end if;
end//

-- Trả về trạng thái đơn hàng với tham số là ID đơn hàng
DELIMITER //
create procedure p_listCheckOut(p_orderID int) 
begin
	select checkout from Orders where ID = p_orderID;
end//

-- Liệt kê toàn bộ sản phẩm theo tham số ID đơn hàng
DELIMITER //
create procedure p_listProducts (p_orderID int)
begin
	select p.productName as productName, od.quantity as quantity, round(p.unitPrice*(1 - od.sale)*od.quantity, 3) as price 
    from Order_Details od join Products p on od.productID = p.ID
    where od.orderID = p_orderID;
end//

DELIMITER //
create procedure p_getIDbyProductName (p_productName varchar(200))
begin
	select ID from Products where productName=p_productName;
end//

-- Trigger ghi nhớ ngày thanh toán khi thay đổi trạng thái của đơn hàng
DELIMITER //
create table order_log (
	id int not null primary key AUTO_INCREMENT,
	orderID int,
    _date datetime not null default now()
)//

create trigger after_order_change
after update on Orders 
for each row
begin
	insert into order_log 
    set
		orderID = old.id;
end//
