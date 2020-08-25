package org.sid.Skyline;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class Bitmap <T extends Comparable<T>>{
	
    LinkedList<TreeSet<Double>> Lt;
	private List<Data<T,Double>> listOfBitsDim;
	int[] sizeOfTreeSets;
	static int dimSize;
	
	
	public Bitmap(List<Data<T,Double>> Data,String[] critere) {
				
        Lt = new LinkedList<TreeSet<Double>>();
        
        sizeOfTreeSets = new int[Data.get(0).dims.size()];
        
		int i;
		dimSize = Data.size();
		
		//chaque treeSet i contient les valeurs distincts d dim i 
		for(i = 0; i< Data.get(0).dims.size(); i++) {
			Lt.add(new TreeSet<Double>());
		}
		
		
		for(Data<T,Double> d: Data) {
			for(i = 0; i< Data.get(0).dims.size(); i++) {
				Lt.get(i).add(d.dims.get(i));
			}
		}

		for(i = 0; i < Data.get(0).dims.size(); i++) {
			sizeOfTreeSets[i] = Lt.get(i).size();
		}
	
		Lt.clear();

		listOfBitsDim = bits(sizeOfTreeSets,Data,critere); 	
		
		dimSize = 0;

	}
	public List<Data<T,Double>> getListOfBitsDim() {
		return listOfBitsDim;
	}
	
	  /**
     * trier les donnees selon le critere
     *
     * @param  Data<T,Double>, le critere de la dimension i
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
	

		
	 /**
     * trier les donnees selon le critere
     *
     * @param  Data<T,Double>
     * @return point null si le point n'est pas un skyline ou Data<T,Double> si le point est un skyline
     *        
     */
	public Data<T,Double> Skyline(Data<T,Double> point){
		
		ArrayList<BigInteger> slicesAx = new ArrayList<BigInteger>();
		ArrayList<BigInteger> slicesAxp1 = new ArrayList<BigInteger>();
		
		//bitslice Vx && Vx+1
		
		for(int i = 0; i< point.dims.size();i++) {
		    BigInteger b1 = new BigInteger(BitSlice(point.dimPos[i],listOfBitsDim,i), 2);
			BigInteger b3 = new BigInteger(BitSlice(point.dimPos[i]+1,listOfBitsDim,i), 2);
			slicesAx.add(b1);
			slicesAxp1.add(b3);
		}
		
		BigInteger bs = slicesAx.get(0);
		BigInteger bs2 = slicesAxp1.get(0);
		
		for(int i = 0; i< point.dims.size();i++) {
			 bs = bs.and(slicesAx.get(i));
			 bs2 = bs2.or(slicesAxp1.get(i));

		}
	
		
		   Data<T,Double> Skyline = null;
		   if( bs.and(bs2).signum() == 0) {
			   Skyline = point;
		   }
		  
		return Skyline;
	}
	
	//pour 
	
	 /**
     * binariser les points donnes
     *
     * @param  sizeOfTreeSets: tableau qui contient le nombre des points distincts dans chaque dim
     * @param  Data<T,Double> , critere 
     * @return bitmap des donnees 
     *        
     */
	public List<Data<T,Double>> bits(int[] sizeOfTreeSets,List<Data<T,Double>> Data,String[] critere) {
		//int size1 = Data<T,Double>.size();
	for(int k=0 ;k < sizeOfTreeSets.length;k++) {
		trieeData(Data, k, critere);
		char[] ch =new char[sizeOfTreeSets[k]];
		Arrays.fill(ch,'1');
		int i = sizeOfTreeSets[k];
		int j = 0 ;
		boolean set = false;
		while(i>0) {
			Arrays.fill(ch,i,sizeOfTreeSets[k],'0');
			if(j<dimSize-1) {
				if(Data.get(j).dims.get(k).compareTo(Data.get(j+1).dims.get(k)) == 0 ) {
					Data.get(j).bitsdim[k] = String.valueOf(ch);
					Data.get(j).dimPos[k] = (i-1);
					i++;
					j++;
					set = true;
			}
		}
			
			Data.get(j).bitsdim[k] = String.valueOf(ch);
			Data.get(j).dimPos[k] = (i-1);

			
		i--;
		j++;
		if(set == true) {j--; set=false;}
		}
	}
	
		return Data;
	}
	
	 /**
     * pour trouver la coupe verticale dans une dimension 
     * 
     *@param  i : la position où on vas couper 
     *@param  l : la bitMap
     * @param  dim : la dimension souhaiter 
     *  
     * @return la coupe verticale
     *        
     */
	public String BitSlice(int i, List<Data<T,Double>> l,int dim) {
		String s = "";
		int size = 0;
		
		size = l.get(0).bitsdim[dim].length();
		if(i >= size) {
			char[] ch =new char[i];
			Arrays.fill(ch,'0');
			s = new String(ch);
			return s;
			}
		
		
		for(Data<T,Double> d : l) {
			
				s += d.bitsdim[dim].charAt(i);
				
		}
		
	
		return s;
	}
	
}
