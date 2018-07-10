package com.codemonkey.domain.seq;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.codemonkey.domain.AbsEE;

@Entity
public class SequenceCreator extends AbsEE{

	private static final long serialVersionUID = 1L;
	
	private String prefix;
	
	@Column(name="start_number")
	private Long start = 0L;
	
	private Integer step = 1;
	
	@Column(name="current_number")
	private Long current = start;
	
	public SequenceCreator() {
	}

	public SequenceCreator(String prefix) {
		this.prefix = prefix;
	}

	public String getQuery() {
		return "prefix";
	}

	public Object[] getQueryParams() {
		return new Object[]{ getPrefix() };
	}

	public String getSequence() {
		return getPrefix() + "-" + getCurrent();
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}
}
