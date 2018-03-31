package eu.nyerel.panda.ijplugin.runner

import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.classFilter.ClassFilter
import com.intellij.ui.classFilter.ClassFilterEditor

import javax.swing.*
import java.awt.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class PandaSettingsEditor<P : RunConfigurationBase>(project: Project) : SettingsEditor<P>() {

    private val monitoredClassesEditor: ClassFilterEditor
    private val mainPanel: JPanel

    init {
        this.mainPanel = JPanel(GridBagLayout())
        this.monitoredClassesEditor = ClassFilterEditor(project)
        this.monitoredClassesEditor.setClassDelimiter(".")
        this.monitoredClassesEditor.border = IdeBorderFactory.createTitledBorder("Monitored classes", false)
        this.mainPanel.add(this.monitoredClassesEditor, GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 10, 1, Insets(0, 0, 0, 0), 0, 0))
    }

    override fun resetEditorFrom(config: P) {
        val monitoredClasses = PandaSettings.getMonitoredClasses(config.project)
        monitoredClassesEditor.filters = monitoredClasses
    }

    override fun applyEditorTo(config: P) {
        PandaSettings.setMonitoredClasses(config.project, monitoredClassesEditor.filters)
    }

    override fun createEditor(): JComponent {
        return mainPanel
    }

}
