package eu.nyerel.panda.agent.instrumentation.jdbc.transformers.method;

import eu.nyerel.panda.agent.instrumentation.AbstractMethodTransformer;
import eu.nyerel.panda.agent.instrumentation.util.MethodBuildingUtil;
import eu.nyerel.panda.agent.instrumentation.util.NamingUtil;
import eu.nyerel.panda.agent.instrumentation.util.MethodCallRecorderUtil;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class SqlMethodTransformer extends AbstractMethodTransformer {

	@Override
	protected String createTransformedBody(CtMethod ctMethod) throws NotFoundException {
		String varStart = NamingUtil.pandalize("start");
		StringBuilder body = new StringBuilder("long ").append(varStart).append(" = ").append(MethodBuildingUtil.getCurrentTimeMillis()).append(";\n");
		body.append(MethodBuildingUtil.wrapTryFinally(
				getReturningInternalMethodCall(ctMethod),
				MethodCallRecorderUtil.class.getName() + ".addQuery(" + findSql() + ", " + MethodBuildingUtil.getCurrentTimeMillis() + " - " + varStart + ");"));
		return body.toString();
	}

	protected String findSql() {
		return "$1";
	}

}
