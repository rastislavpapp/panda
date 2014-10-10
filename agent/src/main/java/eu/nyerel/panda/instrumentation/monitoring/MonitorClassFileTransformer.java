package eu.nyerel.panda.instrumentation.monitoring;

import eu.nyerel.panda.instrumentation.AbstractClassFileTransformer;
import eu.nyerel.panda.util.CollectionUtil;
import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.util.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitorClassFileTransformer extends AbstractClassFileTransformer implements ClassFileTransformer {

	public static final Set<String> TRANSFORM_PACKAGES = CollectionUtil.set(
			"eu.nyerel.panda.testapp",
			"org.postgresql"
	);

	private MonitoredMethodTransformer transformer = new MonitoredMethodTransformer();

	@Override
	protected boolean shouldTransform(String className) {
		for (String packageName: TRANSFORM_PACKAGES) {
			if (isFromPackage(className, packageName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException {
		for (CtMethod method : ctClass.getDeclaredMethods()) {
			if (!method.isEmpty()) {
				transformer.transform(ctClass, method);
			}
		}
	}

	@Override
	protected String getTransformedFlag() {
		return "method_inspection";
	}

	private boolean isFromPackage(String className, String packageName) {
		return className.startsWith(packageName);
	}

}