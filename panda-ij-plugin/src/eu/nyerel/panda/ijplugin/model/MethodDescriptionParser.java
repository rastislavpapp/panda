package eu.nyerel.panda.ijplugin.model;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum MethodDescriptionParser {

    INSTANCE;

    public Method parse(String description) {
        int lastPeriod = description.substring(0, description.lastIndexOf("(")).lastIndexOf(".");
        String className = description.substring(0, lastPeriod);
        String signature = description.substring(lastPeriod + 1);
        String methodName = signature.substring(0, signature.indexOf("("));
        lastPeriod = className.lastIndexOf(".");
        String packageName = className.substring(0, lastPeriod);
        String classNameSimple = className.substring(lastPeriod + 1);

        Method method = new Method();
        method.setClassName(className);
        method.setClassNameSimple(classNameSimple);
        method.setSignature(signature);
        method.setSignatureFull(description);
        method.setName(methodName);
        method.setPackageName(packageName);

        return method;
    }

}
