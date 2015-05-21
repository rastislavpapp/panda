package eu.nyerel.panda.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@Component
public class TestService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void fastCall() {
		recursiveInternalCall(10, false);
	}

	public void testInheritance() {
		new InheritanceTestChild().doNothing("abc");
	}

	@Transactional
	public void callDatabase() {
		jdbcTemplate.update("insert into person (firstName, lastName) values (?, ?)", "Janko", "Hrasko");
	}

	public String makeInternalCalls(int numberOfCalls) {
		return recursiveInternalCall(numberOfCalls, true);
	}

	public String recursiveInternalCall(int number, boolean randomWait) {
		if (randomWait) {
			long millis = Math.round(1 + Math.random() * 20);
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() < (start + millis)) {
			}
		}
		if (number > 0) {
			return recursiveInternalCall(number - 1, randomWait);
		}
		return "finished";
	}

	public void waitFor(long millis) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < (start + millis)) {

		}
	}

}
