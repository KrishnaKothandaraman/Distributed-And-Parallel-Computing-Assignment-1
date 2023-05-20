# Distributed-And-Parallel-Computing-HKU

## Run instructions

<b> ALL COMMANDS ARE RUN FROM THE src/ DIRECTORY OF THIS PROJECT. IT WILL NOT WORK IF IT IS RUN FROM ANYWHERE ELSE!! </b>

1. Import all the files into your IDE under the src/ directory of your project
2. The Project structure should be as follows 
```tree
/
└── src/
    ├── authfiles/
    │   ├── OnlineUser.txt
    │   └── UserInfo.txt
    ├── utils/
    │   └── AuthenticatorResult.java
    ├── AuthInterface.java
    ├── Client.java
    ├── Server.java
    ├── ServerInterface.java
    ├── TextFileAuthenticator.java
    └── security.policy
```


          
3. Edit the security.policy file paths to the UserInfo, OnlineUser and temp.txt files in the same structure as given in the file. Just replace the path prefix before <src/file-name> with the path to that file on your machine

4. Start the RMI registry in the src directory using the command `rmiregistry &`. 
Note: Before running the rmiregistry, you may need to add the `path/to/src` in the classpath and then compile ServerInterface.java with the following command `javac ServerInterface.java`. 

5. Run the Server.java file with the ipaddr of the current machine as an argument. Note: On mac, the command to get the current ip address is ipconfig getifaddr en0, you may find the equivalent of that command on your operating system.
  `javac Server.java && java Server.java <ip-addr>`
  
6. Run the client file with the following commands `javac Client.java && java Client rm Server.class && java Client 10.70.116.245`

Now the GUI should be working!! Enjoy :)


