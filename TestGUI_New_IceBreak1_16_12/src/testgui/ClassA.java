/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testgui;
import java.io.DataOutputStream;
import java.lang.*;
import static java.lang.Math.asin;
import static java.lang.Math.sqrt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import static testgui.ClassB.boolToInt;
import static testgui.GUI.courseVal;
import static testgui.GUI.latVal;
import static testgui.GUI.latVal2;
//import static testgui.GUI.mmsiVal;
import static testgui.GUI.speedVal2;
/**
 *
 * @author Sangeeta
 */
public class ClassA {
    private int msgInd = 1;
    private int repeatInd = 0;
    public long mmsi = 0;
    private int status = 5;
    private int turn = 128;
    public double speed = 0;
    private boolean accuracy = false;
    public double lon = 0;
    public double lat = 0;
    public double course = 0;
    private int heading = 511;
    private int sec = 50;
    private int maneuver = 0;
    private int spare = 0;
    private boolean raim = false;
    private long radio = 67427;
    final double r = 6371 * 1000; // Earth Radius in m  
    private double omega ;
    //variables for Binary string
    private String msgInd_b;
    private String repeatInd_b;
    public String mmsi_b;
    private String status_b;
    private String turn_b;
    public String speed_b;
    private String accuracy_b;
    public String lon_b;
    public String lat_b;
    public String course_b;
    private String heading_b;
    private String sec_b;
    private String maneuver_b;
    private String spare_b;
    private String raim_b;
    private String radio_b;
    private double scaling_lat = 0.0;
    private double scaling_lon = 0.0;
    private boolean originVal;
    private static int msgInd_b_i;
    private ClassA origin;
    public void ClassA(){
        Show.setLength(0);
    }
    public void setMMSI(long mmsiVal){
        this.mmsi = mmsiVal;
    }
    
    public void setLat(double LatVal){
        this.lat = LatVal;
    }
    public void setLon(double LonVal){
        this.lon = LonVal;
    }
    public void setSpeed(double speedVal){
        this.speed = speedVal;
    }
    public void setIsOrigin(boolean origin)
    {
        this.originVal = origin;
    }
    public boolean getOrigin()
    {
        return this.originVal;
    }
    public void setCourse(double courseVal){
        this.course = courseVal;
    }
    public void setOmega(double omegaVal)
    {
        this.omega = omegaVal;
    }
    public double lon1 = 0;
    public double lat1 = 0;
    
