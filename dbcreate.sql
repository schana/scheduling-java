CREATE TABLE Squads(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT
);
CREATE TABLE Crews(
    ID INTEGER PRIMARY KEY,
    SquadID INTEGER
);
CREATE TABLE Members(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT,
    JobID INTEGER,
    CrewID INTEGER
);
CREATE TABLE Jobs(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Description TEXT,
    PositionID INTEGER,
    AlertCount INTEGER,
    BackupCount INTEGER,
    EventCount INTEGER
);
CREATE TABLE Positions(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Description TEXT
);
CREATE TABLE Leave(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    StartDayID INTEGER,
    EndDayID INTEGER
);
CREATE TABLE Certifications(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Description TEXT
);
CREATE TABLE Events(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    EventTypeID INTEGER,
    DayID INTEGER,
    SiteID INTEGER,
    DeputyID INTEGER,
    CommanderID INTEGER
);
CREATE TABLE EventTypes(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Description TEXT
);
CREATE TABLE Sites(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT,
    SiteTypeID INTEGER,
    SquadID INTEGER
);
CREATE TABLE SiteTypes(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Description TEXT
);
CREATE TABLE Days(
    ID INTEGER PRIMARY KEY,
    Ordinal INTEGER,
    MonthID INTEGER,
    WeekdayID INTEGER
);
CREATE TABLE Weekdays(
    ID INTEGER PRIMARY KEY,
    Name Text,
    Energy INTEGER
);
CREATE TABLE Months(
    ID INTEGER PRIMARY KEY,
    Ordinal INTEGER,
    YearID INTEGER
);
CREATE TABLE Years(
    ID INTEGER PRIMARY KEY,
    Ordinal INTEGER
);

CREATE TABLE Certifications_Members(
    CertificationID INTEGER,
    MemberID INTEGER
);
CREATE TABLE Certifications_SiteTypes(
    CertificationID INTEGER,
    SiteTypeID INTEGER
);
CREATE TABLE Leave_Members(
    LeaveID INTEGER,
    MemberID INTEGER
);

CREATE TABLE Configuration(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    AlertLengthDays INTEGER,
    CrewCountPerAlert INTEGER,
    RestrictSitesToSquad INTEGER
);

INSERT INTO Positions (ID,Description) VALUES (1,'Deputy');
INSERT INTO Positions (ID,Description) VALUES (2,'Commander');
INSERT INTO Jobs (ID,Description,AlertCount,BackupCount,EventCount,PositionID) VALUES (1,'DMCCC',9,1,9,1);
INSERT INTO Jobs (ID,Description,AlertCount,BackupCount,EventCount,PositionID) VALUES (2,'MCCC',9,1,9,2);
INSERT INTO Jobs (ID,Description,AlertCount,BackupCount,EventCount,PositionID) VALUES (3,'AssistantFlightCommander',9,1,9,2);
INSERT INTO Jobs (ID,Description,AlertCount,BackupCount,EventCount,PositionID) VALUES (4,'FlightCommander',2,0,2,2);
INSERT INTO Squads (ID,Name) VALUES (1,'321');
INSERT INTO Certifications (ID,Description) VALUES (1,'SCP');
INSERT INTO SiteTypes (ID,Description) VALUES (1,'PLIC');
INSERT INTO SiteTypes (ID,Description) VALUES (2,'SCP');
INSERT INTO Sites (Name,SiteTypeID,SquadID) VALUES ('K',1,1);
INSERT INTO Sites (Name,SiteTypeID,SquadID) VALUES ('L',1,1);
INSERT INTO Sites (Name,SiteTypeID,SquadID) VALUES ('M',1,1);
INSERT INTO Sites (Name,SiteTypeID,SquadID) VALUES ('N',1,1);
INSERT INTO Sites (Name,SiteTypeID,SquadID) VALUES ('O',2,1);
INSERT INTO Certifications_SiteTypes (CertificationID, SiteTypeID) VALUES (1,2);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (1,'Sunday',2);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (2,'Monday',1);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (3,'Tuesday',1);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (4,'Wednesday',1);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (5,'Thursday',1);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (6,'Friday',2);
INSERT INTO Weekdays (ID,Name,Energy) VALUES (7,'Saturday',3);
INSERT INTO EventTypes (Description) VALUES ('Alert');
INSERT INTO EventTypes (Description) VALUES ('Backup');

INSERT INTO Crews (ID,SquadID) VALUES(101,1);

INSERT INTO Members(CrewID,Name,JobID) VALUES (101,'Doe,John',2);
INSERT INTO Members(CrewID,Name,JobID) VALUES (101,'Doe,Jane',1);

--INSERT INTO Certifications_Members (CertificationID,MemberID) VALUES (1,#);







