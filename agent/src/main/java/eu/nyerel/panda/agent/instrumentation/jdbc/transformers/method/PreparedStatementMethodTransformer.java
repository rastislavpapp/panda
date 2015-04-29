package eu.nyerel.panda.agent.instrumentation.jdbc.transformers.method;

import eu.nyerel.panda.agent.instrumentation.jdbc.util.PreparedStatementUtil;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PreparedStatementMethodTransformer extends SqlMethodTransformer {

	@Override
	protected String findSql() {
		return PreparedStatementUtil.class.getName() + ".getPreparedStatementQuery(this)";
	}

	@Override
	protected String uniqueTransformationName() {
		return "prep_stmnt";
	}

}
