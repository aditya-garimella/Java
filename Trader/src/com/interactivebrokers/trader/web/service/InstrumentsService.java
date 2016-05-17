package com.interactivebrokers.trader.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interactivebrokers.trader.web.dao.InstrumentsDao;
import com.interactivebrokers.trader.web.model.Instrument;

@Service("instrumentsService")
public class InstrumentsService {
	private InstrumentsDao instrumentsDao;
	
	@Autowired
	public void setInstrumentsDao(InstrumentsDao instrumentsDao) {
		this.instrumentsDao = instrumentsDao;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public void createInstrument(Instrument instrument) {
		instrumentsDao.createInstrument(instrument);
	}

	public List<Instrument> getCurrentInstruments() {
		return instrumentsDao.getCurrentInstruments();
	}
	
	@Transactional
	public void purchaseInstruments(String purchasingOwner, List<Instrument> purchasingList) {
		double amountSpent = instrumentsDao.purchaseInstruments(purchasingOwner, purchasingList);
		instrumentsDao.updateUserAccountBalance(purchasingOwner, amountSpent);
	}
	
	
}
