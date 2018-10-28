package eu.nyerel.panda.ijplugin

import com.intellij.openapi.components.ApplicationComponent
import eu.nyerel.panda.ijplugin.data.DumpFileReader

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class PandaPlugin: ApplicationComponent {

    override fun initComponent() {
        DumpFileReader.clear()
    }

}