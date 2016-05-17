package com.interactivebrokers.trader.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interactivebrokers.trader.web.model.Instrument;
import com.interactivebrokers.trader.web.model.InstrumentForm;
import com.interactivebrokers.trader.web.service.InstrumentsService;

@Controller
public class InstrumentsController {
	
	private InstrumentsService instrumentsService;
	
	@Autowired
	public void setInstrumentsService(InstrumentsService instrumentsService) {
		this.instrumentsService = instrumentsService;
	}
	
	//Gives us the form to input instrument details
	@RequestMapping("/createInstrument")
	public String createInstrument(Model model) {
	
		model.addAttribute("instrument", new Instrument());
		
		return "createInstrument";
	}
	
	
	//This handles the form submission
	@RequestMapping(value="/doCreateInstrument", method=RequestMethod.POST)
	public String doCreateInstrument(Model model, @Valid Instrument instrument, BindingResult result) {
		
		if(result.hasErrors()) {
			return "createInstrument";
		}
		
		instrumentsService.createInstrument(instrument);
		
		return "instrumentCreated";
	}
	
	//Gets all the instruments
	@RequestMapping("/instruments")
	public String showInstruments(Model model) {
		InstrumentForm instrumentForm = new InstrumentForm();
		List<Instrument> instruments = instrumentsService.getCurrentInstruments();
		instrumentForm.setInstrumentList(instruments);
		model.addAttribute("instrumentForm", instrumentForm);
		return "instruments";
	}
	
	//Used to purchase instrument and then reset the users account
	@RequestMapping(value="/purchaseInstruments", method=RequestMethod.POST)
	public String purchaseInstruments(Model model, @ModelAttribute("instrumentForm")InstrumentForm instrumentForm, Principal principal) {
		List<Instrument> instrumentList = instrumentForm.getInstrumentList();
		List<Instrument> purchasingList = new ArrayList<>();
		for(Instrument instrument : instrumentList){
			if(instrument.isCheck()){
				purchasingList.add(instrument);
			}
		}
		String purchasingOwner = principal.getName();
		instrumentsService.purchaseInstruments(purchasingOwner,purchasingList);
		return "purchaseSummary";
	}
}

