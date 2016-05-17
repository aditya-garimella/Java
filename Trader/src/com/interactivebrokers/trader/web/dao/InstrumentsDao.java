package com.interactivebrokers.trader.web.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.interactivebrokers.trader.web.model.Instrument;
import com.interactivebrokers.trader.web.rowmappers.InstrumentRowMapper;

@Component("instrumentsDao")
public class InstrumentsDao {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	// Isolated all sql queries to improve re-usability
	private static final String SELECT_INSTRUMENT_ID = "select instrument_id from instruments where symbol_id=?";
	private static final String SELECT_USER_ID = "select user_id from users where username=?";
	private static final String INSERT_USER_INSTRUMENT = "insert into user_instruments(user_id, instrument_id, quantity, status) values (?, ?, ?, ?)";
	private static final String SELECT_USER_INSTRUMENT = "select count(*) from user_instruments where user_id = ? and instrument_id = ?";
	private static final String UPDATE_USER_INSTRUMENT_EXISTING = "update user_instruments set quantity=? where user_id = ? and instrument_id = ?";
	private static final String UPDATE_USER_BALANCE = "update users set initial_bal=? where username = ?";
	private static final String SELECT_USER_BALANCE = "select initial_bal from users where username=?";
	private static final String SELECT_USER_INSTRUMENT_QUANTITY = "select quantity from user_instruments where user_id = ? and instrument_id = ?";

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc);
		this.jdbcTemplate = new JdbcTemplate(jdbc);
	}

	public boolean createInstrument(Instrument instrument) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(instrument);

		return namedParameterJdbcTemplate.update(
				"insert into instruments (symbol_id, instrument_desc, price) values (:symbolID, :instrumentDesc, :price)",
				params) == 1;
	}

	public List<Instrument> getCurrentInstruments() {

		return namedParameterJdbcTemplate.query("select * from instruments", new InstrumentRowMapper());
	}

	@Transactional
	public double purchaseInstruments(String purchasingOwner, List<Instrument> purchasingList) {
		double amountSpent = 0d;

		int userId = getUserId(purchasingOwner);

		for (Instrument instrument : purchasingList) {

			int instrumentId = getInstrumentId(instrument.getSymbolID());
			int instrumentQuantity = instrument.getQuantity();
			//If user had already bought the instrument just update the record with the new quantity
			if (userInstrumentExists(userId, instrumentId)) {
				updateUserInstrumentExistingRecord(userId, instrumentId, instrumentQuantity);
			} else {
				//If user buy the instrument for first time insert a new record
				insertUserInstrumentRecord(userId, instrumentId, instrumentQuantity);
			}

			double instrumentCost = instrument.getPrice();
			//amountSpent += instrumentCost * instrumentQuantity;
			amountSpent = amountSpent + (instrumentCost * instrumentQuantity);
		}

		return amountSpent;
	}
	
	public void updateUserAccountBalance(String userName, Double spentAmount) {
		//Get the users existing balance
		double userCurrentBalance = getUserCurrentBalance(userName);
		//Calcualte the present balance
		double initialBal = userCurrentBalance-spentAmount;
		//Update the record in database with the present balance
		jdbcTemplate.update(UPDATE_USER_BALANCE, new Object[] { initialBal, userName });
	}
	
	private int getUserId(String purchasingOwner){
		return (Integer) jdbcTemplate.queryForObject(SELECT_USER_ID, new Object[] { purchasingOwner }, Integer.class);
	}
	
	private int getInstrumentId(String symbolID){
		return (Integer) jdbcTemplate.queryForObject(SELECT_INSTRUMENT_ID, new Object[] { symbolID }, Integer.class);
	}
	
	private boolean userInstrumentExists(int userId, int instrumentId) {
		return jdbcTemplate.queryForObject(SELECT_USER_INSTRUMENT, new Object[] { userId, instrumentId },
				Integer.class) > 0;
	}

	private void updateUserInstrumentExistingRecord(int userId, int instrumentId, int quantity) {
		int intialQuantity = getUserInstrumentQuantity(userId, instrumentId);
		int finalQuantity = intialQuantity + quantity;
		jdbcTemplate.update(UPDATE_USER_INSTRUMENT_EXISTING, new Object[] { finalQuantity, userId, instrumentId });
	}

	private void insertUserInstrumentRecord(int userId, int instrumentId, int quantity) {
		jdbcTemplate.update(INSERT_USER_INSTRUMENT, new Object[] { userId, instrumentId, quantity, "A" });
	}

	private double getUserCurrentBalance(String userName){
		return (Double) jdbcTemplate.queryForObject(SELECT_USER_BALANCE, new Object[] { userName }, Double.class);
	}
	
	private int getUserInstrumentQuantity(int userId, int instrumentId){
		return (Integer) jdbcTemplate.queryForObject(SELECT_USER_INSTRUMENT_QUANTITY, new Object[] { userId, instrumentId }, Integer.class);
	}
	

}
