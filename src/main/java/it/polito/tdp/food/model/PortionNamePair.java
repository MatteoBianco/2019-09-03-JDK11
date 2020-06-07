package it.polito.tdp.food.model;

public class PortionNamePair {

	private String pn1;
	private String pn2;
	private Integer weight;
	
	public PortionNamePair(String pn1, String pn2, Integer weight) {
		super();
		this.pn1 = pn1;
		this.pn2 = pn2;
		this.weight = weight;
	}

	public String getPn1() {
		return pn1;
	}

	public void setPn1(String pn1) {
		this.pn1 = pn1;
	}

	public String getPn2() {
		return pn2;
	}

	public void setPn2(String pn2) {
		this.pn2 = pn2;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pn1 == null) ? 0 : pn1.hashCode());
		result = prime * result + ((pn2 == null) ? 0 : pn2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortionNamePair other = (PortionNamePair) obj;
		if (pn1 == null) {
			if (other.pn1 != null)
				return false;
		} else if (!pn1.equals(other.pn1))
			return false;
		if (pn2 == null) {
			if (other.pn2 != null)
				return false;
		} else if (!pn2.equals(other.pn2))
			return false;
		return true;
	}
	
	
}
