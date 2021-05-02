CREATE DATABASE ncms;

use ncms;

CREATE TABLE User (
    User_ID int NOT NULL ,
    First_name varchar(20),
    Last_name varchar(20),
    role varchar(10),
    password varchar(10),
    PRIMARY KEY (User_ID)
);

CREATE TABLE Patient (
    Patient_ID int NOT NULL ,
    User_ID int,
    Location_X int,
    Location_Y int,
    Queue_Number int,
    Severity_level varchar(7),
    Is_discharged boolean,
    PRIMARY KEY (Patient_ID),
	FOREIGN KEY (User_ID) REFERENCES User (User_ID)
);

CREATE TABLE Patient (
    Patient_ID int NOT NULL ,
    User_ID int,
    Location_X int,
    Location_Y int,
    Queue_Number int,
    Severity_level varchar(7),
    Is_discharged boolean,
    PRIMARY KEY (Patient_ID),
	FOREIGN KEY (User_ID) REFERENCES User (User_ID)
);

CREATE TABLE Hospital (
    Hospital_ID int NOT NULL ,
    Location_X int,
    Location_Y int,
    District varchar(10),
    PRIMARY KEY (Hospital_ID)
);

CREATE TABLE record_admits (
    Record_ID int NOT NULL ,
    Patient_ID int,
    Hospital_ID int,
    Date_Time datetime,
    Bed_Number int,
    PRIMARY KEY (Record_ID),
    FOREIGN KEY (Patient_ID) REFERENCES Patient (Patient_ID),
    FOREIGN KEY (Hospital_ID) REFERENCES Hospital (Hospital_ID)
);

CREATE TABLE Hospital_staff (
    Staff_ID int NOT NULL ,
    User_ID int,
    Hospital_ID int,
    PRIMARY KEY (Staff_ID),
    FOREIGN KEY (User_ID) REFERENCES User (User_ID),
    FOREIGN KEY (Hospital_ID) REFERENCES Hospital (Hospital_ID)
);

CREATE TABLE Director (
    Director_ID int NOT NULL ,
    Staff_ID int,
    PRIMARY KEY (Director_ID),
    FOREIGN KEY (Staff_ID) REFERENCES Hospital_staff (Staff_ID)
);

CREATE TABLE Doctor (
    Doctor_ID int NOT NULL ,
    User_ID int,
    Hospital_ID int,
    PRIMARY KEY (Doctor_ID),
    FOREIGN KEY (User_ID) REFERENCES User (User_ID),
    FOREIGN KEY (Hospital_ID) REFERENCES Hospital (Hospital_ID)    
);

CREATE TABLE Chief_Doctor (
    Chief_DOC_ID int NOT NULL ,
    Doctor_ID int,
    PRIMARY KEY (Chief_DOC_ID),
    FOREIGN KEY (Doctor_ID) REFERENCES Doctor (Doctor_ID)
);

CREATE TABLE MOH (
    MOH_ID int NOT NULL ,
    User_ID int,
    PRIMARY KEY (MOH_ID),
    FOREIGN KEY (User_ID) REFERENCES User (User_ID)
);

CREATE TABLE record_discharge (
    Record_ID int NOT NULL ,
    Patient_ID int,
    Director_ID int,
    Date_Time datetime,
    PRIMARY KEY (Record_ID),
    FOREIGN KEY (Patient_ID) REFERENCES Patient (Patient_ID),
    FOREIGN KEY (Director_ID) REFERENCES Director (Director_ID)
);

