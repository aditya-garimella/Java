package com.interactivebrokers.trader.web.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sun.istack.internal.NotNull;

public class Instrument {
	@Pattern(regexp = "([\\w]{3,7})|([\\w]{3}/[\\w]{3})")
	private String symbolID;
	@NotNull
	@Size(min = 4, max = 70)
	private String instrumentDesc;
	
	private double price;
	private int quantity;
	private boolean check;
	
	public Instrument(){
		
	}

	public Instrument(String symbolID, String instrumentDesc, double price) {
		this.symbolID = symbolID;
		this.instrumentDesc = instrumentDesc;
		this.price = price;
	}

	public String getSymbolID() {
		return symbolID;
	}

	public void setSymbolID(String symbolID) {
		this.symbolID = symbolID;
	}

	public String getInstrumentDesc() {
		return instrumentDesc;
	}

	public void setInstrumentDesc(String instrumentDesc) {
		this.instrumentDesc = instrumentDesc;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (check ? 1231 : 1237);
		result = prime * result + ((instrumentDesc == null) ? 0 : instrumentDesc.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((symbolID == null) ? 0 : symbolID.hashCode());
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
		Instrument other = (Instrument) obj;
		if (check != other.check)
			return false;
		if (instrumentDesc == null) {
			if (other.instrumentDesc != null)
				return false;
		} else if (!instrumentDesc.equals(other.instrumentDesc))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (symbolID == null) {
			if (other.symbolID != null)
				return false;
		} else if (!symbolID.equals(other.symbolID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Instrument [symbolID=" + symbolID + ", instrumentDesc=" + instrumentDesc + ", price=" + price
				+ ", quantity=" + quantity + ", check=" + check + "]";
	}

}
