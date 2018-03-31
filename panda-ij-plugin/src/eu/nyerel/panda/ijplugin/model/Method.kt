package eu.nyerel.panda.ijplugin.model

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
data class Method(val className: String,
                  val classNameSimple: String,
                  val packageName: String,
                  val signature: String,
                  val signatureFull: String,
                  val name: String) {

    val descriptionWithoutPackageName: String
        get() = classNameSimple + "." + signature

}