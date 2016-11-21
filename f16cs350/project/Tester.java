import java.util.*;

public class Tester
{
   private static int Base = 16,encodeSize =2;            
            
   public static void main(String[] args)
   {
      System.out.println(encodeStatement(123.45,"dog one two three cleared to land runway three one left"));
   }
   
   public static int getEncodingSize()
   {
      return encodeSize;
   }
   
   public static int getBase()
   {
      return Base;
   }
   
   
   //encodeStatement(123.45,"dog one two three cleared to land runway three one left");
   public static String encodeStatement(double time, String words)
   {   
      int newNum,i=0,len = getEncodingSize(),base = getBase();
      String temp,phraseString = "",timeString = encodeTime(time);
   
      ArrayList<String> wordList = new ArrayList<String>();
      
      for (String retval: words.split(" ")) 
         wordList.add(retval.toUpperCase());

      while(!wordList.isEmpty())
      {
         temp = Integer.toString(turnIntoNumber(wordList.remove(0)),base).toUpperCase();
         int g=(len-temp.length());
         String zeros="";
         for(i=0;i<g;i++){
            zeros += "0";}
         phraseString += (zeros+temp);
      }
      
      return timeString + phraseString;
   }
   
   public static String encodeTime(double time)//base 10
   {
      long len = Math.round(Math.ceil(Math.log(9999999) / Math.log(getBase())));
      
      int newNum = Integer.parseInt(( "" + Math.round( time * 10000 )) , 10);
      //           ^convert long to int    ^convert double to long
   
      String ret = Integer.toString(newNum,getBase()).toUpperCase();
      //           ^convert to target base  
   
      int i;
      long g=(len-ret.length());
      String zeros="";
      for(i=0;i<g;i++){
         zeros += "0";}
         
      return zeros + ret;
   }//return at target base
   
   public static int turnIntoNumber(String thing)
   {  
      return 14;
   }
}


