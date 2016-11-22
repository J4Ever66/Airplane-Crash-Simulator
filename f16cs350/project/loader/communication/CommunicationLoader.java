package f16cs350.project.loader.communication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *  CSCD:350 -- PROJECT Pt. 1B -- TEAM 5
 *  Aaron Griffis - Grant Edwards - Jordan Everard
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

    private HashMap<String, String> dictionary;
    private HashSet<String> customWords;
    private String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
    "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public CommunicationLoader(int base, int encodingSize){
        this.wordCount = 0;
        this.base = base;
        this.encodeSize = encodingSize;
        this.customWords = new HashSet<>();
        this.dictionary = new HashMap<>();
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

    public String decodeStatement(String s){
        return null;
    }

    public String decodeLog(String log){
        //split by new line
        //first line is checksum
        //compare checksum to actual checksum
        //each line after is statement
        //split time and statement
        //decodeTime();
        //decodeStatement();
        //rebuild decoded log and return
        return null;
    }

    public double decodeTime(String time){
        return 0.0;
    }

    public String encodeStatement(double time, String words){
        return null;
    }

    public String encodeTime(double time){
        return null;
    }

    public String registerCustomWord(String index, String word){
        if(!dictionary.containsKey(index)) {
            this.dictionary.put(index, word);
            this.checksum = dictionary.hashCode();
            wordCount++;
        }else{
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
        return res;
    }


    private void fillDictionary(){  // populate the dictionary at startup with hard coded words ( does not save custom words )
        for(int x = 0; x < dictionaryString.length; x++){
            dictionary.put(getNextFreeIndex(), dictionaryString[x]);
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
