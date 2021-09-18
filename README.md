# ny
New Yahoo!

This is my New Yahoo! implementation, a project started in mid 2007 in order to reproduce and extends the online multiplayer game server from the old Yahoo! Games, but was discontinued shortly thereafter. There is only two games from Yahoo! Games implemented in this project, chequers and pool, but only checkers is functional.

A little demo can be seen at https://www.youtube.com/watch?v=qcP4hzTbonc.

Remembering this project was discontinued for over ten years, possible bugs may be present, then use at your own risk.

The main language used is Java and the editor used was Eclipse.

There is three folders containing this project as described bellow:

- lib: Contains the two external libraries used by this project, mail and mysql.
- newyahoo: The content related to client and server side classes. Client side files are contained inside the folder y and was primarily designed to be executed as Java applet.
- ny: The content related to server side pages/servlets.

This project depends on a Tomcat server and a MySQL server to works. The database configuration can be done using the two files database_creation.sql and mysql_user_creation.sql.

The support to Java applet plugins for browsers was discontinued many years ago, making impossible to run the client side of this project using a modern browser.
A possible translation of source files to HTML5/Javascript may be the better choice to make this project viable in the future, but i  have no plans to do that soon.
Anyway, these sources are here for study purposes, it is up to you whether you want to continue this project or not.
