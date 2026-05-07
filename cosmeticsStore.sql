-- ==========================================================
-- 1. DATABASE SIFIRLAMA VE KURULUM
-- ==========================================================
DROP DATABASE IF EXISTS CosmeticsStore;
CREATE DATABASE CosmeticsStore;
USE CosmeticsStore;

-- ==========================================================
-- 2. TABLO YAPILARI (DDL) - Orijinal Tasarım
-- ==========================================================
CREATE TABLE Users (
    userID INT PRIMARY KEY,
    fullName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    role VARCHAR(100) NOT NULL
);

CREATE TABLE Categories (
    categoryID INT PRIMARY KEY,
    categoryName VARCHAR(255) NOT NULL,
    parentCategoryID INT,
    CONSTRAINT FK_CategoryParent FOREIGN KEY (parentCategoryID) 
        REFERENCES Categories(categoryID) ON DELETE SET NULL
);

CREATE TABLE Products (
    productID INT PRIMARY KEY,
    categoryID INT NOT NULL,
    brandName VARCHAR(255) NOT NULL,
    basePrice DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT FK_ProductCategory FOREIGN KEY (categoryID) 
        REFERENCES Categories(categoryID)
);

CREATE TABLE Product_Variants (
    variantID INT PRIMARY KEY,
    productID INT NOT NULL,
    color VARCHAR(100),
    size VARCHAR(100),
    shade VARCHAR(255),
    sku_Code VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT FK_VariantProduct FOREIGN KEY (productID) 
        REFERENCES Products(productID) ON DELETE CASCADE
);

CREATE TABLE Inventory (
    inventoryID INT PRIMARY KEY,
    variantID INT NOT NULL,
    stockQuantity INT NOT NULL DEFAULT 0,
    warehouseLocation VARCHAR(255),
    CONSTRAINT FK_InventoryVariant FOREIGN KEY (variantID) 
        REFERENCES Product_Variants(variantID)
);

CREATE TABLE Orders (
    orderID INT PRIMARY KEY,
    userID INT NOT NULL,
    orderDate DATETIME,
    totalAmount DECIMAL(12, 2) NOT NULL,
    status VARCHAR(100) NOT NULL,
    CONSTRAINT FK_OrderUser FOREIGN KEY (userID) 
        REFERENCES Users(userID)
);

CREATE TABLE Order_Details (
    detailID INT PRIMARY KEY,
    orderID INT NOT NULL,
    variantID INT NOT NULL,
    quantity INT NOT NULL,
    unitPrice DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_DetailOrder FOREIGN KEY (orderID) 
        REFERENCES Orders(orderID),
    CONSTRAINT FK_DetailVariant FOREIGN KEY (variantID) 
        REFERENCES Product_Variants(variantID)
);

CREATE TABLE Payments (
    paymentID INT PRIMARY KEY,
    orderID INT NOT NULL,
    paymentMethod VARCHAR(100) NOT NULL,
    transactionID VARCHAR(100) NOT NULL UNIQUE,
    paymentStatus VARCHAR(255) NOT NULL,
    CONSTRAINT FK_PaymentOrder FOREIGN KEY (orderID) 
        REFERENCES Orders(orderID)
);

-- ==========================================================
-- 3. DEVASA VERİ GİRİŞİ (DML)
-- ==========================================================

