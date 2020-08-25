package org.sid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sid.Skyline.BNL;
import org.sid.Skyline.Bitmap;
import org.sid.Skyline.DC;
import org.sid.Skyline.Data;
import org.sid.Skyline.Index;
import org.sid.Skyline.NN;
import org.sid.entities.Data4D;
import org.sid.repositories.Data4dRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/data")
public class DataControler<T extends Comparable<T>> {
	
	@Autowired
	Data4dRepository data4dRepository;

	public double temps = 0.0;


	@GetMapping
	@RequestMapping("/{skyline}/{cho1}/{cri1}/{cho2}/{cri2}")
	public List<Data<String,Double>> listData2D(
			@PathVariable(name = "skyline") String skyline,
			@PathVariable(name = "cho1") String cho1,
			@PathVariable(name = "cri1") String cri1,
			@PathVariable(name = "cho2") String cho2,
			@PathVariable(name = "cri2") String cri2
			){
		
		String[] critere = {cri1,cri2};
		ArrayList<Data<String,Double>> arr = new ArrayList<>();
		ArrayList<Data<Long,Double>> arrID = new ArrayList<>();

		List<Data4D> ld = data4dRepository.findAll();
		ArrayList<Double> arr2;
		for(Data4D d : ld) {
			arr2 = new ArrayList<>();
			arr2.add(getDataFinder(cho1, d)); arr2.add(getDataFinder(cho2, d));
			arr.add(new Data<String,Double>(d.getName(),arr2));
			arrID.add(new Data<Long,Double>(d.getId(),arr2));

		}
		
		if(skyline.equals("INDEX")) {
			Index index = new Index(arrID,critere);
			temps = index.temps;
			return index.getSkyline();
		}
		else if(skyline.equals("Bitmap")) {
			Bitmap<String> bm = new Bitmap<String>(arr,critere);
			ArrayList<Data<String,Double>> skbm = new ArrayList<>();
			temps = 0;
			for(Data<String,Double> da : bm.getListOfBitsDim()) {
				long start = System.nanoTime();
				Data<String,Double> sky = bm.Skyline(da);
				long end = System.nanoTime();
				temps = temps + (end - start)/1000000F;
				if(sky != null) {
					skbm.add(sky);
				}
			}

			return skbm;
		}
		else if(skyline.equals("BNL")) {
			BNL bnl = new BNL(arr,critere);
			temps = bnl.temps;
			return bnl.getSkyline();
		}
		else if(skyline.equals("NN")) {
			List<List<Data<String,Double>>> task = new ArrayList<List<Data<String,Double>>>();
			NN nn = new NN(arr,task,critere);
			temps = nn.temps;
			return nn.getSkyline();
		}
		
	
		
		DC dc = new DC(critere,arr);
		temps = dc.temps;
		return dc.getSkyline();

		
	}
	@GetMapping
	@RequestMapping("/alldata/{cri1}/{cri2}")
	public List<Data<String,Double>> list(@PathVariable(name = "cri1") String cri1,
			@PathVariable(name = "cri2") String cri2){
		
		ArrayList<Data<String,Double>> arr = new ArrayList<>();
		List<Data4D> ld = data4dRepository.findAll();
		ArrayList<Double> arr2;
		for(Data4D d : ld) {
			arr2 = new ArrayList<>();
			arr2.add(getDataFinder(cri1, d)); arr2.add(getDataFinder(cri2, d));
			arr.add(new Data<String,Double>(d.getName(),arr2));
		}
		
		return arr;
		
	}
	
