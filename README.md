# Final Project - Software Engineering 2019/2020


## Introduction
The Final Project of Software Engineering course (Politecnico di Milano, 2019/2020)
consists in the implementation of **Santorini**, a strategy board game.  
The distributed system includes a server and multiple clients which can join one match at a time.

* Game description  
  Play as a Greek God or Goddess!  
  You should compete to best aid the island's citizens in building a beautiful village in the middle of the Aegean Sea.  
  The game is designed by Gord Hamilton and published by Roxley Games.  
  More information about Santorini: [Click here](https://roxley.com/products/santorini)

* Game rules  
  Players take turn in order of their join sequence.  
  The randomly selected **Challenger** chooses God Power cards equal to the number of players. Everyone picks a **God Power** from the chosen ones, and the Challenger receives the last card. The Challenger then chooses a Start Player.  
  Every player has got two **Workers** which should be placed on unoccupied spaces (not containing Worker or Dome) on the board.  
  On your turn, select one of your Workers. You **must** always perform a **move** then **build** on your turn.  
  You **lose** if you cannot complete your turn. 
  


## Group GC44
- Feng Yang ([@fengyang98](https://github.com/fengyang98))
- Zheng Maria Yu ([@Trixyz28](https://github.com/Trixyz28))
- Linda Zhu ([@lnd24](https://github.com/lnd24))


## Features
| Requirements | State |
|:-----------------------|:------------------------------------:|
| Complete Rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple matches  | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Advanced Gods | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

* Complete Rules  
  All Simple Gods implemented (except Hermes).  
  n° players supported in a match: 2-3.
 
* Multiple matches  
  The server can handle more matches in the same time.
  
* Advanced Gods  
  5 Advanced Gods implemented: Hera, Hestia, Limus, Poseidon, Zeus.


## System Requirements
This application requires Java 14 to run.  
To visualize the client CLI correctly, the chosen terminal should support UTF-8 characters.  
Recommended screen resolution: 1920x1080 or higher.


## How to create executable files
Server.jar and Client.jar can be created with Maven Shade Plugin, through the command
```sh
mvn clean package
```

## How to run the application
  
### Server
```sh
java -jar Server.jar [port]
```
If there is no port number specified, the server will automatically use the default one (45000).

### Client
The Client should know the IP/Port of the Server to connect.
Double-click on Client.jar will launch the GUI.
As an alternative, it is possible running the client through
```sh
java -jar Client.jar [parameter]
```
If the parameter is null, the GUI will be launched. Otherwise, it will active the CLI.


## Tested environments
* Windows: Win10 (Windows Terminal, WSL)
* Linux: VM Ubuntu 20.04
* MacOS: VM Mojave 10.14


## Test Coverage
<p align="center">
<img src="Deliverables/Coverage/Coverage.PNG">
</p>


## Tools
* JDK 14
* Maven
* JavaFX
* JUnit
* IntelliJ IDEA
* SceneBuilder





