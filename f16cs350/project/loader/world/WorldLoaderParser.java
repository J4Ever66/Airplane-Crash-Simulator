package f16cs350.project.loader.world;

import f16cs350.atc.datatype.*;
import f16cs350.atc.graphics.geometry.GeometryPolyline;
import f16cs350.atc.world.airport.*;
import f16cs350.atc.world.navigation.A_ComponentNavaid;

import java.io.InputStream;
import java.util.*;

/**     ###     WorldLoader Draft 2    ###
 *      Aaron Griffis - Grant Edwards - Jordan Everard
 **/
public class WorldLoaderParser {
    private final WorldLoader _world;
    private final InputStream _stream;
    private HashMap<String, A_ComponentAirport<?>> _airportComponents;
    private HashMap<String, GeometryPolyline> _polylines;
    private HashMap<String, A_ComponentNavaid<?>> _navaidComponents;
    private ArrayList<ComponentAirport> _airports;

    public WorldLoaderParser(WorldLoader wl, InputStream is){
        this._world = wl;
        this._stream = is;
        this._airports = new ArrayList<>();
        this._polylines = new HashMap<>();
        _airportComponents = new HashMap<>();
        _polylines = new HashMap<>();
        _navaidComponents = new HashMap<>();
    }

    public void parse() throws Exception {

        try {
            String data = streamToString();
            _stream.close(); // CLOSE STREAM?
            Scanner scan = new Scanner(data);
            while(scan.hasNextLine()){
                String lines = scan.nextLine();
                if(lines.contains("/")){ lines = lines.substring(0, lines.indexOf('/'));    }
                String[] temp = lines.split("[ ><,:#\'\"\\}\\{\\)\\(]+");

                switch(temp[0].toUpperCase()){
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

    private String streamToString() throws Exception{
        Scanner in = new Scanner(_stream).useDelimiter("\\A"); // "\\A" -- parse ALL
        if(in.hasNext()){   return in.next();   }
        else{   return null;    }
    }

    private void create(final String[] line) throws Exception { // create
        System.out.println("CREATE:\n");
        for(int x = 0; x < line.length; x++){
            System.out.println("[" + x + "]" + " - " + line[x]);
        }

        switch(line[1].toUpperCase()){
            case "AIRPORT": createAirport(line);
                break;
            case "BUILDING": createBuilding(line);
                break;
            case "TAXIWAY": createTaxiway(line);
                break;
            case "RUNWAY": createRunway(line);
                break;
            case "RAMP": createRamp(line);
                break;
            case "TOWER": createTower(line);
                break;
            case "GATE": createGate(line);
                break;
            case "SIGN": createSign(line);
                break;
            case "NAVAID": createNavaid(line);
                break;
        }


    }
//    CREATE AIRPORT myairport2 AT 47#41'5":117#1'2":800 WITH BUILDINGS mybuilding1 GATES mygate1 TOWERS mytower1 SIGNS mysign1 RAMPS myramp1 TAXIWAYS mytaxiway1 RUNWAYS myrunway2
    private void createAirport(final String[] line) throws Exception {
        ComponentAirport airport = new ComponentAirport(line[3], new CoordinatesWorld3D_ATC(
                new Latitude_ATC(Integer.parseInt(line[4]), Integer.parseInt(line[5]), Double.parseDouble(line[6])),
                new Longitude_ATC(Integer.parseInt(line[7]), Integer.parseInt(line[8]), Double.parseDouble(line[9])),
                new Altitude_ATC(Double.parseDouble(line[10]))));

        for(int x = 12; x < line.length; x++){   // get id from buildings/gates/towers/signs/taxiways/runways container
            x += addComponents(airport, x, line, "BUILDING", "TAXIWAY", "RUNWAY", "RAMP", "TOWER", "GATE", "SIGN" );    // automatically adds all components to an airport before adding to list
        }
        _airports.add(airport);

    }

    private int addComponents(final ComponentAirport airport, final int count, final String[] lines, final String... delimiters) throws Exception {
        boolean delim = false;
        int index = count;
        do{
            for(int x = 0; x < delimiters.length; x++) {
                delim = lines[index].compareToIgnoreCase(delimiters[x]) == 0;
            }
            if(!delim){
                airport.addComponent(_airportComponents.get(lines[index + 1])); // fix so adds all of same type..
                index ++;
            }
        }while(!delim);
        return index;
    }

    private void createTaxiway(final String[] line) throws Exception {
        GeometryPolyline gp = _polylines.get(line[4]);
        if(gp != null) {
            _airportComponents.put(line[2], new ComponentAirportTaxiway(line[2], gp, new CoordinatesAnchor_ATC(0, 0), new CoordinatesAnchor_ATC(0, 0)));
        }else{  throw new RuntimeException("Specified polyline could not be found.");   }
    }

    private void createBuilding(final String[] line) throws Exception {
        GeometryPolyline gp = _polylines.get(line[4]);
        if(gp != null) {
            _airportComponents.put(line[2], new ComponentAirportBuilding(line[2], gp));
        }else{  throw new RuntimeException("Specified polyline could not be found.");   }
    }

    private void createRunway(final String[] line) throws Exception {
        GeometryPolyline gp = _polylines.get(line[4]);
        if(gp != null) {
            _airportComponents.put(line[2], new ComponentAirportRunway(line[2], gp, new CoordinatesAnchor_ATC(0, 0), new CoordinatesAnchor_ATC(0, 0)));
        }else{  throw new RuntimeException("Specified polyline could not be found.");   }
    }

    private void createRamp(final String[] line) throws Exception {
        GeometryPolyline gp = _polylines.get(line[4]);
        if(gp != null) {
        _airportComponents.put(line[2], new ComponentAirportRamp(line[2], _polylines.get(line[4])));
        }else{  throw new RuntimeException("Specified polyline could not be found.");   }
    }

    private void createTower(final String[] line) throws Exception {
        _airportComponents.put(line[2], new ComponentAirportTower(line[2], new CoordinatesCartesianAbsolute_ATC(Double.parseDouble(line[4]), Double.parseDouble(line[5]))));
    }

    private void createGate(final String[] line) throws Exception {
        String gateAlias = "";
        int x = 4;
        while(line[x].compareToIgnoreCase("AT") != 0){
            gateAlias += line[x];
            if(line[x + 1].compareToIgnoreCase("AT") != 0){ gateAlias += " ";   }   // add space to reform literal
            x++;
        }
        _airportComponents.put(line[2], new ComponentAirportGate(line[2], new CoordinatesCartesianAbsolute_ATC(Double.parseDouble(line[x + 1]), Double.parseDouble(line[x + 2])), gateAlias));
    }

    private void createSign(final String[] line) throws Exception {
        String signAlias = "";
        int x = 4;
        while(line[x].compareToIgnoreCase("AT") != 0){
            signAlias += line[x];
            if(line[x + 1].compareToIgnoreCase("AT") != 0){ signAlias += " ";   }   // add space to reform literal
            x++;
        }
        _airportComponents.put(line[2], new ComponentAirportSign(line[2], new CoordinatesCartesianAbsolute_ATC(Double.parseDouble(line[x + 1]), Double.parseDouble(line[x + 2])), signAlias));
    }

    //=======================================================================================================================================================================================
    private void createNavaid(final String[] line) throws Exception {
        //create VOR, NDB, ILS, AIRWAY, FIX
        // add components to the _navaid <id, object>
        // if uses other components, find the component in the _navaidComponents ( very similar to airport )
    }


    //=======================================================================================================================================================================================
    private void define(final String[] line){
        System.out.println("DEFINE:\n");
        for(int x = 0; x < line.length; x++){
            System.out.println("[" + x + "]" + " - " + line[x]);    //  { <COORDINATE> <COORDINATE> }
        }
        if(line[1].compareToIgnoreCase("POLYLINE") == 0) {  // DEFINE POLYLINE mypoly3 AS {<-10,-20> <-30,0>} {<15,-25> <-35,-20>} <50,60>


        }else{
            throw new RuntimeException("Invalid define type: " + line[1] + " is invalid.");
        }
    }

    private void add(final String[] line) throws Exception {
        System.out.println("ADD:");
        for(int x = 0; x < line.length; x++){
            System.out.println("[" + x + "]" + " - " + line[x]);
        }
        //_world.addNavaid
        //_world.addAirport
    }

    private void addNavaid(final String[] line){
        //_world.addNavaids((List)_navaidComponents.values()); -- need to
    }

    private void addAirport(final String[] line){
        // List airList = new ArrayList();
        // list.add( get ID FROM AIRPORTS HASHMAP )
        //_world.addAirports(airList);
    }
}
