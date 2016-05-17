package com.interactivebrokers.trader.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interactivebrokers.trader.web.model.Instrument;
import com.interactivebrokers.trader.web.model.User;
import com.interactivebrokers.trader.web.service.UsersService;

@Controller
public class LoginController {
	
	private UsersService usersService;
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}
	
	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		
		List<User> users = usersService.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "admin";
	}
	
	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {
		
		model.addAttribute("user", new User());
		return "newaccount";
	}
	

	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(@Valid User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "newaccount";
		}
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		if(usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		
		return "accountcreated";
	}
	
	//Used to display users account
	@RequestMapping("/accountInfo")
	public String showAccount(Model model, Principal principal) {
		User user = usersService.getUser(principal.getName());
		List<Instrument> userInstruments = usersService.getUserInstrumentList(user.getId());
		model.addAttribute("instruments",userInstruments);
		model.addAttribute("user", user);
		return "accountInfo";
	}
	
	@RequestMapping("/downloadAccountInfo")
	public void exportUserInfo(HttpServletRequest request, HttpServletResponse response, Principal principal){
		PrintWriter writer = null;
		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; AccountInfo.csv");
			String comma = ",";
			writer = response.getWriter();
			User user = usersService.getUser(principal.getName());
			List<Instrument> userInstruments = usersService.getUserInstrumentList(user.getId());
			writer.print("Instrument_ID, Instrument_Description, Instrument_Price, Instrument_Quantity\n");
			for(Instrument instrument : userInstruments){
				writer.print(instrument.getSymbolID() + comma);
				writer.print(instrument.getInstrumentDesc() + comma);
				writer.print(instrument.getPrice() + comma);
				writer.print(instrument.getQuantity() + comma);
				writer.print("\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			writer.flush();
			writer.close();
		}
		
		
	}
}
