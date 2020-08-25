package org.sid.Skyline;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.ToString;
@ToString
public class Data<T extends Comparable<T>,U extends Comparable<? super U>> {
	public int[] dimPos ;
	public String[] bitsdim ;
	public ArrayList<U> dims;  
	public T first;
	
	public Data(T first,ArrayList<U> arr) {
		this.first = first;
		dimPos = new int[arr.size()];
		bitsdim = new String[arr.size()];
		dims = arr;
	}
	
	public Data(Data<T,U> p) {
		this.first = p.first;
		dimPos = new int[p.dims.size()];
		bitsdim = new String[p.dims.size()];
		dims = p.dims;
	}
	public boolean isDominated(Data<T,U> d,String[] critere) {
		
		int n = dims.size();
		boolean b = true;
		boolean b2 = false;
		Data<T,U> p = new Data<T, U>(this);
		for(int i = 0 ; i < n; i++) {
			
			 b = b && p.operateur(critere[i],d.dims.get(i),dims.get(i),1);
			 b2 = b2 ||  p.operateur(critere[i],d.dims.get(i),dims.get(i),0);
		}
		
		if(b && b2) {
			return true;
		}
		 return false;
	}
	
	

	private  boolean operateur(String critere,U d1,U d2,int flag) {
		if(critere.equals("Max")) {
			if(flag == 1) {
				return (d1.compareTo(d2) == 0 || d1.compareTo(d2)== 1);
			}
		return d1.compareTo(d2)== 1;
		}
		
		if(critere.equals("Min")) {
			if(flag == 1) {
				return (d1.compareTo(d2) == 0 || d1.compareTo(d2)== -1);
			}
		}
		return d1.compareTo(d2)== -1;
	}


	
	


	
}

