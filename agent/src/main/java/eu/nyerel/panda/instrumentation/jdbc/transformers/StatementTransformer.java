package eu.nyerel.panda.instrumentation.jdbc.transformers;

import eu.nyerel.panda.instrumentation.AbstractMethodTransformer;
import eu.nyerel.panda.instrumentation.MethodModelClassTransformer;
import eu.nyerel.panda.instrumentation.jdbc.model.StatementMethodModel;
import eu.nyerel.panda.instrumentation.jdbc.transformers.method.StatementMethodTransformer;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class StatementTransformer extends MethodModelClassTransformer {

	private static final StatementMethodTransformer METHOD_TRANSFORMER = new StatementMethodTransformer();

	@Override
	protected Class getMethodModel() {
		return StatementMethodModel.class;
	}

	@Override
	protected AbstractMethodTransformer getMethodTransformer() {
		return METHOD_TRANSFORMER;
	}

}
