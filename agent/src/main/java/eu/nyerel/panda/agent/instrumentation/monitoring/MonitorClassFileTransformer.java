package eu.nyerel.panda.agent.instrumentation.monitoring;

import eu.nyerel.panda.agent.Configuration;
import eu.nyerel.panda.agent.instrumentation.AbstractClassFileTransformer;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitorClassFileTransformer extends AbstractClassFileTransformer {

    private MonitoredMethodTransformer transformer = new MonitoredMethodTransformer();

    @Override
    protected boolean shouldTransform(String className) {
        List<String> excludedClasses = Configuration.getExcludedClasses();
        List<String> monitoredClasses = Configuration.getMonitoredClasses();

        for (String excludedClass : excludedClasses) {
            if (isClass(className, excludedClass) || isFromPackage(className, excludedClass)) {
                return false;
            }
        }

        for (String classNamePattern : monitoredClasses) {
            if (isClass(className, classNamePattern) || isFromPackage(className, classNamePattern)) {
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

    private boolean isClass(String className, String classNamePattern) {
        return className.equals(classNamePattern);
    }

}