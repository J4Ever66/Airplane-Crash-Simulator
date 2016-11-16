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
	private File definition;

	public TerrainLoader(File in){
		this.definition = in;
	}

	public List<List<f16cs350.atc.datatype.CoordinatesWorld3D_ATC>> parse() throws Exception{ //  THROW EXCEPTION?? -- no..
		int rlats = 1, rlons = 1, ralts = 1, nodes = 1, surfaces = 1;
		String rep = "";
		Scanner scan = null;
        List<List<f16cs350.atc.datatype.CoordinatesWorld3D_ATC>> terrain = null;

        try {
			scan = new Scanner(definition);
			while(scan.hasNextLine()){
				String temp = scan.nextLine();
				rep += temp + "\n";
				String[] tkn = temp.split(":| ");

				switch(tkn[0]){
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
			double[] RALT = new double[ralts];
			ArrayList<Integer>[] SURFACES = (ArrayList<Integer>[]) new ArrayList[surfaces];
			ArrayList<Integer> terrainPH = new ArrayList<>();
			HashSet<Integer> nodeIndices = new HashSet<>();
			scan = new Scanner(rep);


			while(scan.hasNextLine()){	// 0RLAT 1# 2#:3#:4# -- 0RALT 1# 2#  -- [TYPE] [INDEX] [VAL]:[VAL]:[VAL]
				String temp = scan.nextLine();
				String[] tkn = temp.split(":| "); // delimit [SPACE] || :

				switch(tkn[0]){
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
						RALT[Integer.parseInt(tkn[1])] = Double.parseDouble(tkn[2]);
						break;
					case "NODE":	// ENFORCE NO DUPLICATE NODE INDICES!!!!!
						int index = Integer.parseInt(tkn[1]);
						if(!nodeIndices.contains(index)) {
							NODES[index][0] = Integer.parseInt(tkn[2]);
							NODES[index][1] = Integer.parseInt(tkn[3]);
							NODES[index][2] = Integer.parseInt(tkn[4]);
							nodeIndices.add(index);
						}else{	throw new RuntimeException("Duplicate node index!");	}
						break;
					case "SURFACE":	//3+
						if(tkn.length < 5){	throw new RuntimeException("Not enough nodes for surface.");	}
						ArrayList<Integer> surfaceNodes = new ArrayList<>();
						for(int x = 2; x < tkn.length; x++){
							surfaceNodes.add(Integer.parseInt(tkn[x]));
						}
						if(SURFACES[Integer.parseInt(tkn[1])] == null) {	SURFACES[Integer.parseInt(tkn[1])] = surfaceNodes;}
						else{	throw new RuntimeException("DUPLICATE SURFACE!");	}
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

			for(int z = 1; z < nodes; z++){
				COORDINATES[z] = new CoordinatesWorld3D_ATC(new Latitude_ATC( RLAT[z][0], RLAT[z][1], RLAT_sec[z]),
						new Longitude_ATC(RLON[z][0], RLON[z][1], RLON_sec[z]), new Altitude_ATC(RALT[z]));
			}

			ArrayList<CoordinatesWorld3D_ATC>[] SURFACE_LISTS = (ArrayList<CoordinatesWorld3D_ATC>[]) new ArrayList[surfaces];

			for(int x = 1; x < SURFACES.length; x++){
				ArrayList<CoordinatesWorld3D_ATC> surfaceCoordinates = new ArrayList<CoordinatesWorld3D_ATC>();
				for(int y = 0; y < SURFACES[x].size(); y++){
					surfaceCoordinates.add(COORDINATES[SURFACES[x].get(y)]);
				}
				if(SURFACE_LISTS[x] == null) {
					SURFACE_LISTS[x] = surfaceCoordinates;
				}else{
					throw new RuntimeException("DUPLICATE SURFACE!");
				}

			}

			terrain = new ArrayList<>();

			for(int w = 0; w < SURFACE_LISTS.length - 1; w++){
				terrain.add(SURFACE_LISTS[terrainPH.get(w)]);
			}
		}catch(Exception e){
			throw new RuntimeException("File parse failed.");
		}

        return terrain;
	}

}