-- KULLANICILAR (Müşteriler ve Adminler)
INSERT INTO Users VALUES 
(1, 'Ayse Yilmaz', 'ayse.y@email.com', 'ayse123', 'Cankaya, Ankara', 'Admin'),
(2, 'Fatma Kaya', 'fatma.k@email.com', 'fatma456', 'Konak, Izmir', 'Customer'),
(3, 'Mehmet Demir', 'meto.d@email.com', 'memo789', 'Besiktas, Istanbul', 'Customer'),
(4, 'Can Ozcan', 'can.o@email.com', 'can321', 'Bornova, Izmir', 'Customer'),
(5, 'Zeynep Ak', 'zeynep.ak@email.com', 'zey99', 'Kizilay, Ankara', 'Customer'),
(6, 'Burak Sahin', 'burak.s@email.com', 'brk44', 'Kadikoy, Istanbul', 'Customer'),
(7, 'Elif Celik', 'elif.c@email.com', 'elif55', 'Buca, Izmir', 'Customer'),
(8, 'Arda Kurt', 'arda.k@email.com', 'arda77', 'Kecioren, Ankara', 'Customer'),
(9, 'Selin Yildiz', 'selin.y@email.com', 'selin123', 'Alsancak, Izmir', 'Customer'),
(10, 'Deniz Arslan', 'deniz.a@email.com', 'deniz456', 'Sisli, Istanbul', 'Customer'),
(11, 'Kaan Korkmaz', 'kaan.k@email.com', 'kaan789', 'Mamak, Ankara', 'Customer'),
(12, 'Buse Tekin', 'buse.t@email.com', 'buse321', 'Karsiyaka, Izmir', 'Customer'),
(13, 'Ozan Gunes', 'ozan.g@email.com', 'ozan99', 'Uskudar, Istanbul', 'Customer'),
(14, 'Cemre Aydin', 'cemre.a@email.com', 'cemre44', 'Yenimahalle, Ankara', 'Customer'),
(15, 'Tarik Eser', 'tarik.e@email.com', 'tarik55', 'Balcova, Izmir', 'Customer'),
(16, 'Melis Koc', 'melis.k@email.com', 'melis77', 'Maltepe, Istanbul', 'Customer'),
(17, 'Emre Polat', 'emre.p@email.com', 'emre123', 'Etimesgut, Ankara', 'Customer'),
(18, 'Cansu Erdem', 'cansu.e@email.com', 'cansu456', 'Goztepe, Izmir', 'Customer'),
(19, 'Berk Yasar', 'berk.y@email.com', 'berk789', 'Sariyer, Istanbul', 'Customer'),
(20, 'Derya Can', 'derya.c@email.com', 'derya321', 'Sincan, Ankara', 'Customer'),
(21, 'Volkan Isik', 'volkan.i@email.com', 'volkan99', 'Cigli, Izmir', 'Customer'),
(22, 'Gizem Avci', 'gizem.a@email.com', 'gizem44', 'Bakirkoy, Istanbul', 'Customer'),
(23, 'Kerem Bulut', 'kerem.b@email.com', 'kerem55', 'Polatli, Ankara', 'Customer'),
(24, 'Asli Turan', 'asli.t@email.com', 'asli77', 'Gaziemir, Izmir', 'Customer'),
(25, 'Gokhan Sen', 'gokhan.s@email.com', 'gokhan123', 'Zeytinburnu, Istanbul', 'Customer'),
(26, 'Ece Dogan', 'ece.d@email.com', 'ece456', 'Gazi, Ankara', 'Customer'),
(27, 'Hakan Ozturk', 'hakan.o@email.com', 'hakan789', 'Foca, IzUr', 'Customer'),
(28, 'Bahar Kaplan', 'bahar.k@email.com', 'bahar321', 'Fatih, Istanbul', 'Customer'),
(29, 'Umut Kiliç', 'umut.k@email.com', 'umut99', 'Golbasi, Ankara', 'Customer'),
(30, 'Merve Cetin', 'merve.c@email.com', 'merve44', 'Urla, Izmir', 'Customer');

-- GENİŞLETİLMİŞ KATEGORİLER
INSERT INTO Categories VALUES 
(10, 'Cosmetics', NULL), (11, 'Skin Care', 10), (12, 'Moisturizers', 11),
(13, 'Cleansers', 11), (14, 'Serums', 11), (15, 'Masks', 11),
(20, 'Makeup', 10), (21, 'Face', 20), (22, 'Foundation', 21), 
(23, 'Lipstick', 20), (24, 'Eyes', 20), (25, 'Mascara', 24), (26, 'Eyeshadow', 24),
(30, 'Fashion', NULL), (31, 'Women', 30), (32, 'Shoes', 31), (33, 'Heels', 32),
(34, 'Sneakers', 32), (35, 'Clothing', 31), (36, 'Dresses', 35),
(40, 'Men', 30), (41, 'Men Shoes', 40), (42, 'Men Sneakers', 41),
(43, 'Men Clothing', 40), (44, 'Shirts', 43), (45, 'Pants', 43);

