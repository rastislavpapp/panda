package eu.nyerel.panda.instrumentation.jdbc.transformers;

import eu.nyerel.panda.instrumentation.AbstractMethodTransformer;
import eu.nyerel.panda.instrumentation.MethodModelClassTransformer;
import eu.nyerel.panda.instrumentation.jdbc.model.ConnectionCreatePreparedStatement;
import eu.nyerel.panda.instrumentation.util.NamingUtil;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.sql.PreparedStatement;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class ConnectionCreatePreparedStatementTransformer extends MethodModelClassTransformer {

	private static final AbstractMethodTransformer METHOD_TRANSFORMER = new AbstractMethodTransformer() {

		@Override
		protected String createTransformedBody(CtMethod ctMethod) throws NotFoundException {
			String varStatement = NamingUtil.pandalize("local_ps");
			return new StringBuilder(PreparedStatement.class.getSimpleName()).append(" ")
					.append(varStatement).append(" = ")
					.append(getReturningInternalMethodCall(ctMethod)).append(";\n").append("System.out.println(\"Inserting \" + $1 + \" into field ").append(PreparedStatementTransformer.FIELD_STORED_SQL).append(" of class \" + ").append(varStatement).append(".getClass().getName() + \");\"")
					.append(varStatement).append(".").append(PreparedStatementTransformer.FIELD_STORED_SQL).append(" = ").append("$1;\n")
					.append("return ").append(varStatement).append(";").toString();
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
