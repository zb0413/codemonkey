package com.codemonkey.domain.seq;

import javax.persistence.Entity;

@Entity
public class YearedSequenceCreator extends SequenceCreator{

	public YearedSequenceCreator(String prefix , Integer year) {
		super(prefix);
		this.year = year;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer year;

	@Override
	public String getQuery() {
		return "prefix&&year";
	}

	@Override
	public Object[] getQueryParams() {
		return new Object[]{getPrefix() , getYear()};
	}

	public String getSequence() {
		return getPrefix() + "-" + getYear() + "-" + getCurrent();
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
}
