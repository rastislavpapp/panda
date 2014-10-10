package eu.nyerel.panda.instrumentation.jdbc.model;

import java.sql.SQLException;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface StatementMethodModel {

	public boolean execute(String sql) throws SQLException;
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException;
	public boolean execute(String sql, int[] columnIndexes) throws SQLException;
	public boolean execute(String sql, String[] columnNames) throws SQLException;

	public int executeUpdate(String sql) throws SQLException;
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException;
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException;
	public int executeUpdate(String sql, String[] columnNames) throws SQLException;


}
