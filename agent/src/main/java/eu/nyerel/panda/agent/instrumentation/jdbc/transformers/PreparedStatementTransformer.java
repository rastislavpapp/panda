package eu.nyerel.panda.agent.instrumentation.jdbc.transformers;

import eu.nyerel.panda.agent.instrumentation.AbstractMethodTransformer;
import eu.nyerel.panda.agent.instrumentation.ClassTransformer;
import eu.nyerel.panda.agent.instrumentation.MethodModelClassTransformer;
import eu.nyerel.panda.agent.instrumentation.jdbc.model.PreparedStatementMethodModel;
import eu.nyerel.panda.agent.instrumentation.jdbc.transformers.method.PreparedStatementMethodTransformer;
import eu.nyerel.panda.agent.instrumentation.util.NamingUtil;
import javassist.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PreparedStatementTransformer implements ClassTransformer {

	public static final String FIELD_STORED_SQL = NamingUtil.pandalize("sql");
	private static final AbstractMethodTransformer METHOD_TRANSFORMER = new PreparedStatementMethodTransformer();
	private static final MethodModelClassTransformer METHOD_ITERATING_TRANSFORMER = new MethodModelClassTransformer() {

		@Override
		protected Class getMethodModel() {
			return PreparedStatementMethodModel.class;
		}

		@Override
		protected AbstractMethodTransformer getMethodTransformer() {
			return METHOD_TRANSFORMER;
		}

	};

	@Override
	public void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException {
		addSqlStorageField(ctClass);
		METHOD_ITERATING_TRANSFORMER.doTransform(ctClass);
	}

	private void addSqlStorageField(CtClass ctClass) throws NotFoundException, CannotCompileException {
		try {
			ctClass.getField(FIELD_STORED_SQL);
		} catch (NotFoundException e) {
			ctClass.addField(CtField.make("public String " + FIELD_STORED_SQL + ";", ctClass));
		}
	}

}