-- ONLARCA ÜRÜN
INSERT INTO Products VALUES 
(100, 12, 'GlowEssence Hydro-Gel', 45.90, 'Intense hydration with 2% Hyaluronic Acid'),
(101, 12, 'AquaDeep Night Cream', 55.00, 'Overnight repair with Ceramides'),
(102, 12, 'SunShield Pro SPF50', 38.00, 'Zinc-based non-greasy sun protection'),
(103, 12, 'VelvetTouch Lotion', 29.90, 'Daily moisturizer with Shea Butter'),
(104, 13, 'PureCleanse Foaming Wash', 22.50, 'Gentle daily cleanser for sensitive skin'),
(105, 13, 'Charcoal Detox Gel', 25.00, 'Deep pore cleansing gel'),
(106, 14, 'Vitamin C Boost Serum', 60.00, 'Brightening serum with 15% Vit C'),
(107, 14, 'Retinol Renew Night Serum', 75.00, 'Anti-aging nighttime serum'),
(108, 15, 'Clay Purify Mask', 30.00, 'Mineral-rich clay face mask'),
(109, 15, 'Honey Glow Sheet Mask', 15.00, 'Hydrating single-use sheet mask'),
(200, 22, 'Velvet Silk Foundation', 65.00, 'Full coverage breathable formula'),
(201, 22, 'GlowDrop Skin Tint', 42.00, 'Dewy finish light coverage'),
(202, 22, 'MatteControl Base', 58.00, 'Oil-free 24h wear matte base'),
(203, 22, 'CoverPlus Concealer', 35.00, 'High-pigment creamy concealer'),
(204, 23, 'LushMatte Liquid', 25.00, 'Transfer-proof matte red'),
(205, 23, 'SatinShine Bullet', 22.00, 'Moisturizing nude satin finish'),
(206, 23, 'PlumpGloss Serum', 28.00, 'Peppermint lip plumper gloss'),
(207, 23, 'LipDefine Liner', 18.00, 'Waterproof precision liner'),
(208, 25, 'VolumeMax Mascara', 24.00, 'Dramatic volume and length'),
(209, 25, 'CurlLash Waterproof', 26.00, 'Smudge-proof curling mascara'),
(210, 26, 'NudePalette 12-Pan', 45.00, 'Everyday neutral eyeshadow palette'),
(211, 26, 'NeonPop Pigments', 35.00, 'Bright bold pressed pigments'),
(400, 33, 'Midnight Stiletto', 145.00, 'Handcrafted leather with crystals'),
(401, 33, 'OfficePro Pump', 95.00, 'Suede pointed-toe comfort pumps'),
(402, 33, 'Evening Gold Ankle', 110.00, 'Metallic gold buckle strap heels'),
(403, 33, 'Vintage Block Heel', 85.00, 'Retro velvet block heel shoes'),
(500, 34, 'NeoRun Runner', 120.00, 'High-rebound cushioning mesh sneakers'),
(501, 34, 'Urban Street White', 89.90, 'Minimalist vegan leather sneakers'),
(502, 34, 'Retro Sport High', 105.00, 'Classic basketball silhouette high-tops'),
(503, 34, 'TrailGuard Hiker', 135.00, 'Rugged waterproof outdoor sneakers'),
(600, 36, 'Summer Floral Maxi', 79.00, 'Linen blend hand-painted floral dress'),
(601, 36, 'Silk Gala Gown', 250.00, 'Pure silk floor-length evening gown'),
(602, 36, 'ModDaily Midi', 55.00, 'Rib-knit versatile midi work dress'),
(603, 36, 'Little Black Lace', 120.00, 'Structured fit lace detail dress'),
(700, 42, 'Mens Urban Walker', 95.00, 'Comfortable daily walking sneakers for men'),
(701, 42, 'Pro Court Basketball', 130.00, 'Professional court shoes'),
(800, 44, 'Oxford Classic White', 45.00, 'Cotton tailored white shirt'),
(801, 44, 'Linen Summer Button', 55.00, 'Breathable short sleeve linen shirt'),
(802, 45, 'Chino Stretch Khaki', 65.00, 'Slim fit stretch chino pants'),
(803, 45, 'Raw Denim Selvedge', 110.00, 'Premium raw unwashed denim jeans');

