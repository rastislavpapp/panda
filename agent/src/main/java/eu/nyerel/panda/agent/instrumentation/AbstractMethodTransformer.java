package eu.nyerel.panda.agent.instrumentation;

import eu.nyerel.panda.agent.instrumentation.util.MethodBuildingUtil;
import eu.nyerel.panda.agent.instrumentation.util.NamingUtil;
import eu.nyerel.panda.agent.util.Log;
import javassist.*;
import javassist.bytecode.AccessFlag;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class AbstractMethodTransformer {

	public void transform(CtClass ctClass, CtMethod ctMethod) throws CannotCompileException, NotFoundException {
		ctClass.addMethod(getPrivateCopy(ctClass, ctMethod));
		String transformedBody = createTransformedBody(ctMethod);
		Log.debug("Transforming {0}", ctMethod.getLongName());
		ctMethod.setBody(MethodBuildingUtil.wrapBrackets(transformedBody));
	}

	protected abstract String createTransformedBody(CtMethod ctMethod) throws NotFoundException;

	protected boolean returnsVoid(CtMethod ctMethod) throws NotFoundException {
		return ctMethod.getReturnType().equals(CtClass.voidType);
	}

	protected String getReturningInternalMethodCall(CtMethod ctMethod) throws NotFoundException {
		String internalMethodCall = getInternalMethodCall(ctMethod);
		if (!returnsVoid(ctMethod)) {
			internalMethodCall = "return " + internalMethodCall;
		}
		return internalMethodCall;
	}

	protected String getInternalMethodCall(CtMethod ctMethod) {
		return getInternalMethodName(ctMethod) + "($$);";
	}

	protected String getInternalMethodName(CtMethod ctMethod) {
		return NamingUtil.pandalize(ctMethod.getName(), uniqueTransformationName());
	}

	protected abstract String uniqueTransformationName();

	private CtMethod getPrivateCopy(CtClass ctClass, CtMethod ctMethod) throws CannotCompileException {
		CtMethod copy = CtNewMethod.copy(ctMethod, getInternalMethodName(ctMethod), ctClass, null);
		int privateModifiers = AccessFlag.setPrivate(copy.getModifiers());
		copy.setModifiers(privateModifiers);
		return copy;
	}

}
