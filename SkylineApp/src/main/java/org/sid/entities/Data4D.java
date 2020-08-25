package org.sid.entities;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
 @AllArgsConstructor  @ToString
public class Data4D {
	

    // le Clé primaire doit etre unqiue 
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 75)
	private String aName;
	
	private Double dim1;
	private Double dim2;
	private Double dim3;
	private Double dim4;
	
	public Data4D() {
	}

	public Data4D(String aName, double dim1, double dim2) {
		this.aName =aName;
		this.dim1 = dim1;
		this.dim2 = dim2;
	}
	
	public Data4D(String aName, double dim1, double dim2, double dim3) {
		this.aName =aName;
		this.dim1 = dim1;
		this.dim2 = dim2;
		this.dim3 = dim3;
	}
	
	public Data4D(String aName, double dim1, double dim2, double dim3, double dim4) {
		this.aName =aName;
		this.dim1 = dim1;
		this.dim2 = dim2;
		this.dim3 = dim3;
		this.dim4 = dim4;
	}
	
	public Data4D(int n,String aName, ArrayList<Double> dims) {
		this.aName =aName;
		if(n == 1) dim1 = dims.get(0);
		if(n == 2) {
		this.dim1 = dims.get(0);
		this.dim2 = dims.get(1);
		}
		if(n == 3){
		this.dim1 = dims.get(0);
		this.dim2 = dims.get(1);
		this.dim3 = dims.get(2);
		} 
		else {
		this.dim1 = dims.get(0);
		this.dim2 = dims.get(1);
		this.dim3 = dims.get(2);
		this.dim4 = dims.get(3);
		}
		if(dim3 == null) dim3 = 0.0;
		if(dim4 == null) dim4 = 0.0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getDim1() {
		return dim1;
	}

	public void setDim1(Double dim1) {
		this.dim1 = dim1;
	}

	public Double getDim2() {
		return dim2;
	}

	public void setDim2(Double dim2) {
		this.dim2 = dim2;
	}

	public Double getDim3() {
		return dim3;
	}

	public void setDim3(Double dim3) {
		this.dim3 = dim3;
	}

	public Double getDim4() {
		return dim4;
	}

	public void setDim4(Double dim4) {
		this.dim4 = dim4;
	}

	
	public String getName() {
		return aName;
	}

	public void setName(String aName) {
		this.aName = aName;
	}
	

}
