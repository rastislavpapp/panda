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
        val args = parseArgs(signature)

        return Method(className, classNameSimple, packageName, signature, description,  args, methodName)
    }

    private fun parseArgs(signature: String): List<String> {
        val args = signature.substringAfter("(").substringBefore(")")
        return if (args.isNotEmpty()) {
            args.split(",")
        } else {
            emptyList()
        }
    }

}
