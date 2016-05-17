package com.interactivebrokers.trader.web.test.tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.interactivebrokers.trader.web.dao.UsersDao;
import com.interactivebrokers.trader.web.model.User;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:com/interactivebrokers/trader/web/config/dao-context.xml",
		"classpath:com/interactivebrokers/trader/web/config/security-context.xml",
		"classpath:com/interactivebrokers/trader/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private DataSource dataSource;

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		String sql = "delete from users where username=\"johnvath\"";
		jdbc.execute(sql);
	}
	
	@Test
	public void testCreateUser() {
		User user = new User("johnvath", "John vath", "hellothere", "john@vath.com", "A", false, "user");
		
		assertTrue("User creation should return true", usersDao.create(user));
	}
	
}
