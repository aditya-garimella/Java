package com.interactivebrokers.trader.web.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.interactivebrokers.trader.web.validation.ValidEmail;

public class User {
	private int id;
	
	@NotBlank
	@Size(min = 5, max = 10)
	@Pattern(regexp = "[UMD]\\d{4,7}")
	private String username;

	@NotBlank
	@Pattern(regexp = "^\\S+$")
	@Size(min = 4, max = 15)
	private String password;

	@ValidEmail
	private String email;

	@NotBlank
	@Size(min = 4, max = 60)
	private String firstName;

	@Size(min = 4, max = 60)
	private String lastName;

	private boolean enabled;
	private String authority;
	
	private double initialBalance = 1000.00;

	private List<Instrument> userInstruments = new ArrayList<>();

	public User() {

	}

	public User(String username, String firstName, String lastName, String password, String email, boolean enabled,
			String authority) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.authority = authority;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public double getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}

	public List<Instrument> getUserInstruments() {
		return userInstruments;
	}

	public void setUserInstruments(List<Instrument> userInstruments) {
		this.userInstruments = userInstruments;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", enabled=" + enabled + ", authority=" + authority + ", initialBalance="
				+ initialBalance + ", userInstruments=" + userInstruments + "]";
	}

}
