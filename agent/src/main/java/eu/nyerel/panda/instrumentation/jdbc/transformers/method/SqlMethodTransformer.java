package eu.nyerel.panda.instrumentation.jdbc.transformers.method;

import eu.nyerel.panda.instrumentation.AbstractMethodTransformer;
import eu.nyerel.panda.instrumentation.util.MethodBuildingUtil;
import eu.nyerel.panda.instrumentation.util.MethodCallRecorderUtil;
import eu.nyerel.panda.instrumentation.util.NamingUtil;
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
