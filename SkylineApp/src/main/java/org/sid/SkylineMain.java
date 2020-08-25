package org.sid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.sid.Skyline.Data;
import org.sid.entities.Data4D;
import org.sid.repositories.Data4dRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class SkylineMain implements CommandLineRunner{
	@Autowired
	Data4dRepository data4dRepository;
    @Autowired
    private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(SkylineMain.class, args);
	
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("l'application a été démarrée sur le port 9090");
		
		int dim;
		String Name;
		ArrayList<Double> dims = new ArrayList<Double>();
		File f = new File("src/main/resources/static/DataCiphering.txt");
			try {
				
				Scanner s = new Scanner(f).useLocale(Locale.US);
				dim = s.nextInt();
				while(s.hasNext()) {
					Name = s.next();
					
					for(int i = 0; i < dim; i++) {
						dims.add(s.nextDouble());
					}
					
					data4dRepository.save(new Data4D(dim,Name,dims));
					dims.clear();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Fichier non trouver");
	}
//		
//		HikariDataSource ds = (HikariDataSource) appContext.getBean("dataSource");
//
//		ManipDuDB mdb = new ManipDuDB(ds);
//		
//		List<Data<String,Double>> l = mdb.findALL();
//		
//		l.forEach(e->System.out.println(e.toString()));
			
		
//        String[] beans = appContext.getBeanDefinitionNames();
//        Arrays.sort(beans);
//        for (String bean : beans) {
//            System.out.println(bean);
//        }
	
	}
}