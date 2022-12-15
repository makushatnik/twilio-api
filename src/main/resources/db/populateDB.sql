INSERT INTO users (first_name, phone_number, email) VALUES
('John Doe', '+14567890123', 'johndoe@example.com'),
('Napoleon Hill', '+79185843179', 'nhill@example.com'),
('Christian Bale', '+16789012345', 'cbale@example.com'),
('Jennifer Lawrence', '+15678901234', 'lawrence@example.com');

INSERT INTO location(latitude, longitude, zip_code, city, state, country) VALUES
('40.714599','-74.002791','10031','New York','New York','USA'),
('42.354364','-71.065489','02116','Boston','Massachusetts','USA'),
('34.055855','-118.246130','90025','Los Angeles','California','USA'),
('38.898201','-77.032758','83856','Washington','Virginia','USA');

INSERT INTO post(user_id,title,description,location_id,category,condition,state,created_at,updated_at,renew_at) VALUES
(1,'Snowboard Nidecker Venus','The Venus is the perfect choice for advanced snowboarders who know exactly what they want from both riding and their board. Comfortable and stable sag of the Standart Camrock with a classic camber.',1,'HOME','NEW','ACTIVE','2021-03-30 01:02:06','2021-03-30 01:04:04','2021-03-30 01:02:06'),
(2,'Snowboard Burton Hideaway','The Burton Hideaway is a great choice for the beginner rider. Can master all mountain, give confidence and help you conquer your first slope.',2,'CLOTHING','USED','ACTIVE',now(),now(),now()),
(1,'Shoes','Snowboard shoes, size 43',3,'ELECTRONICS','BROKEN','ACTIVE','2021-04-01 17:29:03','2021-04-01 17:29:03','2021-04-01 17:29:03'),
(2,'Mask Alpina Big Horn','The Big Horn HM is one of the largest face masks in the Alpina range and offers a very wide view. And thanks to the high quality workmanship, it has an excellent value for money. The reflective..',4,'OFFICE','NEW','ACTIVE','2021-04-01 17:30:12','2021-04-01 17:30:12','2021-04-01 17:30:12'),
(1,'Gloves','Winter gloves',5,'TOY','USED','ACTIVE','2021-04-01 17:31:05','2021-04-01 17:31:05','2021-04-01 17:31:05'),
(2,'Baseball bat','The real American baseball bat',1,'HOME','BROKEN','ACTIVE',now(),now(),now()),
(1,'Idiot','The Idiot, Dostoyevskiy',2,'BOOK','NEW','ACTIVE','2021-04-01 17:32:26','2021-04-01 17:32:26','2021-04-01 17:32:26'),
(2,'Crime & Punishment','Crime & Punishment, Dostoyevskiy',3,'BOOK','USED','ACTIVE','2021-04-01 17:34:32','2021-04-01 17:34:32','2021-04-01 17:34:32'),
(1,'Master & Margarita','Master & Margarita, Bulgakov',4,'BOOK','BROKEN','ACTIVE','2021-04-01 17:37:17','2021-04-01 17:37:17','2021-04-01 17:37:17'),
(2,'Snowboard Shoes Burton Mint Storm Blue','Burton Mint is the world''s best selling women''s shoe. The high popularity is based on the very light and reliable design, which provides an incredible feeling of comfort and is easy to use.',5,'HOME','NEW','ACTIVE','2021-04-01 17:37:38','2021-04-01 17:37:38','2021-04-01 17:37:38'),
(1,'TV','Universal Display TV, 25 inch',1,'HOME','USED','ACTIVE','2021-04-01 17:36:48','2021-04-01 17:40:34','2021-04-01 17:36:48'),
(2,'Desert Strike','Desert Strike CD, desktop game',2,'TOY','USED','ACTIVE','2021-04-02 12:08:01','2021-04-03 01:50:15','2021-04-02 12:08:01'),
(1,'Deerslayer','The Deerslayer, James Fenimore Cooper',3,'BOOK','BROKEN','ACTIVE','2021-04-01 17:33:26','2021-04-03 01:57:16','2021-04-03 01:57:16'),
(2,'Snowboard Bindings Jones Mercury Black','JONES MERCURY - versatile bindings for true all-mountain riding. Ideal for backcountry freestyle and comfortable surf freeride. Designed for riders who are not limited by the same style. They are..',4,'CLOTHING','NEW','ACTIVE','2021-03-30 01:03:35','2021-04-03 01:59:02','2021-04-03 01:59:02'),
(1,'Vacuum cleaner','Vacuum cleaner iRobot, Roomba 896',5,'ELECTRONICS','USED','ACTIVE','2021-03-30 01:03:08','2021-04-03 02:02:17','2021-03-30 01:03:08'),
(2,'Snowboard Jones Frontier','Jeremy Jones created the Frontier snowboard for mountain romantics and adventure seekers. For those whose passion is snow-capped mountains. Swim in the fresh snow, slide down the sidelines, and..',1,'OFFICE','BROKEN','ACTIVE','2021-04-05 13:01:18','2021-04-05 13:01:18','2021-04-05 13:01:17'),
(1,'UV lamp','UV lamp for the virus protection',2,'HOME','NEW','ACTIVE','2021-04-05 13:01:23','2021-04-05 13:01:23','2021-04-05 13:01:23'),
(2,'Skyrim','The Skyrim CD, desktop game',3,'TOY','USED','ACTIVE','2021-04-05 13:01:26','2021-04-05 13:01:26','2021-04-05 13:01:26'),
(1,'Snowboard Nidecker Concept','The Nidecker Concept is a board that offers exciting opportunities for carving riders on the slope and backcountry. With a 7m sidecut radius, this snowboard combines a short radius with a stiff flex.',4,'ELECTRONICS','BROKEN','ACTIVE','2021-04-05 13:01:30','2021-04-05 13:01:30','2021-04-05 13:01:30'),
(2,'Linux Ubuntu','Linux Ubuntu CD, game for adults',5,'TOY','NEW','ACTIVE','2021-04-05 10:48:04','2021-04-05 10:48:04','2021-04-05 10:48:04'),
(1,'Chainsaw','Chainsaw Husquarna',1,'OFFICE','USED','ACTIVE',now(),now(),now());