-- ÜRÜN VARYANTLARI (Farklı Boyut ve Renk Seçenekleri)
INSERT INTO Product_Variants VALUES 
(1001, 100, 'Clear', '50ml', 'Standard', 'GE-50ML-STD'),
(1002, 101, 'White', '50ml', 'Standard', 'AD-50ML-STD'),
(1003, 102, 'Clear', '100ml', 'Standard', 'SS-100ML-STD'),
(1004, 103, 'White', '250ml', 'Standard', 'VT-250ML-STD'),
(1005, 104, 'Clear', '150ml', 'Standard', 'PC-150ML-STD'),
(1006, 105, 'Black', '100ml', 'Standard', 'CD-100ML-STD'),
(1007, 106, 'Yellow', '30ml', 'Standard', 'VC-30ML-STD'),
(1008, 107, 'Clear', '30ml', 'Standard', 'RR-30ML-STD'),
(1009, 108, 'Green', '100g', 'Standard', 'CP-100G-STD'),
(1010, 109, 'White', '1 Piece', 'Standard', 'HG-1PC-STD'),
(2001, 200, 'Beige', '30ml', 'Sand 110', 'VF-SAND-110'),
(2002, 200, 'Tan', '30ml', 'Warm 120', 'VF-WARM-120'),
(2003, 201, 'Porcelain', '30ml', 'Cool 01', 'GD-PORC-01'),
(2004, 202, 'Mocha', '30ml', 'Deep 300', 'MC-DEEP-300'),
(2005, 203, 'Ivory', '15ml', 'Light 10', 'CP-LIGHT-10'),
(3001, 204, 'Red', '5ml', 'Ruby Red', 'LM-RED-01'),
(3002, 205, 'Pink', '4g', 'Dusty Rose', 'SS-ROSE-02'),
(3003, 206, 'Clear', '10ml', 'Glass Finish', 'PG-GLS-03'),
(3004, 207, 'Brown', '1.2g', 'Cocoa', 'LD-COCOA-04'),
(4001, 400, 'Black', '38', 'Satin', 'MS-38-BLK'),
(4002, 400, 'Black', '39', 'Satin', 'MS-39-BLK'),
(4003, 401, 'Navy', '37', 'Suede', 'OP-37-NVY'),
(4004, 402, 'Gold', '38', 'Metallic', 'EG-38-GLD'),
(5001, 500, 'White', '39', 'Mesh', 'NR-39-WHT'),
(5002, 500, 'Black', '40', 'Mesh', 'NR-40-BLK'),
(5003, 501, 'All White', '40', 'Vegan Leather', 'US-40-WHT'),
(5004, 501, 'All White', '41', 'Vegan Leather', 'US-41-WHT'),
(6001, 600, 'Blue Floral', 'M', 'Linen', 'SM-M-BLUE'),
(6002, 600, 'Blue Floral', 'L', 'Linen', 'SM-L-BLUE'),
(6003, 601, 'Emerald', 'S', 'Silk', 'SG-S-EMR'),
(6004, 602, 'Grey', 'M', 'Rib-Knit', 'MD-M-GRY'),
(6005, 603, 'Black', 'S', 'Lace', 'LB-S-BLK'),
(7001, 700, 'Grey', '42', 'Suede', 'MU-42-GRY'),
(7002, 701, 'Red/Black', '44', 'Leather', 'PC-44-RBLK'),
(8001, 800, 'White', 'L', 'Cotton', 'OC-L-WHT'),
(8002, 801, 'Beige', 'M', 'Linen', 'LS-M-BGE'),
(8003, 802, 'Khaki', '32x32', 'Stretch', 'CK-32-KHK'),
(8004, 803, 'Indigo', '34x32', 'Denim', 'RD-34-IND');

