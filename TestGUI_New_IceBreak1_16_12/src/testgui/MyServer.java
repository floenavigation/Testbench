/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testgui;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Sangeeta
 */
public class MyServer {
    ClassA a1,a2,a3,a4 = null;
    ClassB b1,b2,b3,b4 = null;
    
    private static <T> T checkNotNull(T t, String msg) {
    if (t == null) throw new IllegalArgumentException(msg);
    return t;
    }
  
    
    void setClassA1(ClassA a){
        this.a1 = a;
    }
    void setClassA2(ClassA a){
        this.a2 = a;
    }
     void setClassA3(ClassA a){
        this.a3 = a;
    }
      void setClassA4(ClassA a){
        this.a4 = a;
    }
    void setClassB1(ClassB  b){
        this.b1 = b;
    }
    void setClassB2(ClassB b){
        this.b2 = b;
    }
    void setClassB3(ClassB b){
        this.b3 = b;
    }
    void setClassB4(ClassB b){
        this.b4 = b;
    }
    boolean isTaskCompleted = false;
    
    void Server(){    
    try{
        Socket s;
        ServerSocket ss=new ServerSocket(2000);
        s=ss.accept();
        try{
            TimerTask timerTask ; 
            Timer a= new Timer();
            timerTask = new TimerTask(){ 

                @Override
                public void run(){            
                    try {

                        //DataOutputStream out = new DataOutputStream(s.getOutputStream()); 
                        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
                        writer.flush();
                        //out.flush();              
                        if(a1 != null) { 
                            if (!"".equals(a1.getShow1())){
                                //out.writeChars(a1.ShowString() + "\r\n"); 
                                writer.println(a1.ShowString());
                            }                        
                        }
                        else if(b1 != null) {
                            if (!"".equals(b1.getShow1())){
                                //out.writeChars(b1.ShowString() + "\r\n");
                                 writer.println(b1.ShowString());
                            }
                        }
                        if(a2 != null) {
                            if (!"".equals(a2.getShow1())){
                                //out.writeChars(a2.ShowString() + "\r\n");  
                                 writer.println(a2.ShowString());
                            }
                        }
                        else if(b2 != null) {
                            if (!"".equals(b2.getShow1())){
                                //out.writeChars(b2.ShowString() + "\r\n");  
                                 writer.println(b2.ShowString());
                            }
                        }
                        if(a3 != null) { 
                            if (!"".equals(a3.getShow1())){
                                //out.writeChars(a3.ShowString() + "\r\n");  
                                 writer.println(a3.ShowString());
                            }                        
                        }
                        else if(b3 != null) {
                            if (!"".equals(b3.getShow1())){
                                //out.writeChars(b3.ShowString() + "\r\n");
                                 writer.println(b3.ShowString());
                            }
                        }
                        if(a4 != null) {
                            if (!"".equals(a4.getShow1())){
                                //out.writeChars(a4.ShowString() + "\r\n");  
                                 writer.println(a4.ShowString());                            
                            }
                        }
                        else if(b4 != null) {
                            if (!"".equals(b4.getShow1())){
                                //out.writeChars(b4.ShowString() + "\r\n");  
                                 writer.println(b4.ShowString());
                            }
                        }
                        writer.flush();
                        //out.flush(); 
                        //out.close();
                        //ss.close();
                        //s.close();
                        System.out.println("first done");
                        } catch (IOException ex) {                    
                            System.out.println("catch1"+ex);
                            ex.printStackTrace();                  
                        }                
                    }          
            };                
            a.scheduleAtFixedRate(timerTask,0,1000*60);
        }catch(Exception e){
            System.out.println("catch2" + e);
        }
    //s.close();
    }catch(Exception e){
        System.out.println(e);
        //s.close();
    }
    }
}
    