package f16cs350.project.loader.terrain;

import f16cs350.atc.datatype.CoordinatesWorld3D_ATC;
import f16cs350.atc.datatype.Latitude_ATC;

import java.io.File;
import java.util.*;

public class TerrainLoader{
	private File definition;    //  constructor initiate parse? Most likely not.

	public TerrainLoader(File in){
		this.definition = in;
	}

	public List<List<f16cs350.atc.datatype.CoordinatesWorld3D_ATC>> parse() throws Exception {
		int rlats = 0, rlons = 0, ralts = 0, nodes = 0, surfaces = 0;
		String rep = "";
		Scanner scan = null;

		try {	// extend try catch to whole function?
			scan = new Scanner(definition);
		}catch(Exception e){
			throw new RuntimeException("File import failed.");
		}

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
				default: throw new RuntimeException("Invalid file input.");
			}
		}

		int[][] RLAT = new int[rlats][2], RLON = new int[rlons][2], NODES = new int[nodes][3];
		double[] RLAT_sec = new double[rlats], RLON_sec = new double[rlons];
		int[] RALT = new int[ralts];


		Object[] SURFACES = new Object[surfaces];

		scan = new Scanner(rep);

		while(scan.hasNextLine()){	// 0RLAT 1# 2#:3#:4# -- 0RALT 1# 2#  -- TYPE INDEX VAL:VAL:VAL
			String temp = scan.nextLine();
			String[] tkn = temp.split(":| "); // delimit [SPACE] && :

			int tkn1 = Integer.parseInt(tkn[1]),
					tkn2 = Integer.parseInt(tkn[2]);


			switch(tkn[0]){
				case "RLAT":
                    int tkn3 = Integer.parseInt(tkn[3]);
                    double tkn4 = Double.parseDouble(tkn[4]);

					RLAT[tkn1][0] = tkn2;	//DEG
					RLAT[tkn1][1] = tkn3;	//MIN
					RLAT_sec[tkn1] = tkn4;//SEC
				break;
				case "RLON":
					RLON[tkn1][0] = tkn2;
					RLON[tkn1][1] = Integer.parseInt(tkn[3]);
					RLON_sec[tkn1] = Double.parseDouble(tkn[4]);
				break;
				case "RALT":	
					RALT[tkn1] = tkn2;	// FEET
				break;
				case "NODE":
					NODES[tkn1][0] = tkn2;
                    NODES[tkn1][1] = Integer.parseInt(tkn[3]);
                    NODES[tkn1][2] = Integer.parseInt(tkn[4]);
				break;
				case "SURFACE":	
					ArrayList<Integer> surfaceNodes = new ArrayList<>();
					for(int x = 2; x < tkn.length; x++){ // length / size?
						surfaceNodes.add(Integer.parseInt(tkn[x])); // each node index
					}
					SURFACES[tkn1] = nodes;
				break;
				case "TERRAIN":	break;
				default: throw new RuntimeException("Invalid file input.");
			}
		}

		// at this point we should have every value for every index that we need to construct.

        //CoordinatesWorld3D_ATC[] NODES = new CoordinatesWorld3D_ATC[nodes];
        //NODES[tkn1] = new CoordinatesWorld3D_ATC(new Latitude_ATC(tkn2, tkn3, tkn4), );

		// construct NODES ( 3DCOORDINATES ) .. create List of surfaces.
		// create the terrain list, add surfaces and return.
        return null;
	}



}