-- DEPO VE STOK YÖNETİMİ
INSERT INTO Inventory VALUES 
(1, 1001, 50, 'Warehouse A - B1'), (2, 1002, 40, 'Warehouse A - B2'),
(3, 1003, 60, 'Warehouse A - B3'), (4, 1004, 30, 'Warehouse A - B4'),
(5, 1005, 25, 'Warehouse B - C1'), (6, 1006, 45, 'Warehouse B - C2'),
(7, 1007, 15, 'Warehouse A - B5'), (8, 1008, 20, 'Warehouse A - B6'),
(9, 1009, 80, 'Warehouse C - D1'), (10, 1010, 200, 'Warehouse C - D2'),
(11, 2001, 30, 'Warehouse A - B7'), (12, 2002, 25, 'Warehouse A - B8'),
(13, 2003, 40, 'Warehouse A - B9'), (14, 2004, 10, 'Warehouse A - B10'),
(15, 2005, 55, 'Warehouse B - C3'), (16, 3001, 100, 'Warehouse B - C4'),
(17, 3002, 90, 'Warehouse B - C5'), (18, 3003, 120, 'Warehouse C - D3'),
(19, 3004, 75, 'Warehouse C - D4'), (20, 4001, 15, 'Warehouse D - E1'),
(21, 4002, 12, 'Warehouse D - E2'), (22, 4003, 8, 'Warehouse D - E3'),
(23, 4004, 20, 'Warehouse D - E4'), (24, 5001, 35, 'Warehouse D - E5'),
(25, 5002, 40, 'Warehouse D - E6'), (26, 5003, 50, 'Warehouse E - F1'),
(27, 5004, 45, 'Warehouse E - F2'), (28, 6001, 18, 'Warehouse E - F3'),
(29, 6002, 14, 'Warehouse E - F4'), (30, 6003, 5, 'Warehouse E - F5'),
(31, 6004, 25, 'Warehouse F - G1'), (32, 6005, 30, 'Warehouse F - G2'),
(33, 7001, 60, 'Warehouse G - H1'), (34, 7002, 22, 'Warehouse G - H2'),
(35, 8001, 85, 'Warehouse H - I1'), (36, 8002, 40, 'Warehouse H - I2'),
(37, 8003, 55, 'Warehouse H - I3'), (38, 8004, 30, 'Warehouse H - I4');

-- ONLARCA GERÇEKÇİ SİPARİŞ
INSERT INTO Orders VALUES 
(6001, 2, '2026-04-15 11:20:00', 45.90, 'Delivered'),
(6002, 3, '2026-04-20 14:45:10', 90.00, 'Shipped'),
(6003, 4, '2026-05-01 09:30:00', 120.00, 'Processing'),
(6004, 5, '2026-05-04 15:00:00', 42.00, 'Pending'),
(6005, 6, '2026-05-05 10:15:00', 145.00, 'Delivered'),
(6006, 7, '2026-05-06 14:20:00', 38.00, 'Shipped'),
(6007, 8, '2026-05-07 16:45:00', 250.00, 'Delivered'),
(6008, 9, '2026-05-08 09:10:00', 89.90, 'Processing'),
(6009, 10, '2026-05-09 11:30:00', 22.50, 'Pending'),
(6010, 11, '2026-05-10 13:40:00', 130.00, 'Shipped'),
(6011, 12, '2026-05-11 15:55:00', 65.00, 'Delivered'),
(6012, 13, '2026-05-12 18:05:00', 110.00, 'Processing'),
(6013, 14, '2026-05-13 20:20:00', 79.00, 'Shipped'),
(6014, 15, '2026-05-14 08:45:00', 60.00, 'Delivered'),
(6015, 16, '2026-05-15 12:15:00', 25.00, 'Pending'),
(6016, 17, '2026-05-16 14:35:00', 120.00, 'Processing'),
(6017, 18, '2026-05-17 16:50:00', 45.00, 'Delivered'),
(6018, 19, '2026-05-18 19:10:00', 18.00, 'Shipped'),
(6019, 20, '2026-05-19 10:25:00', 55.00, 'Delivered'),
(6020, 21, '2026-05-20 11:40:00', 95.00, 'Processing');

-- SİPARİŞ DETAYLARI (Sepet İçerikleri)
INSERT INTO Order_Details VALUES 
(7001, 6001, 1001, 1, 45.90),
(7002, 6002, 2001, 1, 65.00),
(7003, 6002, 3001, 1, 25.00),
(7004, 6003, 5001, 1, 120.00),
(7005, 6004, 2003, 1, 42.00),
(7006, 6005, 4001, 1, 145.00),
(7007, 6006, 1003, 1, 38.00),
(7008, 6007, 6003, 1, 250.00),
(7009, 6008, 5003, 1, 89.90),
(7010, 6009, 1005, 1, 22.50),
(7011, 6010, 7002, 1, 130.00),
(7012, 6011, 8003, 1, 65.00),
(7013, 6012, 4004, 1, 110.00),
(7014, 6013, 6001, 1, 79.00),
(7015, 6014, 1007, 1, 60.00),
(7016, 6015, 1006, 1, 25.00),
(7017, 6016, 6005, 1, 120.00),
(7018, 6017, 8001, 1, 45.00),
(7019, 6018, 3004, 1, 18.00),
(7020, 6019, 1002, 1, 55.00),
(7021, 6020, 4003, 1, 95.00);

