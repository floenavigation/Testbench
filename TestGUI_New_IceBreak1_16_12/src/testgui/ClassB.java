/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testgui;
import java.io.DataOutputStream;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import static testgui.GUI.courseVal;
import static testgui.GUI.latVal;
//import static testgui.GUI.mmsiVal;
/**
 *
 * @author Sangeeta
 */
public class ClassB {
    private int msgInd = 18;
    private int repeatInd = 0;
    private long mmsi = 0;
    private int regreserved1=000000;
    private double speed = 0;
    private boolean position= true;
    private double lon = 0;
    private double lat = 0;
    private double course = 0;
    private int heading = 181;
    private int sec = 15;
    private int regreserved2=00;
    private boolean CSUnit = false;
    private boolean DisplayFlag = false;
    private boolean DSCFlag = false;
    private boolean BandFlag = false;
    private boolean MessageFlag = false;
    private boolean AssignedFlag = false;
    private boolean raim = false;
    private long radio = 149208;
    private double scaling_lat = 0.0;
    private double scaling_lon = 0.0;
    
   
    //variables for Binary string
    private static String msgInd_b;
    private static String repeatInd_b;
    private static String mmsi_b;
    private String regreserved1_b;
    private String position_b;
    private String speed_b;
    private String lon_b;
    private String lat_b;
    private String course_b;
    private String heading_b;
    private String sec_b;
    private String regreserved2_b;
    private String CSUnit_b;
    private String DisplayFlag_b;
    private String DSCFlag_b;
    private String BandFlag_b;
    private String MessageFlag_b;
    private String AssignedFlag_b;
    private String raim_b;
    private String radio_b;
    
    private  int msgInd_b_i;
    int aval;
    int rval;

    public void setMMSIB(long mmsiVal){
        this.mmsi = mmsiVal;
    }
    public void setLatB(double LatVal){
        this.lat = LatVal;
    }
    public void setLonB(double LonVal){
        this.lon = LonVal;
    }
    public void setSpeedB(double speedVal){
        this.speed = speedVal;
    }
    public void setCourseB(double courseVal){
        this.course = courseVal;
    }   
    public static int boolToInt(Boolean b)
    {
	return b ? 1 : 0;
    }
     public void setScaling_lat(double scaling_lat_New){
        this.scaling_lat = scaling_lat_New;
    }
    public void setScaling_lon(double scaling_lon_New){
        this.scaling_lon = scaling_lon_New;
    }  
 public void Timer(){
        AppendBin();
        setData();
        DecToAscii();
     try{
        
        TimerTask timerTask ; 
        Timer a= new Timer();
        timerTask = new TimerTask(){  
            @Override
            public void run(){
                Display1();
                calculateNewPosition();
                AppendBin();
                setData();
                DecToAscii();
                            }
        };
        a.scheduleAtFixedRate(timerTask,0,1000*2);
    }catch(Exception e){
    
        }
//     return ;
    }
    private  final String TAG = "AIVDM";
    private  String packetName = "!AIVDM";
    private  int fragCount = 1 ;
    private  int fragNum = 1;
    private  int seqMsgID;
    private  char channelCode;
    private  String payload;
    private  String eod = "0*5C";
     
    public void setShow(){
        Show.setLength(0);
    }
    
    public String getShow1(){
        return Show.toString();
    }
    public  int Display(){
        System.out.println(packetName + "," + fragCount + "," + fragNum + "," + " " + "," + channelCode + "," + AscAppend + "," + eod);
        return 0;
    }
    public String ShowString()
    {
        String Show1 = Display1().toString(); 
//        System.out.println("In Show" + Show.toString());
        setShow();
        return Show1;
    }
    
    public double getLat()
    {
        return this.lat;
    }
    public double getLon(){
        return this.lon;
    }
    public double getSpeed(){
        return this.speed;
    }
    public double getCourse(){
        return this.course;
    }
    public long getMMSI(){
        return this.mmsi;
    }
    public void ClassB(){
        Show.setLength(0);
    }
    public StringBuilder Display1(){
        DisplayVal.setLength(0);
        Show.setLength(0);
//        System.out.println("In display:" + AscAppend);
        Show = DisplayVal.append(packetName).append(",").append(fragCount).append(",").append(fragNum).append(",,").append(",").append(AscAppend).append(",").append(eod);        
        return Show;                  
    } 
        
    private StringBuilder binary = new StringBuilder();
    private StringBuilder DisplayVal = new StringBuilder();
    private StringBuilder Show = new StringBuilder();
    private StringBuilder AscAppend = new StringBuilder();
    
