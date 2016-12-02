package f16cs350.project.loader.communication;

import java.util.*;

/**
 *  CSCD:350 -- PROJECT Pt. 1B -- TEAM 5
 *  Aaron Griffis(mia) - Grant Edwards - Jordan Everard
 **/
public class CommunicationLoader {
   private int base, encodeSize, checksum, wordCount;
   private String[] dictionaryString = { "a", "above", "across", "affirmative", "after", "again", "ah", "airport", "alpha", "altitude", "american", "an", "and", "approach",
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
   private HashMap<String, String> dictionary, opposite;
   private String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
    "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

   public CommunicationLoader(int base, int encodingSize){
      this.wordCount = 0;
      this.base = base;
      this.encodeSize = encodingSize;
      this.dictionary = new HashMap<>();
      this.opposite = new HashMap<>();
      fillDictionary();
      this.checksum = dictionary.hashCode();
   }


   public int calculateMaxValue(int base, int size){
      double sum = 0;
      for(int x = 0; x < size; x++){
         sum += (base - 1) * Math.pow(base,  x);
      }
      return (int)sum;
   }

   public String decodeStatement(String statement)
   {

         int timeLength = (int) Math.round(Math.ceil(Math.log(9999999) / Math.log(getBase()))),
                 inc = getEncodingSize();
         String retWords = "",
                 time = "" + decodeTime(statement.substring(0, (timeLength)));
         try {
         statement = statement.substring(timeLength);

         int l, r;
         String key;
         for (l = 0, r = inc; r < statement.length() + 1; l += inc, r += inc)
            retWords += " " + dictionary.get(statement.substring(l, r));
      	}catch(Exception e){
        	    throw new RuntimeException("Decode statement FAILED. " +e.getMessage());
      	}
      
      return time + retWords.toUpperCase();
   }
 
   public String decodeLog(String log){
      String ret = "";
      try {
         Scanner s = new Scanner(log);
         int check = Integer.parseInt(s.nextLine());
         if (s.hasNextLine() && check == this.checksum ) {
            while(s.hasNextLine()){
               ret += decodeStatement(s.nextLine());
            }
         }
      }catch(Exception e){
         throw new RuntimeException("Decode log FAILED. " +e.getMessage());
      }
   
      return ret;
   }

   public double decodeTime(String time){
      return ((double)Integer.parseInt(time,getBase()))/10000;
   }   

   public String encodeStatement(double time, String words)
   {   
      int newNum,i=0,len = getEncodingSize(),base = getBase();
      String temp,phraseString = "",timeString = encodeTime(time);
   
      ArrayList<String> wordList = new ArrayList<String>();
      try {
      for (String retval: words.split(" ")) 
         wordList.add(retval);
            
      while(!wordList.isEmpty())
      {
         temp = opposite.get(wordList.remove(0));
         int g=(len-temp.length());
         String zeros="";
         for(i=0;i<g;i++){
            zeros += "0";}
         phraseString += (zeros+temp);
      }
      }catch(Exception e){
         throw new RuntimeException("Encode statement FAILED. " +e.getMessage());
      }
      return timeString + phraseString;
   }//////////////////////
   
   public String encodeTime(double time)//base 10
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
    
   public String registerCustomWord(String index, String word){
      if(!dictionary.containsKey(index)) {
         this.dictionary.put(index, word.toUpperCase());
         this.opposite.put(word.toUpperCase(), index);
         this.checksum = dictionary.hashCode();
         wordCount++;
      }
      else{
         throw new RuntimeException("Index already used.");
      }
      return index;
   }

   public boolean isValidCustomIndex(String index){
      int indexVal = getIndex(index);
      if(indexVal > dictionaryString.length && indexVal <= calculateMaxValue(base, encodeSize)){
         return true;
      }
      return false;
   }

   private int getIndex(String index){ // convert any given key back to integer base 10 value
      int sum = 0;
      for(int x = 0; x < index.length(); x++){
         int intVal = getIntegerValue(index.charAt(index.length() - (x + 1)));
         sum += intVal * Math.pow(base, x);
      }
      return sum;
   }

   public boolean isValidIndex(String index){ // getMaxValue
      int indexVal = getIndex(index);
      if(indexVal >= 0 && indexVal <= calculateMaxValue(base, encodeSize)){
         return true;
      }
      return false;
   }

   private int getIntegerValue(char c){
      for(int x = 0; x < key.length; x++){
         if(key[x].equals("" + c)){
            return x;
         }
      }
      return -1; // throw runtime?
   }

   public String getNextFreeIndex(){
      int curIndex = (wordCount + 1);
      String res = "";
      while(curIndex > 0){
         res = key[ curIndex % base  ] + res;
         curIndex /= base;
      }
      while(res.length() < encodeSize){
         res = "0" + res; // add on 0 until matches encodeSize
      }
      return res;
   }


   private void fillDictionary(){  // populate the dictionary at startup with hard coded words ( does not save custom words )
      for(int x = 0; x < dictionaryString.length; x++){
         String index = getNextFreeIndex();
         dictionary.put(index, dictionaryString[x].toUpperCase());
         opposite.put(dictionaryString[x].toUpperCase(), index);
         wordCount++;
      }
   }

   public Map<String, String> getDictionary(){
      return (Map<String, String>)this.dictionary.clone();
   }

   public int getDictionaryChecksum(){
      return checksum;
   }

   public int getBase(){
      return this.base;
   }

   public int getEncodingSize(){
      return this.encodeSize;
   }
}
