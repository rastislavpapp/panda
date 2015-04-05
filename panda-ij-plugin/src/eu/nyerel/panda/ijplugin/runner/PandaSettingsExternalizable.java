package eu.nyerel.panda.ijplugin.runner;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@State(name = "pandaSettings", storages = {
		@Storage(id = "pandaSettings", file = StoragePathMacros.PROJECT_FILE)
})
public class PandaSettingsExternalizable implements PersistentStateComponent<PandaSettingsExternalizable> {

	private List<String> monitoredClasses = new ArrayList<String>();

	@Nullable
	@Override
	public PandaSettingsExternalizable getState() {
		return this;
	}

	@Override
	public void loadState(PandaSettingsExternalizable state) {
		XmlSerializerUtil.copyBean(state, this);
	}

	public List<String> getMonitoredClasses() {
		return monitoredClasses;
	}

	public void setMonitoredClasses(List<String> monitoredClasses) {
		this.monitoredClasses = monitoredClasses;
	}

}
