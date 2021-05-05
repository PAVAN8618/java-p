import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.SwingContainer;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.io.*;


class Server extends JFrame{


    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

     private JLabel heading = new JLabel("Client Area");
        private JTextArea messageArea = new JTextArea();
        private JTextField messageInput = new JTextField();
        private Font font = new Font ("Roboto",Font.PLAIN,20);
        //private int font = new Font ("Roboto",Font.PLAIN,20);

    

       // private Font font = new Font ("Roboto",Font.PLAIN,20);




//const
    public Server(){

        try{

                server = new ServerSocket(7776);
                System.out.println("Server is ready to accept connection");
                System.out.println("waiting....");
                socket = server.accept();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
                    startReading();
                    starWriting();
                    createGUI();
                    handleEvent();


        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    private void handleEvent() {

        messageInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

                //System.out.println("key released : " +e.getKeyCode());
                if(e.getKeyCode() == 10){
                    //System.out.println("You have pressed enter button");
                        String contentToSend = messageInput.getText();
                        messageArea.append("Server : " + contentToSend + "\n");

                        out.println(contentToSend);
                        out.flush();
                        messageInput.setText(" ");
                        messageInput.requestFocus();

                }
                
            }


        }); 


}


    private void createGUI(){

        this.setTitle( "Server Message[End]");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);




        heading.setFont(font);
        messageArea.setFont(font);
        messageArea.setFont(font);
        heading.setIcon(new ImageIcon("log.jpg"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(heading,java.awt.BorderLayout.NORTH);
        this.add(messageArea,java.awt.BorderLayout.CENTER);
        this.add(messageInput,java.awt.BorderLayout.SOUTH);
        this.setVisible(true);


}


            public void startReading(){
                Runnable r1 = () -> {
                    System.out.println("reader started...");
                    try{
                while(true)
                {
                
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                    //System.out.println("Client terminated the chat...");
                    System.out.println("Server terminated the chat...");
                    JOptionPane.showMessageDialog(this, "Client Terminated the chat");
                    messageInput.setEnabled(false);
                    break;
                    
                    }
                System.out.println("Client : "+ msg +"\n");
                }}
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
                
                };

                new Thread(r1).start();

            }
            public void starWriting(){

                Runnable r2 = () ->
                {
                    try{
                    while(true){
                       
                            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                            String content = br1.readLine();
                            out.println(content);
                            out.flush();

                        }}
                     
                    
                    catch(Exception e){
                        e.printStackTrace();
                    }
                };
                new Thread(r2).start();

            }




    public static void main(String args[]){
            System.out.println("this is server ..going to start server");
new Server();

}}