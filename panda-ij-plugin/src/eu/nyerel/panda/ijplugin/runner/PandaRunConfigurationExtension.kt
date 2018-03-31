package eu.nyerel.panda.ijplugin.runner

import com.intellij.execution.ExecutionException
import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.WriteExternalException
import org.jdom.Element

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class PandaRunConfigurationExtension : RunConfigurationExtension() {

    override fun <P : RunConfigurationBase> createEditor(configuration: P): SettingsEditor<P>? {
        return PandaSettingsEditor(configuration.project)
    }

    @Throws(ExecutionException::class)
    override fun <P : RunConfigurationBase> updateJavaParameters(configuration: P, params: JavaParameters, runnerSettings: RunnerSettings?) {
    }

    @Throws(InvalidDataException::class)
    override fun readExternal(runConfiguration: RunConfigurationBase, element: Element) {

    }

    @Throws(WriteExternalException::class)
    override fun writeExternal(runConfiguration: RunConfigurationBase, element: Element) {

    }

    override fun getEditorTitle(): String? {
        return "Panda"
    }

    override fun isApplicableFor(configuration: RunConfigurationBase): Boolean {
        return true
    }

}
