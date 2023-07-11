create database PRJ302_TranMinhDuc_HE1730333
go

use PRJ302_TranMinhDuc_HE1730333
GO

create table users(
	username nvarchar(50),
	password varchar(30) not null,
	email varchar(80) unique not null,
	isTeacher bit,
	primary key (username)
)
go

create table questions(
	id int identity(1,1),
	questionText nvarchar(200),
	topic nvarchar(20),
	author nvarchar(50),
	primary key (id),
	constraint author foreign key (author) references users(username),
)
go

alter table questions
alter column questionText nvarchar(400) not null
alter table questions
add constraint qText UNIQUE(questionText)

create table answers(
	qid int,
	choice varchar(1),
	isKey bit,
	answerText nvarchar(200),
	constraint questionID foreign key (qid) references questions(id),
	primary key(qid, choice)
)
go

create table attempDetails(
	username nvarchar(50) not null,
	qid int not null,
	isCorrect bit, 
	timeFinished datetime,
	primary key (username, qid, timeFinished)
)

create table attemps(
	username nvarchar(50) not null,
	timeFinished datetime,
	score int,
	constraint username foreign key (username) references users(username),
	primary key(username, timeFinished)
)

alter table attempDetails
add choice varchar(1)
alter table attempDetails
add constraint FK_userTime foreign key (username, timeFinished) references attemps(username, timeFinished)
alter table attempDetails
add constraint FK_qid_choice foreign key (qid, choice) references answers(qid, choice)

insert into dbo.users(username, password, email, isTeacher)
values(N'teacher1', '123', 'test@gmail.com', 1)

insert into dbo.questions(questionText, topic, author)
values(N'Which type of driver can interpret JDBC calls to the database-specific native call interface', 'database', 'teacher1'),
(N'_____ sends a request to an object and includes the result in a JSP file.', 'jsp', 'teacher1'),
(N'A JSP_____ lets you define methods or fields that get inserted into the main body of the servlet class (outside of the JspService method that is called by service to process the request)', 'jsp', 'teacher1')

insert into dbo.answers(qid, choice, isKey, answerText)
values(1, 'A', 0, N'Type-1'),
(1, 'B', 1, N'Type-2'),
(1, 'C', 0, N'Type-4'),
(1, 'D', 0, N'Type-3'),
(2, 'A', 0, N'import directive'),
(2, 'B', 1, N'<jsp:include>'),
(2, 'C', 0, N'include directive'),
(2, 'D', 0, N'<jsp:forward>'),
(3, 'A', 1, N'declaration'),
(3, 'B', 0, N'scriptlet'),
(3, 'C', 0, N'expression')


		
