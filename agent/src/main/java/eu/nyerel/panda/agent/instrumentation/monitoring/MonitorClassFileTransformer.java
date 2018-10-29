package eu.nyerel.panda.agent.instrumentation.monitoring;

import eu.nyerel.panda.agent.Configuration;
import eu.nyerel.panda.agent.instrumentation.AbstractClassFileTransformer;
import eu.nyerel.panda.agent.util.PatternUtil;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MonitorClassFileTransformer extends AbstractClassFileTransformer {

    private MonitoredMethodTransformer transformer = new MonitoredMethodTransformer();

    private List<Pattern> excludedClassPatterns;
    private List<Pattern> monitoredClassPatterns;

    public MonitorClassFileTransformer() {
        this.excludedClassPatterns = getPatterns(Configuration.getExcludedClasses());
        this.monitoredClassPatterns = getPatterns(Configuration.getMonitoredClasses());
    }

    @NotNull
    private List<Pattern> getPatterns(List<String> classMasks) {
        return classMasks.stream().map(PatternUtil::getPattern).collect(Collectors.toList());
    }

    @Override
    protected boolean shouldTransform(String className) {
        for (Pattern excludedPattern : excludedClassPatterns) {
            if (excludedPattern.matcher(className).matches()) {
                return false;
            }
        }

        for (Pattern monitoredPattern : monitoredClassPatterns) {
            if (monitoredPattern.matcher(className).matches()) {
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

}