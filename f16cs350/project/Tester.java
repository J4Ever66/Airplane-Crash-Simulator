import java.util.*;

public class Tester
{
   private static int Base = 16,encodeSize =2;       
   private static String[] dictionaryString = { "a", "above", "across", "affirmative", "after", "again", "ah", "airport", "alpha", "altitude", "american", "an", "and", "approach",
            "approved", "are", "as", "at", "atis", "before", "begin", "behind", "below", "bravo", "center", "cessna", "charlie", "clearance", "cleared", "climb", "contact", "correct",
            "course", "cross", "declare", "decrease", "degrees", "delay", "delivery", "delta", "departure", "descend", "direct", "discretion", "dme", "do", "east", "echo", "eight", "emergency",
            "enable", "engine", "established", "execute", "executing", "expect", "failure", "feet", "filed", "fire", "five", "flight", "for", "four", "foxtrot", "frequency", "front", "fuel", "gate",
            "golf", "has", "have", "heading", "heavy", "hold", "hotel", "how", "hundred", "ident", "if", "ils", "immediate", "inbound", "increase", "india", "intentions", "intercept", "is", "juliett",
            "kilo", "land", "landing", "leaving", "left", "level", "lima", "looking", "maintain", "mayday", "mike", "minute", "minutes", "missed", "navigation", "ndb", "negative", "niner", "north",
            "northeast", "northwest", "not", "november", "o'clock", "of", "one", "only", "option", "or", "oscar", "out", "outbound", "own", "pan-pan", "papa", "per", "pilot", "point", "problem", "proceed",
            "quebec", "radar", "ramp", "readback", "ready", "report", "request", "resume", "right", "risk", "roger", "romeo", "runway", "say", "second", "seconds", "service", "seven", "sierra", "sight",
            "six", "south", "southeast", "southwest", "speed", "squawk", "standby", "takeoff", "tango", "taxi", "taxiway", "terminal", "terminated", "terrain", "the", "then", "thousand", "three", "to",
            "tower", "traffic", "turn", "two", "um", "unable", "uniform", "united", "until", "vector", "vfr", "via", "victor", "vor", "west", "what", "when", "where", "whether", "whiskey", "who", "why",
            "wilco", "will", "with", "x-ray", "yankee", "you", "zero", "zulu"};     
            
   public static void main(String[] args)
   {
      System.out.println(encodeStatement(123.45,"yankee unable squawk standby hundred fart"));
      
      System.out.println(decodeTime(encodeTime(1)));
   }
   
   public static int getEncodingSize()
   {
      return encodeSize;
   }
   
   public static int getBase()
   {
      return Base;
   }
   
   public static double decodeTime(String time){
      return ((double)Integer.parseInt(time,getBase()))/10000;
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
   
   private static int turnIntoNumber(String search)
   {  
      
      int i;
      for(i=0;i<dictionaryString.length;i++)
         if((dictionaryString[i].toUpperCase()).compareTo(search) == 0)
            return i;
   
      return 14;//custom
   }
}
