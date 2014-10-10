package eu.nyerel.panda.instrumentation.jdbc.transformers.method;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class StatementMethodTransformer extends SqlMethodTransformer {

	@Override
	protected String uniqueTransformationName() {
		return "statement";
	}

}
