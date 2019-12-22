# MyPhotoAlbum
## Introduction
*My Photo Album* is a Web Application where you can upload your photos and organize them into different albums. You can also mark your photos as *Favourite* to reach them quickly. You can download back your photos whenever you want. Before using these services you have to create an account using your email and its pretty simple.

## How to setup application for use
### Required Softwares
* Java 1.8
* Apache Tomcat v9.0
* Eclipse Java EE IDE
* MySQL Server 8.0

*An Internet Connection is needed for downloading the maven dependencies.*

### Create Database in MySQL
```sql 
  CREATE DATABASE ThePhotoAlbum;
```

### Modify The Configurations Files
Open *ThePhotoAlbum/resources/configs/datasource.properties*
```properties
  jdbc.driverClassName=com.mysql.cj.jdbc.Driver
  jdbc.url=jdbc:mysql://localhost:3306/ThePhotoAlbum?useSSL=false
  jdbc.username=your_username
  jdbc.password=your_password
```

Open *ThePhotoAlbum/resources/configs/gamilAuthentication.properties*
```properties
  email=your_email
  password=your_password
```
** Google Account Security must be reduced before using it **

### We're Ready
Now Deploy The Project

### Technologies, Frameworks And Libraries Used in Project
* Servlet 4.0
* JSP 2.0
* jQuery 3.3.3
* Bootstrap 4.3
* Ajax
* Gson JSON Library
* Typescript