    public int ConvertLon(double va1)
    {
        Boolean neg;
        int lond1;
        if (va1<0.0) {
			va1 = -va1;
			neg=true;
                     }
        else 
        neg=false;
        lond1 = 0x00000000;
        Long L = Math.round(va1 * 600000.0);
        lond1 = L.intValue();

        if (neg==true) {
                lond1 = ~lond1;
                lond1 += 1;
                lond1 &= 0x0FFFFFFF;
        }
        return lond1;     
    }  
   public int ConvertLat(double va11)
    {
        Boolean neg;
        int lond;
        if (va11<0.0) {
			va11 = -va11;
			neg=true;
                     }
        else 
        neg=false;
        lond = 0x00000000;
        Long L = Math.round(va11 * 600000.0);
        lond = L.intValue();

        if (neg==true) {
                lond = ~lond;
                lond += 1;
                lond &= 0x07FFFFFF;
        }
        return lond;     
    } 
    public void  AppendBin()
    {
        binary.setLength(0);
        msgInd_b = String.format("%6s",Integer.toString(msgInd,2)).replace(' ','0');
        repeatInd_b = String.format("%2s",Integer.toString(repeatInd,2)).replace(' ','0');
        mmsi_b = String.format("%30s",Long.toString(mmsi,2)).replace(' ','0');       
        regreserved1_b = String.format("%8s",Integer.toString(regreserved1,2)).replace(' ','0');
        speed_b = String.format("%10s",Long.toBinaryString((Math.round(speed * 10)))).replace(' ','0');
        String speed_b_val = speed_b.substring(0, 10);
        position_b = String.format("%1s",Integer.toString(boolToInt(position),2)).replace(' ','0');
        lon = lon + scaling_lon;
        int lon_val = ConvertLon(lon);	
        String lon_b_val = String.format("%28s",Integer.toString(lon_val,2)).replace(' ','0'); 
        lat = lat + scaling_lat;
        int lat_val = ConvertLat(lat);	
        String lat_b_val = String.format("%27s",Integer.toString(lat_val,2)).replace(' ','0'); 
        course_b = String.format("%12s",Long.toBinaryString((Math.round(course * 10)))).replace(' ','0'); 
        String course_b_val = course_b.substring(0, 12);
        heading_b = String.format("%9s",Integer.toString(heading,2)).replace(' ','0');        
        sec_b = String.format("%6s",Integer.toString(sec,2)).replace(' ','0');               
        regreserved2_b = String.format("%2s",Integer.toString(regreserved2,2)).replace(' ','0'); 
        CSUnit_b = String.format("%1s",Integer.toString(boolToInt(CSUnit),2)).replace(' ','0');
        DisplayFlag_b = String.format("%1s",Integer.toString(boolToInt(DisplayFlag),2)).replace(' ','0');
        DSCFlag_b = String.format("%1s",Integer.toString(boolToInt(DSCFlag),2)).replace(' ','0');
        BandFlag_b = String.format("%1s",Integer.toString(boolToInt(BandFlag),2)).replace(' ','0');
        MessageFlag_b = String.format("%1s",Integer.toString(boolToInt(MessageFlag),2)).replace(' ','0');
        AssignedFlag_b = String.format("%1s",Integer.toString(boolToInt(AssignedFlag),2)).replace(' ','0');    
        raim_b = String.format("%1s",Integer.toString(rval,2)).replace(' ','0');
        radio_b = String.format("%20s",Long.toString(radio,2)).replace(' ','0');
                
        binary.append(msgInd_b).append(repeatInd_b).append(mmsi_b).append(regreserved1_b).append(speed_b_val).append(position_b).append(lon_b_val).append(lat_b_val).append(course_b_val).append(heading_b).append(sec_b).append(regreserved2_b).append(CSUnit_b).append(DisplayFlag_b).append(DSCFlag_b).append(BandFlag_b).append(MessageFlag_b).append(AssignedFlag_b).append(raim_b).append(radio_b);
        
        
        
    }
    static <T> Object strbuildtodec(int begin, int end, int len, StringBuilder binary, Class<?> type)
    {
        try{
            char[] array = new char[len];
            binary.getChars(begin,(end + 1),array,0);

            long decimal = 0;
            for(int pow = len; pow > 0; pow--)
            {
                if(array[pow - 1] == '1')
                    decimal += Math.pow(2,len - pow);
            }
    //		System.out.println("dec: " + decimal);
            if(type == int.class)
                return (int)(long)decimal;
            else
                return decimal;
        }catch (IndexOutOfBoundsException e) {
            String text = String.valueOf(e.getStackTrace());
//            Log.d(TAG, text);
            if(type == int.class)
                return (int)(long)0;
            else
                return (long) 0;
            //return 0;
        }
        
    }
    int var[]=new int[30];
    
        public void setData()
                
    {
        int i;
        for( i=0; i<28;i++)
        {
            
            var[i]= (int)strbuildtodec(i*6,((i+1)*6)-1,6,binary,int.class);
            
            if (var[i]>= 40)
            {
                var[i] += 8;
            }
            var[i] += 48;
//             System.out.println("Integer values" + var[i] );  
        }
       
           
    }
       public void DecToAscii()
       {
           char ch[] = new char[30];
           AscAppend.setLength(0);
           for(int i=0; i<30;i++)
           {
               ch[i] = (char) var[i];
               
               AscAppend.append(ch[i]);
               
           }    
//           System.out.println(AscAppend);
            
       }
 
public void calculateNewPosition(){
//        System.out.println("class B lat"+lat);
//        System.out.println("class B lon"+lon);
        double latV = lat;
        double lonV = lon;
        double speedV = speed;
        double bearing = course;
        final double r = 6371 * 1000; // Earth Radius in m
        double distance = speedV * 10;
                        
        double lat2 = Math.asin(Math.sin(Math.toRadians(latV)) * Math.cos(distance / r)
                + Math.cos(Math.toRadians(latV)) * Math.sin(distance / r) * Math.cos(Math.toRadians(bearing)));
        double lon2 = Math.toRadians(lonV)
                + Math.atan2(Math.sin(Math.toRadians(bearing)) * Math.sin(distance / r) * Math.cos(Math.toRadians(latV)), Math.cos(distance / r)
                - Math.sin(Math.toRadians(latV)) * Math.sin(lat2));
        lat2 = Math.toDegrees( lat2);
        lon2 = Math.toDegrees(lon2);
        lat = lat2;
        lon = lon2;
//        System.out.println("changed val"+lat);
//        System.out.println(+lon);
        
       
    }
   Boolean neg;
        
}

    

