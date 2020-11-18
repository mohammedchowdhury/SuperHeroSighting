drop database if exists SuperHeroSighting;
create database SuperHeroSighting;
use SuperHeroSighting;

-- CREATE TABLE LOCATION
CREATE TABLE `LOCATION`(
`LocationID` INT PRIMARY KEY AUTO_INCREMENT, 
`LocationName` VARCHAR(50) NOT NULL, 
`LocationDescription` VARCHAR(300) NOT NULL, 
`AddressInformation` VARCHAR(50) NOT NULL, 
`Latitude` VARCHAR(15) NOT NULL, 
`Longitude` VARCHAR(15) NOT NULL); 

-- CREATE TABLE ORGAINIZATION
CREATE TABLE `ORGANIZATION`(
`OrganizationID` INT PRIMARY KEY AUTO_INCREMENT, 
`OrganizationName` VARCHAR(50) NOT NULL, 
`OrganizationDescription` VARCHAR(300) NOT NULL, 
`ContactInformationPhone` VARCHAR(15),
`LocationID` INT NOT NULL, 
FOREIGN KEY(`LocationID`) REFERENCES `LOCATION`(`LocationID`)); 

-- CREATE TABLE SUPERPOWER
CREATE TABLE `SUPERPOWER`(
`SuperPowerID` INT PRIMARY KEY AUTO_INCREMENT, 
`SuperPowerName` VARCHAR(50)); 

-- CREATE TABLE HERO
CREATE TABLE `HERO`(
`HeroID` INT PRIMARY KEY AUTO_INCREMENT, 
`HeroName` VARCHAR(50) NOT NULL, 
`HeroDescription` VARCHAR(300) NOT NULL, 
`SuperPowerID` INT NOT NULL, 
FOREIGN KEY(`SuperPowerID`) REFERENCES `SUPERPOWER`(`SuperPowerID`)); 

-- CREATE HERO_ORGANIZATION TABLE 
CREATE TABLE `HERO_ORGANIZATION`(
`HeroID` int not null,
`OrganizationID` int not null,
primary key(`HeroID`,`OrganizationID`),
foreign key (`HeroID`) references `HERO`(`HeroID`),
foreign key (`OrganizationID`) references `ORGANIZATION`(`OrganizationID`));

-- CREATE SIGHTING TABLE 
CREATE TABLE `SIGHTING`(
`SightingID` INT PRIMARY KEY AUTO_INCREMENT, 
`SightingDate` DATE NOT NULL, 
`SightingDescription` VARCHAR(300) NOT NULL, 
`HeroID` INT NOT NULL, 
FOREIGN KEY(`HeroID`) REFERENCES `HERO`(`HeroID`),
`LocationID` INT NOT NULL, 
FOREIGN KEY(`LocationID`) REFERENCES `LOCATION`(`LocationID`)); 
