package com.interactivebrokers.trader.web.test.tests;

import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.interactivebrokers.trader.web.dao.InstrumentsDao;
import com.interactivebrokers.trader.web.model.Instrument;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:com/interactivebrokers/trader/web/config/dao-context.xml",
		"classpath:com/interactivebrokers/trader/web/config/security-context.xml",
		"classpath:com/interactivebrokers/trader/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class InstrumentDaoTests {
	
	@Autowired
	private InstrumentsDao instrumentsDao;
	
	@Autowired
	private DataSource dataSource;

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		String sql = "delete from instruments where symbol_id=\"11111\"";
		jdbc.execute(sql);
	}
	
	@Test
	public void testCreateInstrument() {
		Instrument instrument = new Instrument("11111", "instrument desc", 22.2);
		
		assertTrue("User creation should return true", instrumentsDao.createInstrument(instrument));
	}

}
