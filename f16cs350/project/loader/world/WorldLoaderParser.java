package f16cs350.project.loader.world;

import java.io.InputStream;
import java.util.Scanner;

/**     ###     WorldLoader -- ROUGH DRAFT TEMPLATE     ###
 *      -- ENFORCED TYPES, SIZES, FORMAT, ORDER.
 *      Cannot create or add a component that A. does not exist or B. doesn't have all required sub-components
 **/
public class WorldLoaderParser {
    private WorldLoader world;
    private InputStream stream;

    public WorldLoaderParser(WorldLoader wl, InputStream is){
        this.world = wl;
        this.stream = is;

    }

    /*
        [NAVAIDS] - ILS, NDB, VOR, FIX, AIRWAY
        [AIRPORTS] - ComponentAirportBuilding, ComponentAirportGate, ComponentAirportSign, ComponentAirportTower

     WorldLoader.addNavaids( List<A_ComponentNavaid<?>> -- where ? is a subclass ComponentNavaid
     WorldLoader.addAirports( List<ComponentAirport> -- where an airport is made up of many components.

     ComponentAirport.toXML_()
     A_ComponentNAvaid -- implements XMLable interface(enforces toXML function for each subclass)
     */

    public void parse() throws Exception{

        try {
            String data = streamToString(), command = "--";
            //System.out.println("DATA: \n\n" + data); -- verified pulling in all characters from stream.

            // step 1. Isolate each individual line for more localized parsing.
            // step 2. Identify the action Create, Define or Add.
            // step 3. Identify the type of object to be instantiated/etc.
            // step 4. Send the remainder of the line to a function that matches the corresponding type, along with corresponding ArrayList.
            // step 5. Parse the data to create the object, if anything goes wrong throw RuntimeException
            // step 6. Once prompted, ADD the created object lists into the WorldLoader, call toXML.

            Scanner scan = new Scanner(data);
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(line.contains("/")){ line = line.substring(0, line.indexOf('/'));    }
                String[] temp = line.split("[ ><,:#\'\"\\}\\{\\)\\(]+");

                switch(temp[0]){
                    case "CREATE": create(temp);
                        break;
                    case "DEFINE": define(temp);
                        break;
                    case "ADD": add(temp);
                        break;
                    default: // do nothing .. bad input
                }

            }
        }catch(Exception e){
            throw new RuntimeException("WorldLoaderParser parse() failed.");
        }
    }

    private void create(String[] line){ // create
        System.out.println("CREATE:\n");
        for(int x = 0; x < line.length; x++){
            System.out.println("[" + x + "]" + " - " + line[x]);
        }
    }
    private void define(String[] line){
        System.out.println("DEFINE:\n");
        for(int x = 0; x < line.length; x++){
            System.out.println("[" + x + "]" + " - " + line[x]);
        }
    }
    private void add(String[] line){
        System.out.println("ADD:\n");
        for(int x = 0; x < line.length; x++){
            System.out.println("[" + x + "]" + " - " + line[x]);
        }
    }

    private String streamToString() throws Exception{
        Scanner in = new Scanner(stream).useDelimiter("\\A"); // "\\A" -- parse ALL
        if(in.hasNext()){   return in.next();   }
        else{   return null;    }
    }

}
