package eu.nyerel.panda.ijplugin.runner

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.ui.classFilter.ClassFilter

import java.util.ArrayList

private const val PROPERTY_PANDA_MONITORED_CLASSES = "panda.monitored.classes"
private const val PROPERTY_PANDA_EXCLUDED_CLASSES = "panda.excluded.classes"

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object PandaSettings {

    var isAggregateCallTree = true
    var isCallTreeStructured = true
    var isSortByDuration = true
    var isShowPackageName = false
    var hideProxyClasses = true

    fun getMonitoredClasses(project: Project): Array<ClassFilter> {
        return getClasses(project, PROPERTY_PANDA_MONITORED_CLASSES)
    }

    fun getExcludedClasses(project: Project): Array<ClassFilter> {
        return getClasses(project, PROPERTY_PANDA_EXCLUDED_CLASSES)
    }

    private fun getClasses(project: Project, propertyName: String): Array<ClassFilter> {
        val properties = PropertiesComponent.getInstance(project)
        val classesProperty = properties.getValues(propertyName)
        return if (classesProperty != null) {
            convertToMonitoredClasses(classesProperty)
        } else {
            emptyArray()
        }
    }

    fun getMonitoredClassesAsString(project: Project): String {
        val monitoredClasses = convertToStringArray(getMonitoredClasses(project))
        return convertArrayToString(monitoredClasses)
    }

    fun getExcludedClassesAsString(project: Project): String {
        val monitoredClasses = convertToStringArray(getExcludedClasses(project))
        return convertArrayToString(monitoredClasses)
    }

    private fun convertArrayToString(monitoredClasses: Array<String>?): String {
        val sb = StringBuilder()
        if (monitoredClasses != null) {
            for (i in monitoredClasses.indices) {
                sb.append(monitoredClasses[i])
                if (i < monitoredClasses.size - 1) {
                    sb.append(",")
                }
            }
        }
        return sb.toString()
    }

    fun setMonitoredClasses(project: Project, monitoredClasses: Array<ClassFilter>) {
        val properties = PropertiesComponent.getInstance(project)
        properties.setValues(PROPERTY_PANDA_MONITORED_CLASSES, convertToStringArray(monitoredClasses))
    }

    fun setExcludedClasses(project: Project, excludedClasses: Array<ClassFilter>) {
        val properties = PropertiesComponent.getInstance(project)
        properties.setValues(PROPERTY_PANDA_EXCLUDED_CLASSES, convertToStringArray(excludedClasses))
    }

    private fun convertToMonitoredClasses(monitoredClassPatterns: Array<String>): Array<ClassFilter> {
        val classFilterList = ArrayList<ClassFilter>()
        for (monitoredClassPattern in monitoredClassPatterns) {
            val classFilter = ClassFilter(monitoredClassPattern)
            classFilter.isEnabled = true
            classFilterList.add(classFilter)
        }
        return classFilterList.toTypedArray()
    }

    private fun convertToStringArray(monitoredClasses: Array<ClassFilter>?): Array<String>? {
        return if (monitoredClasses == null) {
            null
        } else {
            val list = ArrayList<String>()
            for (monitoredClass in monitoredClasses) {
                if (monitoredClass.isEnabled) {
                    list.add(monitoredClass.pattern)
                }
            }
            list.toTypedArray()
        }
    }

}
