package clilent;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Main {


    static DataInputStream input;
    static DataOutputStream output;
    static ServerSocket server;
    static Socket client;
    static int noOfClients;
    static String textIn;
    static String textOut;


    public static void main(String[] args) throws IOException{

//        InetAddress localAddress = InetAddress.getLocalHost();
        int portNo;
//        char pressAnyKey;
        String ip = null;

        String userName;
        String password;


        Scanner scan = new Scanner(System.in);

        
        System.out.println("________________________________________________________________\n");
        System.out.println("\t\t\tCHAT APPLICATION CLIENT");
        System.out.println("________________________________________________________________");


       
         System.out.println("Press any key to get Connected to the Server . . . .!");
         System.in.read();
//         pressAnyKey = scan.next().charAt(0);

//         System.out.println();
          
         System.out.print("Enter the IP Address of Server: ");
         ip = scan.next();
         System.out.println();


           System.out.print("Connecting to " + ip + ", please enter Port Numeber: ");

           portNo = scan.nextInt();

             System.out.println();

           try{
           client = new Socket();
           client.connect(new InetSocketAddress(ip,portNo),1000);

           }catch(SocketTimeoutException t){
               System.out.println("Connection Timed Out, Unable to connect to the Server at " + ip);
           }
     

//                 client = new Socket(ip, portNo);


             input = new DataInputStream(client.getInputStream());
             output = new DataOutputStream(client.getOutputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        
             System.out.print("Enter your User Name: ");
             userName = scan.next();
             output.writeUTF(userName);


             System.out.println();

             System.out.print("Enter your Password: ");
             password = scan.next();

             output.writeUTF(password);

             System.out.println();


             String tf =  input.readUTF();
         

             if(tf.compareTo(tf)==0)
             {

             System.out.println("Access Granted, Just connected to " + client.getRemoteSocketAddress());


            //client & server send\receive data through IO Streams

            System.out.println("Start the chitchat, type your message: ");

            textIn = "Client";
            while(!textIn.equalsIgnoreCase("end")){

               

            System.out.print("\nClient: ");
            textOut = br.readLine();
            output.writeUTF(textOut);

             if(textOut.equalsIgnoreCase("end")){
                    System.out.println("\n\n>>>>>>>>>>>Chat Ended<<<<<<<<<<<");
                     break;

             }

            output.flush();



            textIn = input.readUTF();
            System.out.print("Server: " + textIn);



            }


            }
             else{

               System.out.println( "Access Denied by Server, Invalid User Name and/or Password");
            }
           


            input.close();
            output.close();
            client.close();

         
         
         
          
    }


}
