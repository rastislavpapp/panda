package eu.nyerel.panda.instrumentation;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public interface ClassTransformer {

	void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException;

}
