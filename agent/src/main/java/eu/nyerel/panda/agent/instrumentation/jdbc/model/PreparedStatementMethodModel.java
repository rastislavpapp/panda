package eu.nyerel.panda.agent.instrumentation.jdbc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface PreparedStatementMethodModel {

	boolean execute() throws SQLException;
	ResultSet executeQuery() throws SQLException;
	int executeUpdate() throws SQLException;

}
