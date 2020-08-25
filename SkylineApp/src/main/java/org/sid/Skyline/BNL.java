package org.sid.Skyline;
 import java.util.ArrayList;
 import java.util.List;

 public class BNL<T extends Comparable<T>>{ 
	//liste qui joue le role de la fenetre ou les points skyline seront stockes
     private List<Data<T,Double>> skyline;   
     private static String[] critere;
 	 public double temps;

     public BNL(List<Data<T,Double>> list,String[] critere) {   
         super();   
      
         this.critere=critere;
        
         if(critere[0].equals("Max")) 
		 {
			 list.sort((e1,e2) -> e2.dims.get(0).compareTo(e1.dims.get(0)) );
		 }else {
			 list.sort((e1,e2) -> e1.dims.get(0).compareTo(e2.dims.get(0)) );
		 }
		 long start = System.nanoTime();
		 skyline = skcalculater(list);
		long end = System.nanoTime();

		temps = (end - start)/1000000F;

		 critere = null;
     }   
    
     public List<Data<T,Double>> getSkyline(){   
         return skyline;   
     }   
  //  fonction qui compare les points de la fenetre avec les points de la liste des donnees pour trouver le skyline
     
     public List<Data<T,Double>> skcalculater(List<Data<T,Double>> l){
    	 List<Data<T,Double>> fenetre = new ArrayList<Data<T,Double>>();
         if (l != null && !l.isEmpty()){   
             fenetre.add(l.get(0));   
             for (int i = 1; i < l.size(); i++) {   
                 boolean dominate = false;   
                 for (int j = 0; j < fenetre.size(); j++) {
                	//si un point de la fenetre est domine par un point de la liste
                	//des donnes, il sera supprime de la fenetre.
                	 if((fenetre.get(j)).isDominated(l.get(i),critere )){fenetre.remove(j);}
                //	si un point de la liste des donnes est domine par un point de 
                	//la fenetre, on sort de la boucle 
                     if ((l.get(i)).isDominated(fenetre.get(j),critere )) {   
                         dominate = true;   
                         break;   
                     }        
                 }   
                 if (!dominate)  
                     fenetre.add(l.get(i));            
             }
         }
         return fenetre;
     }
    
     public String toString() {
    	 return this.skyline+" ";
     }
    
 }
