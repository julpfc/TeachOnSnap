package com.julvez.pfc.teachonsnap.stats.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity. Describes a key-value pair for statistics from the application. 
 */
@Entity
public class StatsData {
	
	/** Data key and primary key for the entity */
	@Id	
	private String key;
	
	/** Data identifier to the source of the stats*/
	private int id;
	
	/** Data value for this stats*/
	private int value;
	
	/**
	 * @return data's id pointing to the source of the data
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return data's value for this stat
	 */
	public int getValue() {
		return value;
	}
			
	/**
	 * Setter for mapping big values from database
	 * @param value to be set
	 */
	public void setValue(BigInteger value) {
		if(value != null){
			this.value = value.intValue();
		}
	}
	
	/**
	 * @return data's key for this stat
	 */
	public String getKey() {
		return key;
	}
	
	
	@Override
	public String toString() {
		return "(" + key + "(" + id + "), " + value + ")";
	}
	
}
