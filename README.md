# My project

Hello it's my first serious project called CarManagementSystem1. Application allows us to operate with the collection of cars. Data about sample cars
are stored in JSON file.
 

## Description

To run the application we need a few steps. 

1. We need to open a terminal of the main pom.xml file for the whole project 
and use the command "mvn clean install".
2. Now we open the terminal of the menu module pom.xml file and use command "mvn clean compile assembly:single".
3. After that we need to open target terminal and use command "java --enable-preview -cp menu-1.0-SNAPSHOT-jar-with-dependencies.jar com.app.menu.App"
4. Program will ask us to give the path of JSON file path on ur computer to turn on the application.

## Stack

Maven , Java 12 , Junit 4.12, Lombok 1.18.8, Gson 2.8.5, Eclipse-collections 9.2.0 .