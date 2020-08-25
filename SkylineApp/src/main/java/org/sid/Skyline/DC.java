package org.sid.Skyline;

import java.util.ArrayList;
import java.util.List;


public class DC <T extends Comparable<T>>{   
	
	public static String[] critere;
	private List<Data<T,Double>> skyline;
	public double temps;
	
	public DC(String[] crtiere,List<Data<T,Double>> Data) {
		this.critere = crtiere;
				
		if(critere[0].equals("Max")) 
		{
			Data.sort((e1,e2) -> e2.dims.get(0).compareTo(e1.dims.get(0)) );
		}else {
			Data.sort((e1,e2) -> e1.dims.get(0).compareTo(e2.dims.get(0)) );
		}
		
		long start = System.nanoTime();
		skyline = DCSkyline(Data);
		long end = System.nanoTime();

		temps = (end - start)/1000000F;
		critere = null;
	}
	
	
	
    public List<Data<T,Double>> getSkyline() {
		return skyline;
	}



	public List<Data<T,Double>> DCSkyline(List<Data<T,Double>> list) {   
        if (list.size() == 1) {   
            return list;   
        } else {   
            List<Data<T,Double>> left = new ArrayList<Data<T,Double>>();   
            left.addAll(list.subList(0, list.size() / 2));   
            List<Data<T,Double>> right = new ArrayList<Data<T,Double>>();   
            right.addAll(list.subList(list.size() / 2, list.size()));      
            return merge(DCSkyline(left), DCSkyline(right));   
        }   
    }
    
    public List<Data<T,Double>> merge(List<Data<T,Double>> sLeft, List<Data<T,Double>> sRight) {   
        if (sLeft == null)   
            return sRight;   
        if (sRight == null)   
            return sLeft;   
        int j, i;   
        int end = sLeft.size();
        for (j = 0; j < sRight.size(); j++) { 
            for (i =0; i < end; i++) {            
            	if (sRight.get(j).isDominated(sLeft.get(i),critere)) {   
                    break;   
                }            	
            }   
            if (i == end) {  
                sLeft.add(sRight.get(j));  
            }                 
        }        
        return sLeft;       
    }
    
 }
