package com.interactivebrokers.trader.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.interactivebrokers.trader.web.model.Instrument;
import com.interactivebrokers.trader.web.model.User;
import com.interactivebrokers.trader.web.rowmappers.UserRowMapper;

@Component("usersDao")
public class UsersDao {

	private NamedParameterJdbcTemplate jdbc;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
		this.jdbcTemplate = new JdbcTemplate(jdbc);
	}

	@Transactional
	public boolean create(User user) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("username", user.getUsername());
		params.addValue("password", passwordEncoder.encode(user.getPassword()));
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("email", user.getEmail());
		params.addValue("authority", user.getAuthority());
		params.addValue("enabled", user.isEnabled());
		params.addValue("initialBalance", user.getInitialBalance());
		return jdbc.update("insert into users (username, password, first_name, last_name,  email, authority, enabled, initial_bal) values (:username, :password, :firstName, :lastName,  :email, :authority, :enabled, :initialBalance)", params) == 1;
	}
	
	public boolean exists(String username) {
		return jdbc.queryForObject("select count(*) from users where username=:username", 
				new MapSqlParameterSource("username", username), Integer.class) > 0;
	}

	public List<User> getAllUsers() {
		return jdbc.query("select * from users", BeanPropertyRowMapper.newInstance(User.class));
	}

	public User getUser(String userName) {
		User user = jdbc.queryForObject("select * from users where username=:username", 
				new MapSqlParameterSource("username", userName), new UserRowMapper());
		
		return user;
		
	}

	public List<Instrument> getUserInstrumentList(Integer userId) {
		
		List<Instrument>userInstruments = new ArrayList<>();
		String sql = "select i.symbol_id, i.instrument_desc, i.price, ui.quantity from Trader.instruments i, "
				+ "Trader.user_instruments ui where i.instrument_id = ui.instrument_id and ui.status='A' and ui.user_id=?";
		List<Map<String, Object>>rows = jdbcTemplate.queryForList(sql, userId);
		for (Map row : rows) {
			Instrument instrument = new Instrument();
			instrument.setSymbolID((String)(row.get("symbol_id")));
			instrument.setInstrumentDesc((String)row.get("instrument_desc"));
			instrument.setQuantity((Integer)row.get("quantity"));
			instrument.setPrice((Double)row.get("price"));
			userInstruments.add(instrument);
		}
		
		return userInstruments;

	}

	
	
}
