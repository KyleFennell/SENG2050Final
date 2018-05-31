USE c3137540_db

CREATE TABLE User(
	userID int AUTO_INCREMENT PRIMARY KEY,
	userName nvarchar(25) NOT NULL,
	password nvarchar(100) NOT NULL,
	firstName nvarchar(35) NOT NULL,
	surname nvarchar(35) NOT NULL,
	email nvarchar(255) NOT NULL UNIQUE,
	phoneNum nvarchar(12) NOT NULL,
	isStaff tinyint(1) NOT NULL
);

CREATE TABLE Issue(
	issueID int AUTO_INCREMENT PRIMARY KEY,
	title nvarchar(50) NOT NULL,
	description nvarchar(255) NOT NULL,
	resolutionDetails nvarchar(255),
	reportedDateTime DateTime NOT NULL,
	resolvedDateTime DateTime,
	UserID int NOT NULL,
	status nvarchar(50) NOT NULL,
	category nvarchar(25) NOT NULL,
	subCategory nvarchar(25) NOT NULL	
);

CREATE TABLE UserIssue( 	/* used for the many to many relationship between staff and issues (myissues) */
	userID int NOT NULL,
	issueID int NOT NULL,

	FOREIGN KEY(userID) REFERENCES User(userID) ON UPDATE CASCADE ON DELETE NO ACTION,
	FOREIGN KEY(issueID) REFERENCES Issue(issueID) ON UPDATE CASCADE ON DELETE NO ACTION,
);

CREATE TABLE Comment(
	commentID int AUTO_INCREMENT PRIMARY KEY,
	issueID int NOT NULL,
	UserID int NOT NULL,
	commentValue nvarchar(255) NOT NULL,

	FOREIGN KEY(issueID) REFERENCES Issue(issueID) ON UPDATE CASCADE ON DELETE NO ACTION,
	FOREIGN KEY(UserID) REFERENCES User(UserID) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Keyword(
	keywordID int AUTO_INCREMENT PRIMARY KEY,  /* i think this is irrelevant because you will always be searching by keywords or issues */
	keyword nvarchar(25) NOT NULL,
	issueID int NOT NULL,

	FOREIGN KEY(issueID) REFERENCES Issue(issueID) ON UPDATE CASCADE ON DELETE NO ACTION
);

INSERT INTO User (userName, password, firstName, surname, email, phoneNum, isStaff) VALUES 
('user1', 'password', 'Bob','Kennedy', 'bkennedy@gmail.com', '43658765', 0);

INSERT INTO User (userName, password, firstName, surname, email, phoneNum, isStaff) VALUES
('user2', 'password', 'Tiffany','South', 'tSouth@gmail.com', '43321144', 0);

INSERT INTO User (userName, password, firstName, surname, email, phoneNum, isStaff) VALUES
('user3', 'password', 'Paul','Stanton', 'pauly123@hotmail.com', '0439458314', 0);

INSERT INTO User (userName, password, firstName, surname, email, phoneNum, isStaff) VALUES
('staff1', 'password', 'Jenny','Summmers', 'jenny99@gmail.com', '43658798', 1);

INSERT INTO User (userName, password, firstName, surname, email, phoneNum, isStaff) VALUES
('staff2', 'password', 'George','Grundy', 'ggrundy@hotmail.com', '0437988742', 1);

INSERT INTO User (userName, password, firstName, surname, email, phoneNum, isStaff) VALUES
('staff3', 'password', 'Terry','Hatcher', 'thatcher@gmail.com', '0433397854', 1);


INSERT INTO Issue(title, description, reportedDateTime, UserID, status,category,subCategory) VALUES
('Network not connecting', 'My network connection won\'t work when trying to connect to the U: in the IT labs', NOW(), 1, 'New','Network','Connectivity');