	@GetMapping
	@RequestMapping("/{skyline}/{cho1}/{cri1}/{cho2}/{cri2}/{cho3}/{cri3}")
	public List<Data<T,Double>> listData3D(
			@PathVariable(name = "skyline") String skyline,
			@PathVariable(name = "cri1") String cri1,
			@PathVariable(name = "cri2") String cri2,
			@PathVariable(name = "cri3") String cri3,
			@PathVariable(name = "cho1") String cho1,
			@PathVariable(name = "cho2") String cho2,
			@PathVariable(name = "cho3") String cho3
			){
		
		String[] critere = {cri1,cri2,cri3};
		
		ArrayList<Data<String,Double>> arr = new ArrayList<>();
		ArrayList<Double> arr2 ;
		
		for(Data4D d : data4dRepository.findAll()) {
			
			arr2 = new ArrayList<>();
			arr2.add(getDataFinder(cho1, d)); arr2.add(getDataFinder(cho2, d));arr2.add(getDataFinder(cho3, d));
			arr.add(new Data<String,Double>(d.getName(),arr2));

		}
		
		if(skyline.equals("INDEX")) {
			Index index = new Index(arr,critere);
			temps = index.temps;
			return index.getSkyline();
		}
		else if(skyline.equals("Bitmap")) {
			Bitmap bm = new Bitmap(arr,critere);
			List<Data<T, Double>> skbm = new ArrayList<>();
			int size = bm.getListOfBitsDim().size();
			long start = System.nanoTime();
			for(int i = 0; i < size ; i++) {
				Data<T,Double> sky = bm.Skyline((Data) bm.getListOfBitsDim().get(i));
				if(sky != null) {
					skbm.add(sky);
				}
			}
			long end = System.nanoTime();
			temps = (end - start)/1000000F;

			return skbm;
		}
		else if(skyline.equals("BNL")) {
			BNL bnl = new BNL(arr,critere);
			temps = bnl.temps;
			return bnl.getSkyline();
		}
		else if(skyline.equals("NN")) {
			List<List<Data<T,Double>>> task = new ArrayList<List<Data<T,Double>>>();
			NN nn = new NN(arr,task,critere);
			temps = nn.temps;
			return nn.getSkyline();
		}
		
	
		
		DC dc = new DC(critere,arr);
		temps = dc.temps;
		return dc.getSkyline();
		
	}
	
	@GetMapping
	@RequestMapping("/{skyline}/{cho1}/{cri1}/{cho2}/{cri2}/{cho3}/{cri3}/{cho4}/{cri4}")
	public List<Data<String,Double>> listData4D(
			@PathVariable(name = "skyline") String skyline,
			@PathVariable(name = "cri1") String cri1,
			@PathVariable(name = "cri2") String cri2,
			@PathVariable(name = "cri3") String cri3,
			@PathVariable(name = "cri4") String cri4,
			@PathVariable(name = "cho1") String cho1,
			@PathVariable(name = "cho2") String cho2,
			@PathVariable(name = "cho3") String cho3,
			@PathVariable(name = "cho4") String cho4){
		
		String[] critere = {cri1,cri2,cri3,cri4};
		ArrayList<Data<String,Double>> arr = new ArrayList<>();
		ArrayList<Double> arr2 ;
		
		for(Data4D d : data4dRepository.findAll()) {
			arr2 = new ArrayList<>();
			arr2.add(getDataFinder(cho1, d)); arr2.add(getDataFinder(cho2, d));arr2.add(getDataFinder(cho3, d));arr2.add(getDataFinder(cho4, d));
			arr.add(new Data<String,Double>(d.getName(),arr2));
		}
		
		if(skyline.equals("INDEX")) {
			Index index = new Index(arr,critere);
			temps = index.temps;
			return index.getSkyline();
		}
		else if(skyline.equals("Bitmap")) {
			Bitmap<String> bm = new Bitmap<String>(arr,critere);
			ArrayList<Data<String,Double>> skbm = new ArrayList<>();
			temps = 0;
			for(Data<String,Double> da : bm.getListOfBitsDim()) {
				long start = System.nanoTime();
				Data<String,Double> sky = bm.Skyline(da);
				long end = System.nanoTime();
				temps = temps + (end - start)/1000000F;
				if(sky != null) {
					skbm.add(sky);
				}
			}

			return skbm;
		}
		else if(skyline.equals("BNL")) {
			BNL bnl = new BNL(arr,critere);
			temps = bnl.temps;
			return bnl.getSkyline();
		}
		else if(skyline.equals("NN")) {
			List<List<Data<String,Double>>> task = new ArrayList<List<Data<String,Double>>>();
			NN nn = new NN(arr,task,critere);
			temps = nn.temps;
			return nn.getSkyline();
		}
		
	
		
		DC dc = new DC(critere,arr);
		temps = dc.temps;
		return dc.getSkyline();

		
	}
	
	public static double getDataFinder(String criteria, Data4D d) {
		if(criteria.equals("dim1")){
			return d.getDim1();
		}else if(criteria.equals("dim2")) {
			return d.getDim2();
		}else if(criteria.equals("dim3")) {
			return d.getDim3();
		}
			return d.getDim4();
		
	}
	
	@GetMapping
	@RequestMapping("/temps")
	public double AlgoTemps() {
		
		return temps;
	}
}
