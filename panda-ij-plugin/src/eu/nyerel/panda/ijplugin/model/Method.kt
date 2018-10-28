package eu.nyerel.panda.ijplugin.model

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
data class Method(val className: String,
                  val classNameSimple: String,
                  val packageName: String,
                  val signature: String,
                  val signatureFull: String,
                  val args: List<String>,
                  val name: String) {

    val descriptionWithoutPackageName: String
        get() = "$classNameSimple.$name${argsWithoutPackageName.joinToString(",", "(", ")")}"

    private val argsWithoutPackageName: List<String>
        get() = args.map { it.substringAfterLast(".") }

}