package eu.nyerel.panda.testapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@Component
public class TestService {

	@Value("${db-jdbc-url}")
	private String jdbcUrl;
	@Value("${db-driver-name}")
	private String driverName;
	@Value("${db-user-name}")
	private String userName;
	@Value("${db-password}")
	private String password;

	public void fastCall() {
		recursiveInternalCall(10, false);
	}

	public void callDatabase() {
		try {
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(jdbcUrl, userName, password);
			PreparedStatement statement = conn.prepareStatement("insert into person (firstName, lastName) values (?, ?)");
			statement.setString(1, "Janko");
			statement.setString(2, "Hrasko");
			statement.execute();
			conn.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
