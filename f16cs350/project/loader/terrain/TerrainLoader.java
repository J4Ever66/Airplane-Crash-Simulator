package f16cs350.project.loader.terrain;

import f16cs350.atc.datatype.Altitude_ATC;
import f16cs350.atc.datatype.CoordinatesWorld3D_ATC;
import f16cs350.atc.datatype.Latitude_ATC;
import f16cs350.atc.datatype.Longitude_ATC;

import java.io.File;
import java.util.*;

/**
 *  CSCD:350 -- PROJECT Pt. 1A -- TEAM 5
 *
 **/

public class TerrainLoader{
	private File definition;    //  constructor initiate parse? Most likely not.

	public TerrainLoader(File in){
		this.definition = in;
	}

	public List<List<f16cs350.atc.datatype.CoordinatesWorld3D_ATC>> parse(){ //  THROW EXCEPTION??
		int rlats = 1, rlons = 1, ralts = 1, nodes = 1, surfaces = 1;   // start at 1 so index matches
		String rep = "";
		Scanner scan = null;
        List<List<f16cs350.atc.datatype.CoordinatesWorld3D_ATC>> terrain = null;

        try {
			scan = new Scanner(definition);

		while(scan.hasNextLine()){	// SCAN TO FIND ELEMENTS WHILE SAVING REPRESENTATION.
			String temp = scan.nextLine();
			rep += temp + "\n";	// scan file into string, no re-read.
			String[] tkn = temp.split(":| ");

			switch(tkn[0]){ // first token SHOULD BE TYPE!
				case "RLAT":	rlats++;
				break;
				case "RLON":	rlons++;
				break;
				case "RALT":	ralts++;
				break;
				case "NODE":	nodes++;
				break;
				case "SURFACE":	surfaces++;
				break;
				case "TERRAIN":	break;
				default:
			}
		}

		int[][] RLAT = new int[rlats][2], RLON = new int[rlons][2], NODES = new int[nodes][3];
		double[] RLAT_sec = new double[rlats], RLON_sec = new double[rlons];
		int[] RALT = new int[ralts];
		ArrayList<Integer>[] SURFACES = (ArrayList<Integer>[]) new ArrayList[surfaces];
        ArrayList<Integer> terrainPH = new ArrayList<>();
		scan = new Scanner(rep);

		while(scan.hasNextLine()){	// 0RLAT 1# 2#:3#:4# -- 0RALT 1# 2#  -- TYPE INDEX VAL:VAL:VAL
			String temp = scan.nextLine();
			String[] tkn = temp.split(":| "); // delimit [SPACE] || :

			switch(tkn[0]){ // prevent duplicates?
				case "RLAT":
					RLAT[Integer.parseInt(tkn[1])][0] = Integer.parseInt(tkn[2]);	//DEG
					RLAT[Integer.parseInt(tkn[1])][1] = Integer.parseInt(tkn[3]);	//MIN
					RLAT_sec[Integer.parseInt(tkn[1])] = Double.parseDouble(tkn[4]); //SEC
				break;
				case "RLON":
					RLON[Integer.parseInt(tkn[1])][0] = Integer.parseInt(tkn[2]);
					RLON[Integer.parseInt(tkn[1])][1] = Integer.parseInt(tkn[3]);
					RLON_sec[Integer.parseInt(tkn[1])] = Double.parseDouble(tkn[4]);
				break;
				case "RALT":
				    //if(true){ //RALT[Integer.parseInt(tkn[1])]) { -- how to check if the index is already there?
                        RALT[Integer.parseInt(tkn[1])] = Integer.parseInt(tkn[2]);    // FEET
                    //}else{
                    //    throw new RuntimeException("Duplicate RALT index, failed to parse file.");
                    //}
				break;
				case "NODE":
					//if(NODES[Integer.parseInt(tkn[1])] == null) {	// PREVENT DUPLICATE ENTRY
						NODES[Integer.parseInt(tkn[1])][0] = Integer.parseInt(tkn[2]);
						NODES[Integer.parseInt(tkn[1])][1] = Integer.parseInt(tkn[3]);
						NODES[Integer.parseInt(tkn[1])][2] = Integer.parseInt(tkn[4]);
					//}else{
					//	throw new RuntimeException("Duplicate NODE index, failed to parse file.");
					//}
				break;
				case "SURFACE":	//3+
					ArrayList<Integer> surfaceNodes = new ArrayList<>();
					for(int x = 2; x < tkn.length; x++){
						surfaceNodes.add(Integer.parseInt(tkn[x])); // each node index
					}
					SURFACES[Integer.parseInt(tkn[1])] = surfaceNodes;
				break;
				case "TERRAIN":
                    for(int x = 1; x < tkn.length; x++){
                        terrainPH.add(Integer.parseInt(tkn[x])); // each node index
                    }
				    break;
				default:
			}
		}
        CoordinatesWorld3D_ATC[] COORDINATES = new CoordinatesWorld3D_ATC[nodes];

        for(int z = 1; z < nodes; z++){ // create node 1 to N
            COORDINATES[z] = new CoordinatesWorld3D_ATC(new Latitude_ATC( RLAT[z][0], RLAT[z][1], RLAT_sec[z]),
                    new Longitude_ATC(RLON[z][0], RLON[z][1], RLON_sec[z]), new Altitude_ATC(RALT[z]));
        }

        ArrayList<CoordinatesWorld3D_ATC>[] SURFACE_LISTS = (ArrayList<CoordinatesWorld3D_ATC>[]) new ArrayList[surfaces];

        for(int x = 1; x < SURFACES.length; x++){ // start 1
            ArrayList<CoordinatesWorld3D_ATC> surfaceCoordinates = new ArrayList<CoordinatesWorld3D_ATC>();
            for(int y = 0; y < SURFACES[x].size(); y++){    // INDIVIDUAL NODE PLACEHOLDER FOR SURFACE CONSTRUCTION
                surfaceCoordinates.add(COORDINATES[SURFACES[x].get(y)]); // add corresponding node according to placeholder
            }
            SURFACE_LISTS[x] = surfaceCoordinates; // add list to SURFACE OBJECT placeholder
        }

        terrain = new ArrayList<>();

        for(int w = 0; w < SURFACE_LISTS.length - 1; w++){
            terrain.add(SURFACE_LISTS[terrainPH.get(w)]);   // get place holder for surface, grab surface and add to list
        }

		}catch(Exception e){
			throw new RuntimeException(e.getMessage()); // correct thing to do? should method throw?
		}
		System.out.println("TERRAIN SIZE" + terrain.size());

        return terrain;
	}

}
