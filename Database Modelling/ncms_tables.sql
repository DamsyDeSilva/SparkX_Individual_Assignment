create database ncms;

CREATE TABLE `hospital` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(45) NULL,
  `district` VARCHAR(45) NULL,
  `location_x` INT NULL,
  `location_y` INT NULL,
  `build_date` DATE NULL,
  `avail_beds` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `user` (
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NULL,
  `first_name` VARCHAR(50) NULL,
  `last_name` VARCHAR(50) NULL,
  `hospital_id` VARCHAR(50) NULL,
  `role` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`username`),
  FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`id`));

CREATE TABLE `doctor` (
  `id` VARCHAR(20) NOT NULL,
  FOREIGN KEY (`id`) REFERENCES `user` (`username`));

CREATE TABLE `patient` (
  `id` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(50) NULL,
  `last_name` VARCHAR(50) NULL,
  `district` VARCHAR(10) NULL,
  `location_x` INT NULL,
  `location_y` INT NULL,
  `severity_level` VARCHAR(50) NULL,
  `gender` VARCHAR(10) NULL,
  `contact` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `age` INT NULL,
  `admit_date` DATE NULL,
  `admitted_by` VARCHAR(45) NULL,
  `discharge_date` DATE NULL,
  `discharged_by` VARCHAR(45) NULL,
  `hospital_id` VARCHAR(50) NOT NULL,
  `bed_no` INT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`id`),
  FOREIGN KEY (`admitted_by`) REFERENCES `doctor` (`id`),
  FOREIGN KEY (`discharged_by`) REFERENCES `doctor` (`id`));

CREATE TABLE `patient_queue` (
  `id` INT NOT NULL,
  `patient_id` VARCHAR(50) NOT NULL,
  `status` INT DEFAULT 1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`));
