create schema if not exists whatcook collate latin1_swedish_ci;

create table if not exists category
(
	ID int auto_increment
		primary key,
	NAME varchar(100) null
);

create table if not exists ingredient
(
	ID int auto_increment
		primary key,
	CONTENT varchar(200) null,
	RECIPE_ID int null
);

create index ingredient_FK
	on ingredient (RECIPE_ID);

create table if not exists method
(
	ID int auto_increment
		primary key,
	STEP int null,
	CONTENT varchar(1000) null,
	RECIPE_ID int null
);

create index method_FK
	on method (RECIPE_ID);

create table if not exists nutrion
(
	ID int auto_increment
		primary key,
	RECIPE_ID int null,
	CALORIES float null,
	FAT float null,
	FIBER float null,
	PROTEIN float null,
	CARBS float null
);

create index nutrion_FK
	on nutrion (RECIPE_ID);

create table if not exists recipe
(
	ID int auto_increment
		primary key,
	NAME varchar(200) null,
	IMG varchar(225) null,
	CATEGORY_ID int null,
	PREP_TIME int null,
	COOK_TIME int null,
	YEILD varchar(200) null,
	nutrion_id int null,
	constraint CATEGORY_ID
		foreign key (CATEGORY_ID) references category (ID)
);

create index CATEGORY_ID_idx
	on recipe (CATEGORY_ID);

