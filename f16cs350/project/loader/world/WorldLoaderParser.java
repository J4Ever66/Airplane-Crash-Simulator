package f16cs350.project.loader.world;

import java.io.InputStream;

/**     ###     WorldLoader -- ROUGH DRAFT TEMPLATE     ###
 *      -- ENFORCED TYPES, SIZES, FORMAT, ORDER.
 *      Cannot create or add a component that A. does not exist or B. doesn't have all required sub-components
 **/
public class WorldLoaderParser {
    private WorldLoader world;
    private InputStream stream;

    public WorldLoaderParser(WorldLoader wl, InputStream is){
        this.world = wl;
        this.stream = is; // not sure if required.

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
        // step 1. Isolate each individual line for more localized parsing.
        // step 2. Identify the action Create, Define or Add.
        // step 3. Identify the type of object to be instantiated/etc.
        // step 4. Send the remainder of the line to a function that matches the corresponding type, along with corresponding ArrayList.
        // step 5. Parse the data to create the object, if anything goes wrong throw RuntimeException
        // step 6. Once prompted, ADD the created object lists into the WorldLoader, call toXML.
    }


}
