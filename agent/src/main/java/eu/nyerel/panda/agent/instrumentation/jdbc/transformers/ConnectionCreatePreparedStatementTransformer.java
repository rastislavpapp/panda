package eu.nyerel.panda.agent.instrumentation.jdbc.transformers;

import eu.nyerel.panda.agent.instrumentation.MethodModelClassTransformer;
import eu.nyerel.panda.agent.instrumentation.jdbc.model.ConnectionCreatePreparedStatement;
import eu.nyerel.panda.agent.instrumentation.jdbc.util.PreparedStatementUtil;
import eu.nyerel.panda.agent.instrumentation.AbstractMethodTransformer;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ConnectionCreatePreparedStatementTransformer extends MethodModelClassTransformer {

	private static final AbstractMethodTransformer METHOD_TRANSFORMER = new AbstractMethodTransformer() {

		@Override
		protected String createTransformedBody(CtMethod ctMethod) throws NotFoundException {
			return new StringBuilder(PreparedStatementUtil.class.getName()).append(".storePreparedStatementQuery(this, $1);")
					.append(getReturningInternalMethodCall(ctMethod)).toString();
		}

		@Override
		protected String uniqueTransformationName() {
			return "conn_create_ps";
		}

	};

	@Override
	protected Class getMethodModel() {
		return ConnectionCreatePreparedStatement.class;
	}

	@Override
	protected AbstractMethodTransformer getMethodTransformer() {
		return METHOD_TRANSFORMER;
	}

}
