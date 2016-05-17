package com.interactivebrokers.trader.web.model;

import java.util.ArrayList;
import java.util.List;

public class InstrumentForm {
	private List<Instrument> instrumentList = new ArrayList<>();

	public List<Instrument> getInstrumentList() {
		return instrumentList;
	}

	public void setInstrumentList(List<Instrument> instrumentList) {
		this.instrumentList = instrumentList;
	}
	
}
