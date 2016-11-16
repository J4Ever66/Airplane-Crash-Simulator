package f16cs350.project.loader.communication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *  CSCD:350 -- PROJECT Pt. 1B -- TEAM 5
 *
 **/
public class CommunicationLoader {
    private int base, encodeSize;
    private HashMap<String, String> dictionary;
    private HashSet<String> customWords;
    private String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
    "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};



    public CommunicationLoader(int base, int encodingSize){
        this.base = base;
        this.encodeSize = encodingSize;
    }


    public int calculateMaxValue(int base, int size){
        return -1;
    }

    public String decodeStatement(String s){
        return null;
    }

    public String decodeLog(String log){
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
        return null;
    }

    public boolean isValidCustomIndex(String index){
        return false;
    }

    public boolean isValidIndex(String index){
        return false;
    }

    public String getNextFreeIndex(){
        return null;
    }

    public int getDictionaryChecksum(){
        return -1;
    }

    public Map<String, String> getDictionary(){ // hashmap
        return this.dictionary;
    }

    public int getBase(){
        return this.base;
    }

    public int getEncodingSize(){
        return this.encodeSize;
    }
}
