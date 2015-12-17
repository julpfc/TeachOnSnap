package com.julvez.pfc.teachonsnap.stats.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StatsData {
	@Id	
	private String key;
	private int id;
	private int value;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setValue(BigInteger value) {
		if(value != null){
			this.value = value.intValue();
		}
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "(" + key + "(" + id + "), " + value + ")";
	}
	
}
