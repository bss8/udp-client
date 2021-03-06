# udp-client
UDP Client for sending messages and commands to a UDP Server

This client is intended for use with a UDP Server. 
Source code for server: https://github.com/bss8/udp-server 

Note: This project intentionally forgoes the convenience of using a build system like Maven 
or Gradle and instead opts for manual configuration. The intent is to practice and understand 
how Java is built and the convenience such systems offer. If building locally, you will 
need to add JUnit 5.4 to your classpath if you wish to run unit tests. Otherwise, remove those 
classes. 

###Class diagram
![UDP Client class diagram](src/main/resources/img/UDPClient.png)

## To Build
#####Windows: 
`cd scripts`    
`build.bat`

#####Linux: 
`cd scripts`    
`./build.sh`

## To Run
Prerequisite: build the application. 
#####Windows: 
`cd scripts`    
`run.bat`

#####Linux: 
`cd scripts`    
`./run.sh`