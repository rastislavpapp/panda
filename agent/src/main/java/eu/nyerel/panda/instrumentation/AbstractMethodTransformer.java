package eu.nyerel.panda.instrumentation;

import eu.nyerel.panda.instrumentation.util.MethodBuildingUtil;
import eu.nyerel.panda.instrumentation.util.NamingUtil;
import javassist.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class AbstractMethodTransformer {

	public void transform(CtClass ctClass, CtMethod ctMethod) throws CannotCompileException, NotFoundException {
		ctClass.addMethod(getCopy(ctClass, ctMethod));
		String transformedBody = createTransformedBody(ctMethod);
		System.out.println("Transformed body of method " + ctMethod.getLongName() + ":\n" + transformedBody);
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
		return NamingUtil.pandalize(ctMethod.getName() + "_" + uniqueTransformationName());
	}

	protected abstract String uniqueTransformationName();

	private CtMethod getCopy(CtClass ctClass, CtMethod ctMethod) throws CannotCompileException {
		return CtNewMethod.copy(ctMethod, getInternalMethodName(ctMethod), ctClass, null);
	}

}
