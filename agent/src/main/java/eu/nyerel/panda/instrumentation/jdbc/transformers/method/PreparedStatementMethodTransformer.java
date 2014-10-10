package eu.nyerel.panda.instrumentation.jdbc.transformers.method;

import eu.nyerel.panda.instrumentation.jdbc.transformers.PreparedStatementTransformer;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PreparedStatementMethodTransformer extends SqlMethodTransformer {

	@Override
	protected String findSql() {
		return "$0." + PreparedStatementTransformer.FIELD_STORED_SQL;
	}

	@Override
	protected String uniqueTransformationName() {
		return "prep_stmnt";
	}

}
