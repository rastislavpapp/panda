package eu.nyerel.panda.ijplugin.model

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object MethodDescriptionParser {

    fun parse(description: String): Method {
        var lastPeriod = description.substring(0, description.lastIndexOf("(")).lastIndexOf(".")
        val className = description.substring(0, lastPeriod)
        val signature = description.substring(lastPeriod + 1)
        val methodName = signature.substring(0, signature.indexOf("("))
        lastPeriod = className.lastIndexOf(".")
        val packageName = className.substring(0, lastPeriod)
        val classNameSimple = className.substring(lastPeriod + 1)

        return Method(className, classNameSimple, packageName, signature, description, methodName)
    }

}
