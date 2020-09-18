package org.sid.Skyline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NN <T extends Comparable<T>> {
	
	private List<Data<T,Double>> skyline;
	public float temps = 0;
	public NN(List<Data<T,Double>> data,List<List<Data<T,Double>>> task,T[] critere) {
		super();
		      long start = System.currentTimeMillis();
		skyline = sky(getTask(data,minDist(data,critere),task,critere),critere);
		      long end = System.currentTimeMillis();
			temps = = (end - start) / 1000F;
	}
	
	public List<Data<T,Double>> getSkyline(){   
        return skyline;   
    }   
	
	/**
	 * determiner l'indice d'un point skyline dans une liste.
	 * @param liste des donnees 
	 * @param critere
	 * @return l'indice du point 
	 */
	
	public int minDist(List<Data<T,Double>> list,T[] critere ){
		
		List<Double> distance = new ArrayList<Double>();
		double dist = 0;
	
		for(Data<T,Double> d:list) {
			for(int i=0;i<d.dims.size();i++) {
				dist+=d.dims.get(i);
			}
			distance.add(dist);
			dist=0;
		}
		
		if(critere[0].equals("Min") && critere[1].equals("Min")) {
			return distance.indexOf(Collections.min(distance)); //le point avec la petite distance avec l'origine
		}
		else if(critere[0].equals("Max") && critere[1].equals("Max")) {
			return distance.indexOf(Collections.max(distance)); //le point avec la plus grande distance avec l'origine 
		}
		else if(critere[0].equals("Min") && critere[1].equals("Max")) {
			List<Double> dim = new ArrayList<Double>();
			for(Data<T,Double> d:list) {
				dim.add((Double) d.dims.get(0));
			}
			return dim.indexOf(Collections.min(dim)); // le point avec la petite valeur selon la dimension 1
		}
		else if(critere[0].equals("Max") && critere[1].equals("Min")) {
			List<Double> dim = new ArrayList<Double>();
			for(Data<T,Double> d:list) {
				dim.add((Double) d.dims.get(0));
			}
			return dim.indexOf(Collections.max(dim)); //le point avec la grande valeur selon la dimension 1
		}
		return -1;
	}
		
	/**
	 * determiner les regions
	 * @param liste des donnees
	 * @param index (retourne de la fonction au-dessus selon le critere)
	 * @param task (liste des taches ou les regions seront ajouter )
	 * @param critere
	 * @return la liste des taches
	 */
	
	public List<List<Data<T,Double>>> getTask(List<Data<T,Double>> list,int index,List<List<Data<T,Double>>> task,T[] critere) {
		
		List<Data<T,Double>> region1 = new ArrayList<Data<T,Double>>();
		List<Data<T,Double>> region2 = new ArrayList<Data<T,Double>>();
		
		task.add(list);
		if((critere[0].equals("Min") && critere[1].equals("Min"))||(critere[0].equals("Max") && critere[1].equals("Max"))) {
			for(Data<T,Double> d:list) {			
				if(list.isEmpty()) break;
				if(d.dims.get(0)>list.get(index).dims.get(0) && d.dims.get(1)<list.get(index).dims.get(1))
				{
				region1.add(d);	
				}				
				else if(d.dims.get(0)<list.get(index).dims.get(0) && d.dims.get(1)>list.get(index).dims.get(1))
				{
				region2.add(d);	
				}			
		}
		}
		else {
			for(Data<T,Double> d:list) {			
				if(list.isEmpty()) break;
				if(d.dims.get(0)>list.get(index).dims.get(0) && d.dims.get(1)>list.get(index).dims.get(1))
					{
					region1.add(d);	
					}				
				else if(d.dims.get(0)<list.get(index).dims.get(0) && d.dims.get(1)<list.get(index).dims.get(1))
					{
					region2.add(d);	
					}			
			}
		}
				
		if (!region1.isEmpty()) { getTask(region1,minDist(region1, critere),task,critere);}		
		if (!region2.isEmpty()) { getTask(region2,minDist(region2, critere),task,critere);} 
		
//		System.out.println(task);
		
		return task;
	}
	
	/**
	 * determiner les points skyline.
	 * @param task (liste des taches)
	 * @param critere
	 * @return la liste des points skyline
	 */
	
	public List<Data<T,Double>> sky(List<List<Data<T,Double>>> task,T[] critere){
		List<Data<T,Double>> skyline = new ArrayList<Data<T,Double>>();
		for(List<Data<T,Double>> l : task) {
			if(l.size()==1) skyline.add(l.get(0));
			else if(!l.isEmpty()) {skyline.add(l.get(minDist(l,critere)));}
		}
		return skyline;
	}
}