-- ÖDEME İŞLEMLERİ (Transaction Kayıtları)
INSERT INTO Payments VALUES 
(8001, 6001, 'PayPal', 'TXN-001', 'Success'),
(8002, 6002, 'Credit Card', 'TXN-002', 'Success'),
(8003, 6003, 'Credit Card', 'TXN-003', 'Success'),
(8004, 6004, 'Bank Transfer', 'TXN-004', 'Success'),
(8005, 6005, 'Credit Card', 'TXN-005', 'Success'),
(8006, 6006, 'Credit Card', 'TXN-006', 'Success'),
(8007, 6007, 'Wire Transfer', 'TXN-007', 'Success'),
(8008, 6008, 'PayPal', 'TXN-008', 'Pending'),
(8009, 6009, 'Credit Card', 'TXN-009', 'Success'),
(8010, 6010, 'Bank Transfer', 'TXN-010', 'Success'),
(8011, 6011, 'Credit Card', 'TXN-011', 'Success'),
(8012, 6012, 'PayPal', 'TXN-012', 'Success'),
(8013, 6013, 'Credit Card', 'TXN-013', 'Success'),
(8014, 6014, 'Credit Card', 'TXN-014', 'Success'),
(8015, 6015, 'Bank Transfer', 'TXN-015', 'Pending'),
(8016, 6016, 'Credit Card', 'TXN-016', 'Success'),
(8017, 6017, 'PayPal', 'TXN-017', 'Success'),
(8018, 6018, 'Credit Card', 'TXN-018', 'Success'),
(8019, 6019, 'Wire Transfer', 'TXN-019', 'Success'),
(8020, 6020, 'Credit Card', 'TXN-020', 'Success');

-- ==========================================================
-- 4. TEKNİK ARAÇLAR (Senin Yazdığın Trigger ve View)
-- ==========================================================

-- Gelişmiş Stok Kontrolü (Trigger)
DELIMITER //
CREATE TRIGGER BeforeOrderInsert
BEFORE INSERT ON Order_Details
FOR EACH ROW
BEGIN
    DECLARE current_stock INT;
    SELECT stockQuantity INTO current_stock FROM Inventory WHERE variantID = NEW.variantID;
    
    IF current_stock < NEW.quantity THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Insufficient stock!';
    ELSE
        UPDATE Inventory SET stockQuantity = stockQuantity - NEW.quantity WHERE variantID = NEW.variantID;
    END IF;
END //
DELIMITER ;

-- Yönetici Raporu (View)
CREATE VIEW AdminOrderReport AS
SELECT o.orderID, o.orderDate, u.fullName AS Customer, u.address AS City, p.brandName AS Product, pv.size, pv.shade, od.quantity, o.status
FROM Orders o
JOIN Users u ON o.userID = u.userID
JOIN Order_Details od ON o.orderID = od.orderID
JOIN Product_Variants pv ON od.variantID = pv.variantID
JOIN Products p ON pv.productID = p.productID;

-- ==========================================================
-- 5. KONTROL SORGULARI 
-- ==========================================================
SELECT * FROM Users;           -- 30 Kullanıcı
SELECT * FROM Categories;      -- Detaylı Kozmetik ve Moda Hiyerarşisi
SELECT * FROM Products;        -- Onlarca Kapsamlı Ürün
SELECT * FROM Product_Variants;-- Boyut, ml, Renk ve SKU Kodları
SELECT * FROM Inventory;       -- Farklı Depolarda Stok Takibi
SELECT * FROM Orders;          -- Farklı Zamanlarda Oluşturulmuş Siparişler
SELECT * FROM AdminOrderReport;-- Tam Kapsamlı Sipariş Raporu