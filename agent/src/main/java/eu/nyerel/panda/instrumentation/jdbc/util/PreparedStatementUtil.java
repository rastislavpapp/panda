package eu.nyerel.panda.instrumentation.jdbc.util;

import eu.nyerel.panda.monitoring.DataStorage;

import java.sql.PreparedStatement;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PreparedStatementUtil {

	public static void storePreparedStatementQuery(PreparedStatement st, String query) {
		DataStorage.INSTANCE.store(st, query);
	}

	public static String getPreparedStatementQuery(PreparedStatement st) {
		return (String) DataStorage.INSTANCE.load(st);
	}

}
