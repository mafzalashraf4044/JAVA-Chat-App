package myserver;

    import java.net.*;
    import java.io.*;
    import java.util.Random;

import java.util.Scanner;
public class Main {


    static DataInputStream input;
    static DataOutputStream output;
    static ServerSocket server;
    static Socket connection;
    static int noOfClients;
    static String textIn;
    static String textOut;

 

    public static void main(String[] args)throws IOException{

        String[] userName = new String[2];
        String[] password = new String[2];


        userName[0] = "u1";
        password[0] = "123";

        userName[1] = "u2";
        password[1] = "123";

        String checkUserName;
        String checkPassword;
        String fileName = "No File";

//          File file = new File("C:/ChatHistory1.txt");
//          FileWriter fileWriter = new FileWriter(file);

        


 
        System.out.println("________________________________________________________________\n");
        System.out.println("\t\t\tCHAT APPLICATION SERVER");
        System.out.println("________________________________________________________________");

  
      
          do{
                setServer();

          try{

            System.out.println(InetAddress.getLocalHost() + " is Waiting for client on port " + server.getLocalPort() + "...");

            connection = server.accept(); //now a connection has been established b\w client & server
            String connected = "Just connected to " + connection.getRemoteSocketAddress();
            System.out.println(connected); //this method returns the socket address of the client

                  input = new DataInputStream(connection.getInputStream());
                  output = new DataOutputStream(connection.getOutputStream());
                  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      Random randomNo = new Random();
      int i = randomNo.nextInt(100000);

      fileName = "MyFile" + Integer.toString(i) + ".txt";

      FileOutputStream outputStream = new FileOutputStream(fileName);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
      BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
      
      bufferedWriter.write(connected);
      bufferedWriter.newLine();


             checkUserName = input.readUTF();
             checkPassword = input.readUTF();

//             System.out.println("user" + checkUserName + " password " + checkPassword );

             boolean b = (checkUserName.equals(userName[0]) || checkUserName.equals(userName[1])) && checkPassword.equals(password[0]);


            //client & server send\receive data through IO Streams

            if(b){




            output.writeUTF("true");

            while(true){

            textIn = "Client: ".concat(input.readUTF());
            System.out.print(textIn);


            bufferedWriter.write(textIn);
            bufferedWriter.newLine();

            
            if(textIn.equalsIgnoreCase("Client: end")){
                System.out.println("\n\n>>>>>>>>>>Connection ended by Client<<<<<<<<<");
                break;

            }

            
            System.out.print("\nServer: ");
            textOut = br.readLine();
            output.writeUTF(textOut);
//            textOut = textOut.concat("\n");


            bufferedWriter.write("Server: ".concat(textOut));
            bufferedWriter.newLine();

            output.flush();

            

            }

            }else{

                output.writeUTF("false");

                 System.out.println("Invalid User Name and/or Password");

            }

             
            bufferedWriter.close();

            output.close();
            server.close();
            connection.close();





        }catch(SocketTimeoutException s){

            System.out.println("Socket timed out!");

         }
              System.out.println("\n\nThe Current Chat History is saved at File: " + fileName);
       readFile();

          }while(true);

    }

    public static void setServer() throws IOException{


            try{
            InetAddress localAddress = InetAddress.getLocalHost();
             // getLocalHost is a class method of InetAddress class
            // getLocalHost will return the name and IP address of server

            InetSocketAddress serverSocketAddress =  new InetSocketAddress(localAddress, 4444); //instantiating an object of InetSocketAddress class, this class contains builtin methods

            server = new ServerSocket(); //we haven't used ServerSocket construtor instead we will use bind method
            //we have created a socket for the server

            noOfClients = 1;

            server.bind(serverSocketAddress, noOfClients);
            //we are binding the server to the socket through port no and host name\IP
            //now the socket of server is ready to be connected to a client & is waiting for a client request
       
            }catch( IOException e1 ){
                System.out.println("Connection Failed!!");
//                 throw new IOException("Connection failed!!");


            }



     }

    public static void readFile() throws IOException{


            String fileName;
            Scanner scan = new Scanner(System.in);

            System.out.print("\nEnter File Name with Extension to see Chat History: ");
            fileName = scan.next();
            System.out.println();

            System.out.println("________________________________________________________________\n");
            System.out.println("\t\tCHAT HISTORY OF File: " + fileName);
            System.out.println("________________________________________________________________");


            FileInputStream inputStream = new FileInputStream(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-16");
            int character;


             while ((character = reader.read()) != -1) {
                System.out.print((char) character);
            }
            reader.close();

    }

}
