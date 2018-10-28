package eu.nyerel.panda.ijplugin.runner

import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.classFilter.ClassFilterEditor

import javax.swing.*
import java.awt.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class PandaSettingsEditor<P : RunConfigurationBase>(val project: Project) : SettingsEditor<P>() {

    private val monitoredClassesEditor: ClassFilterEditor
    private val excludedClassesEditor: ClassFilterEditor
    private val mainPanel: JPanel = JPanel(GridBagLayout())

    init {
        this.monitoredClassesEditor = createClassFilterEditor("Monitored classes")
        this.excludedClassesEditor = createClassFilterEditor("Excluded classes")
        this.mainPanel.add(this.monitoredClassesEditor, createGridBagConstraints(0, 0))
        this.mainPanel.add(this.excludedClassesEditor, createGridBagConstraints(0, 1))
    }

    private fun createGridBagConstraints(x: Int, y: Int): GridBagConstraints {
        return GridBagConstraints(x, y, 1, 1, 1.0, 1.0, 10, 1, Insets(0, 0, 0, 0), 0, 0)
    }

    private fun createClassFilterEditor(title: String): ClassFilterEditor {
        val editor = ClassFilterEditor(project)
        editor.setClassDelimiter(".")
        editor.border = IdeBorderFactory.createTitledBorder(title, false)
        return editor
    }

    override fun resetEditorFrom(config: P) {
        monitoredClassesEditor.filters = PandaSettings.getMonitoredClasses(config.project)
        excludedClassesEditor.filters = PandaSettings.getExcludedClasses(config.project)
    }

    override fun applyEditorTo(config: P) {
        PandaSettings.setMonitoredClasses(config.project, monitoredClassesEditor.filters)
        PandaSettings.setExcludedClasses(config.project, excludedClassesEditor.filters)
    }

    override fun createEditor(): JComponent {
        return mainPanel
    }

}
