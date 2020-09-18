package org.sid.Skyline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Index<T extends Comparable<T>>
{

private List<List<Data<T,Double>>> ListDims;
private List<Data<T,Double>> Skyline;
public float temps;

	public Index(List<Data<T,Double>> Data,String[] critere) 
	{
		ListDims = new ArrayList<List<Data<T,Double>>>();
		
		for(int i = 0; i < critere.length ; i++)
		{
			ListDims.add(new LinkedList<Data<T,Double>>(Data));
			trieeData(ListDims.get(i),i,critere);
		}
		
		long start = System.currentTimeMillis();
		DC dc = new DC(critere,IndexSkyline(ListDims,critere));
		
		long end = System.currentTimeMillis();

		temps = (end - start)/1000F;
		Skyline = dc.getSkyline();
		
	}
	
	  /**
     * trouver le skyline condidat
     *
     * @param  BTrees 
     * @param critere
     * @return liste de donnes
     *        
     */

	public List<Data<T,Double>> IndexSkyline(List<List<Data<T,Double>>> BTrees, String[] critere){
		
		List<Data<T,Double>> skylineCondidat = new ArrayList<Data<T,Double>>();
		boolean flag = false;
		int k = 0;
		
		while(k <  BTrees.get(0).size()) {
			
			for(int j = 0; j < BTrees.size(); j++) {
				if(skylineCondidat.contains(BTrees.get(j).get(k))) {
					flag = true;
					break;
				}
				skylineCondidat.add(BTrees.get(j).get(k));
			}
			if(flag) break;
			k++;
		}
		
		return skylineCondidat;
		
	}

	 /**
     * trier les donnees selon le critere
     *
     * @param  Data, le critere de la dimension i
     * @return void
     *        
     */
	public void trieeData(List<Data<T,Double>> Data, int i,String[] critere) {
		
		if(critere[i].equals("Max")) 
		{
			Data.sort((e1,e2) -> e2.dims.get(i).compareTo(e1.dims.get(i)) );
		}else {
			Data.sort((e1,e2) -> e1.dims.get(i).compareTo(e2.dims.get(i)) );
		}
		
	}

	public List<List<Data<T,Double>>> getListDims() {
		return ListDims;
	}
	

	public List<Data<T,Double>> getSkyline() {
			return Skyline;
		}

}
