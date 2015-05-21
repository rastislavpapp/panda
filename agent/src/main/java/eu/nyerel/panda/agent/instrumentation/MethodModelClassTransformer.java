package eu.nyerel.panda.agent.instrumentation;

import javassist.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public abstract class MethodModelClassTransformer implements ClassTransformer {

    protected abstract Class getMethodModel();

    protected abstract AbstractMethodTransformer getMethodTransformer();

    @Override
    public void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass model = cp.get(getMethodModel().getName());
        CtMethod[] modelMethods = model.getDeclaredMethods();
        AbstractMethodTransformer methodTransformer = getMethodTransformer();
        for (CtMethod declaredMethod : modelMethods) {
            CtMethod method = findMethod(ctClass, declaredMethod);
            if (method != null) {
                methodTransformer.transform(ctClass, method);
            }
        }
    }

    private static CtMethod findMethod(CtClass classToTransform, CtMethod declaredMethod) {
        try {
            return classToTransform.getDeclaredMethod(declaredMethod.getName(), declaredMethod.getParameterTypes());
        } catch (NotFoundException e) {
            return null;
        }
    }

}
