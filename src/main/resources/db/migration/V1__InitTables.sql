/*
drop table item_ownership;
drop table champion_ownership;
drop table ability;
drop table champion;
drop table item;
drop table user;
drop table monster;
*/

create table User(
    id bigint auto_increment,
    constraint PK_User primary key (id),
    username nvarchar(255) unique,
    email varchar(255),
    password varchar(255),
    role varchar(50),
    funds int,
    picture varchar(255),
    level int,
    exp int
);

create table Champion(
    id bigint auto_increment,
    constraint PK_Champion primary key (id),
    name varchar(255) unique,
    hp int,
    base_damage int,
    price int,
    mana int,
    picture varchar(255),
    name_color varchar(255)
);

create table Monster(
    id bigint auto_increment,
    constraint PK_Monster primary key (id),
    name varchar(255) unique,
    level int,
    hp int,
    base_damage int,
    money_reward int,
    exp_reward int,
    picture varchar(255),
    name_color varchar(255)
);

create table Item(
    id bigint auto_increment,
    constraint PK_Item primary key (id),
    name varchar(255) unique,
    bonus_damage int,
    bonus_hp int,
    price int,
    type varchar(255),
    picture varchar(255),
    name_color varchar(255)
);

create table Ability(
    id bigint auto_increment,
    constraint PK_Ability primary key (id),
    name varchar(255) unique,
    type varchar(255),
    healing int,
    damage int,
    picture varchar(255),
    mana_cost int,
    ref_champion bigint,
    constraint FK_Ability_Owned foreign key (ref_champion) references Champion(id)
);

create table Champion_Ownership (
    id bigint auto_increment,
    constraint PK_Champion_Ownership primary key (id),
    level int,
    ref_champion bigint,
    ref_user bigint,
    constraint unique_champion_user unique(ref_champion,ref_user),
    constraint FK_Champion_Owned foreign key (ref_champion) references Champion(id),
    constraint FK_User_Champion_Owner foreign key (ref_user) references User(id)
);

create table Item_Ownership (
    id bigint auto_increment,
    constraint PK_Item_Ownership primary key (id),
    item_count int,
    ref_item bigint,
    ref_user bigint,
    constraint unique_item_user unique(ref_item,ref_user),
    constraint FK_Item_Owned foreign key (ref_item) references Item(id),
    constraint FK_User_Item_Owner foreign key (ref_user) references User(id)
);

-- CREATE INDEX idx_champName ON Champion(name);
-- CREATE INDEX idx_itemName ON Item(name);
-- CREATE INDEX idx_abilityName ON Ability(name);
--
INSERT INTO User (username, email, password, role, funds, level, exp, picture) VALUES ("Y33t", "yeet@ex.com" , "1234", "player", 1500, 10, 50, "img2.webp");
INSERT INTO User (username, email, password, role, funds, level, exp, picture) VALUES ("Shr3k", "shrek@ex.com" , "nimda", "admin", 3000, 50, 12, "img6.webp");
INSERT INTO User (username, email, password, role, funds, level, exp, picture) VALUES ("I0h4nnis", "yohaniis@ex.com" , "admin", "admin", 2000, 2, 0, "img3.webp");
INSERT INTO User (username, email, password, role, funds, level, exp, picture) VALUES ("N00b", "pro@ex.com" , "best", "player", 2500, 100, 99, "img4.webp");
--
-- INSERT INTO Item (name, bonus_power, bonus_hp, price, type) VALUES ("Tunica de 2 tone", 0 , 100, 100, "Armor");
-- INSERT INTO Item (name, bonus_power, bonus_hp, price, type) VALUES ("Toporceanu", 50 , 0, 200, "Weapon");
-- INSERT INTO Item (name, bonus_power, bonus_hp, price, type) VALUES ("Lapte caldut", 0 , 20, 50, "Potion");
-- INSERT INTO Item (name, bonus_power, bonus_hp, price, type) VALUES ("Papusa Chucky", 300, 0, 500, "Throwable");
--
-- INSERT INTO Champion (name, hp, power) VALUES ("Cargus Radioactivus", 200, 50);
-- INSERT INTO Champion (name, hp, power) VALUES ("Yamato Elementus Ixus", 100, 80);
-- INSERT INTO Champion (name, hp, power) VALUES ("Temploloid Armoid", 60, 150);
-- INSERT INTO Champion (name, hp, power) VALUES ("Amodo Bazuka", 1, 1000);
-- INSERT INTO Champion (name, hp, power) VALUES ("Tankinatus Nuclearus", 500, 100);
--
insert into monster (name, level, hp, base_damage, money_reward, exp_reward, picture, name_color) values ("Musca Tzetze", 3, 100, 40, 400, 30, "musca_tzetze.png", "#d501ff");
insert into monster (name, level, hp, base_damage, money_reward, exp_reward, picture, name_color) values ("Fanel Puternic", 10, 600, 200, 3000, 60, "fanel_puternic.png", "#f02f22");
