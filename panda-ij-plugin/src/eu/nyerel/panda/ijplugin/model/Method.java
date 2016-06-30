package eu.nyerel.panda.ijplugin.model;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Method {

    private String className;
    private String classNameSimple;
    private String packageName;
    private String signature;
    private String signatureFull;
    private String name;

    public String getDescriptionWithoutPackageName() {
        return classNameSimple + "." + signature;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameSimple() {
        return classNameSimple;
    }

    public void setClassNameSimple(String classNameSimple) {
        this.classNameSimple = classNameSimple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureFull() {
        return signatureFull;
    }

    public void setSignatureFull(String signatureFull) {
        this.signatureFull = signatureFull;
    }

}