    public void setLat1(double LatVal){
        this.lat1 = LatVal;
    }
    public void setLon1(double LonVal){
        this.lon1 = LonVal;
    }
    public double getOmega()
    {
        return this.omega;
    }
     public double getLat()
    {
        return this.lat;
    }
    public double getLon(){
        return this.lon;
    }
    public double getLat1()
    {
        return this.lat1;
    }
    public double getLon1(){
        return this.lon1;
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
    public int boolToInt(Boolean b)
    {
	return b ? 1 : 0;
    }
    public void setScaling_lat(double scaling_lat_New){
        this.scaling_lat = scaling_lat_New;
    }
    public void setScaling_lon(double scaling_lon_New){
        this.scaling_lon = scaling_lon_New;
    }
    public void setOrigin(ClassA origin){
        this.origin = origin;
    }
    private  final String TAG = "AIVDM";
    private  String packetName = "!AIVDM";
    private  int fragCount = 1 ;
    private  int fragNum = 1;
    private  int seqMsgID;
    private  char channelCode = 'A';
    private  String payload;
    private  String eod = "0*5C";
    
    public String Show1 = "";
    
    public  int Display(){
           System.out.println(packetName + "," + fragCount + "," + fragNum + "," + " " + "," + channelCode + "," + AscAppend + "," + eod);
           return 0;
    }
    public String ShowString()
    {
        Show1 = Display1().toString(); 
        System.out.println("Packet is : " + Show1);
        setShow();
//        System.out.println("In Show" + Show.toString());
        return Show1;
    }
    
    public void setShow(){
        Show.setLength(0);
    }    
    public String getShow1(){
        return Show.toString();
    }
    public boolean flag = false;
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
        a.scheduleAtFixedRate(timerTask,0,1000*60);
    }catch(Exception e){
        e.printStackTrace();
        }
   
    }
    public StringBuilder Display1(){
        DisplayVal.setLength(0);
        Show.setLength(0);
//        System.out.println("In display:" + AscAppend);
        Show = DisplayVal.append(packetName).append(",").append(fragCount).append(",").append(fragNum).append(",,").append(channelCode).append(",").append(AscAppend).append(",").append(eod);        
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
        status_b = String.format("%4s",Integer.toString(status,2)).replace(' ','0');
        turn_b = String.format("%8s",Integer.toString(turn,2)).replace(' ','0');          
        speed_b = String.format("%10s",Long.toBinaryString((Math.round(speed * 10)))).replace(' ','0');  
        String speed_b_val = speed_b.substring(0, 10);        
        accuracy_b = String.format("%1s",Integer.toString(boolToInt(accuracy),2)).replace(' ','0');  
        lon = lon + scaling_lon;
        int lon_val = ConvertLon(lon);	
        String lon_b_val = String.format("%28s",Integer.toString(lon_val,2)).replace(' ','0');
        lat = lat + scaling_lat;
//        System.out.println("New Ice break Lat value" +lat);
        int lat_val = ConvertLat(lat);	
        String lat_b_val = String.format("%27s",Integer.toString(lat_val,2)).replace(' ','0');           
        course_b = String.format("%12s",Long.toBinaryString((Math.round(course * 10)))).replace(' ','0'); 
        String course_b_val = course_b.substring(0, 12);        
        heading_b = String.format("%9s",Integer.toString(heading,2)).replace(' ','0');        
        sec_b = String.format("%6s",Integer.toString(sec,2)).replace(' ','0');      
        maneuver_b = String.format("%2s",Integer.toString(maneuver,2)).replace(' ','0');
        spare_b = String.format("%3s",Integer.toString(spare,2)).replace(' ','0');
        raim_b = String.format("%1s",Integer.toString(boolToInt(raim),2)).replace(' ','0');
        radio_b = String.format("%19s",Long.toString(radio,2)).replace(' ','0');       
        binary.append(msgInd_b).append(repeatInd_b).append(mmsi_b).append(status_b).append(turn_b).append(speed_b_val).append(accuracy_b).append(lon_b_val).append(lat_b_val).append(course_b_val).append(heading_b).append(sec_b).append(maneuver_b).append(spare_b).append(raim_b).append(radio_b);
//        System.out.println(binary);
        
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
        for( i=0; i<28;i++){           
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
   
//  public void calculateNewPosition(){
////        System.out.println("Class A "+lat);
////        System.out.println(+lon);
//        double latV = lat;
//        double lonV = lon;
//        double latV1 = GUI.latVal2;
//        double lonV1 = GUI.lonVal2;
//        double speedV = speed;
//        double bearing = course;
//        final double r = 6371 * 1000; // Earth Radius in m
//        double distance = speedV * 10;
//                        
//        double lat2 = Math.asin(Math.sin(Math.toRadians(latV)) * Math.cos(distance / r)
//                + Math.cos(Math.toRadians(latV)) * Math.sin(distance / r) * Math.cos(Math.toRadians(bearing)));
//        double lon2 = Math.toRadians(lonV)
//                + Math.atan2(Math.sin(Math.toRadians(bearing)) * Math.sin(distance / r) * Math.cos(Math.toRadians(latV)), Math.cos(distance / r)
//                - Math.sin(Math.toRadians(latV)) * Math.sin(lat2));
//        lat2 = Math.toDegrees( lat2);
//        lon2 = Math.toDegrees(lon2);
//        
//        double latV2 = Math.asin(Math.sin(Math.toRadians(latV1)) * Math.cos(distance / r)
//                + Math.cos(Math.toRadians(latV1)) * Math.sin(distance / r) * Math.cos(Math.toRadians(bearing)));
//        double lonV2 = Math.toRadians(lonV1)
//                + Math.atan2(Math.sin(Math.toRadians(bearing)) * Math.sin(distance / r) * Math.cos(Math.toRadians(latV1)), Math.cos(distance / r)
//                - Math.sin(Math.toRadians(latV1)) * Math.sin(latV2));
//        lat2 = Math.toDegrees( latV2);
//        lon2 = Math.toDegrees(lonV2);
//        
//  //    calculate distance between 1st and 2nd point
//        double latDiff = (latV1 - latV)/2;
//        double lonDiff = (lonV1 - lonV)/2;
//        double a = Math.sin(latDiff);
//        double b = Math.sin(lonDiff);
//        double d = 2*r*asin(sqrt(a*a + Math.cos(latV)*Math.cos(latV1)*b*b));
//        System.out.println("difference value " +d);
//        
//        lat = lat2;
//        lon = lon2;
//        GUI.latVal2 = latV1;
//        GUI.lonVal2 = lonV1;
//        
////        System.out.println("Class A changed val : "+lat);
////        System.out.println(+lon);
//        
//       
//    }  
    static double beta1;
    static double d;
    
    public void calculateBasicParams(){
        double latV = lat;
        double lonV = lon;
        double latV1 = GUI.latVal2;
        double lonV1 = GUI.lonVal2;  
        beta1 = calculateAngleBeta(latV,lonV,latV1,lonV1);     
        System.out.println("Bearing angle " +beta1);  
        
        //calculate distance between 1st and 2nd point
        double latDiff = (latV1 - lat)/2;
        double lonDiff = (lonV1 - lon)/2;
        double a = Math.sin(Math.toRadians(latDiff));
        double b = Math.sin(Math.toRadians(lonDiff));
        d = 2*r*asin(sqrt(a*a + Math.cos(Math.toRadians(lat))*Math.cos(Math.toRadians(latV1))*b*b));
        System.out.println("difference value " +d);               
    }        
   public void calculateNewPosition(){
        double latV = lat;
        double lonV = lon;
        double omegaVal = omega; 
        System.out.println("Omega" +omegaVal);        
        if(originVal)
        {       
            double speedV = speed;
            double bearing = course;
            double distance = speedV * 10;
            double lat2 = Math.asin(Math.sin(Math.toRadians(latV)) * Math.cos(distance / r)
                    + Math.cos(Math.toRadians(latV)) * Math.sin(distance / r) * Math.cos(Math.toRadians(bearing)));
            double lon2 = Math.toRadians(lonV)
                        + Math.atan2(Math.sin(Math.toRadians(bearing)) * Math.sin(distance / r) * Math.cos(Math.toRadians(latV)), Math.cos(distance / r)
                        - Math.sin(Math.toRadians(latV)) * Math.sin(lat2));
            lat2 = Math.toDegrees(lat2);
            lon2 = Math.toDegrees(lon2);
            System.out.println("Origin lat: " +lat2);
            System.out.println("Origin lon: " +lon2);
            lat = lat2;
            lon = lon2;
        }
        else{
            //calculate beta2 value              

            double theta = ((2* Math.PI*(omegaVal))/60) * 10;
            System.out.println("Theta value " +theta);
            double betaVal = beta1;
            double beta2 = betaVal + Math.toDegrees(theta); 
            System.out.println("New Beta value " +beta2);
            //calculate rotated 2nd points lat and lon
            
            //double meters = 50;
            // number of km per degree = ~111km (111.32 in google maps, but range varies
            // between 110.567km at the equator and 111.699km at the poles)
            // 1km in degree = 1 / 111.32km = 0.0089
            // 1m in degree = 0.0089 / 1000 = 0.0000089
            //double coef = meters * 0.0000089;
           
            double sample = (d * Math.cos(beta2)) * 0.0000089;
            System.out.println("Sample adding value to lat " +sample);
            
            double latV2 = origin.getLat() + (d * Math.cos(beta2)) * 0.0000089;
            double lonV2 = origin.getLon() + (d * Math.sin(beta2)) * 0.0000089;
            System.out.println("Origin Lat val in degrees: " + origin.getLat());
            System.out.println("Origin Lon val in degrees: " + origin.getLon());
            System.out.println("New Lat val in degrees: " + latV2);
            System.out.println("New Lon val in degrees: " + lonV2);

            beta1 = beta2;
            lat = latV2;
            lon = lonV2;
        }

    }
   public static double calculateBearing(double lat1, double lon1, double lat2, double lon2){
        double y = Math.sin(Math.toRadians(lon2 - lon1)) * Math.cos(Math.toRadians(lat2));
        double x = (Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))) -
                (Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lon2 - lon1)));
        return Math.toDegrees(Math.atan2(y,x));
    }
   public static double calculateAngleBeta(double lat1, double lon1, double lat2, double lon2){
        double bearing = calculateBearing(lat1, lon1, lat2, lon2);
         if(bearing >= 0 && bearing <= 180){
            bearing -= 90;
        }
        else if(bearing > 180 && bearing <= 360){
            bearing -= 270;
        }
        return Math.abs(bearing);
    } 
}

   

