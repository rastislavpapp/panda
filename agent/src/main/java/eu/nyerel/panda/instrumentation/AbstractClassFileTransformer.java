package eu.nyerel.panda.instrumentation;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class AbstractClassFileTransformer implements ClassFileTransformer {

	private static final ClassPool CLASS_POOL = ClassPool.getDefault();
	private static final Set<ClassLoader> REGISTERED_CLASS_LOADERS = new HashSet<ClassLoader>();

	protected abstract boolean shouldTransform(String className);
	protected abstract void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException;
	protected abstract String getTransformedFlag();

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		className = canonizeClassName(className);
		if (shouldTransform(className)) {
			registerClassLoader(loader);
			try {
				CtClass ctClass = CLASS_POOL.get(className);
				if (!alreadyTransformed(ctClass) && !ctClass.isInterface()) {
					ctClass.defrost();
					doTransform(ctClass);
					markAsTransformed(ctClass);
					classfileBuffer = ctClass.toBytecode();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return classfileBuffer;
	}

	private String canonizeClassName(String className) {
		return className.replace("/", ".");
	}

	private void registerClassLoader(ClassLoader loader) {
		if (!REGISTERED_CLASS_LOADERS.contains(loader)) {
			CLASS_POOL.insertClassPath(new LoaderClassPath(loader));
			REGISTERED_CLASS_LOADERS.add(loader);
		}
	}

	private void markAsTransformed(CtClass ctClass) throws CannotCompileException {
		ctClass.addField(new CtField(CtClass.intType, getTransformedFlag(), ctClass));
	}

	private boolean alreadyTransformed(CtClass ctClass) {
		try {
			ctClass.getField(getTransformedFlag());
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

}
