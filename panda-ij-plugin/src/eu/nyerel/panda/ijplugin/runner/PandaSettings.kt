package eu.nyerel.panda.ijplugin.runner

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.ui.classFilter.ClassFilter

import java.util.ArrayList

private const val PROPERTY_PANDA_MONITORED_CLASSES = "panda.monitored.classes"

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object PandaSettings {

    var isAggregateCallTree = true
    var isCallTreeStructured = true
    var isSortByDuration = true
    var isShowPackageName: Boolean = false

    fun getMonitoredClasses(project: Project): Array<ClassFilter>? {
        val properties = PropertiesComponent.getInstance(project)
        val classesProperty = properties.getValues(PROPERTY_PANDA_MONITORED_CLASSES)
        return if (classesProperty != null) {
            convertToMonitoredClasses(classesProperty)
        } else {
            null
        }
    }

    fun getMonitoredClassesAsString(project: Project): String {
        val monitoredClasses = convertToStringArray(getMonitoredClasses(project))
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
        if (monitoredClasses == null) {
            return null
        } else {
            val list = ArrayList<String>()
            for (monitoredClass in monitoredClasses) {
                if (monitoredClass.isEnabled) {
                    list.add(monitoredClass.pattern)
                }
            }
            return list.toTypedArray()
        }
    }

}
