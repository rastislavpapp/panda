package eu.nyerel.panda.monitoring;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class DataStorage implements MonitoringEventListener {

	private final ThreadLocal<Map<Object, Object>> dataMap = new ThreadLocal<Map<Object, Object>>();

	private static DataStorage INSTANCE;

	private DataStorage() {
	}

	public static DataStorage getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataStorage();
		}
		return INSTANCE;
	}

	public void store(Object key, Object value) {
		getDataMap().put(key, value);
	}

	public Object load(Object key) {
		return getDataMap().get(key);
	}

	private Map<Object, Object> getDataMap() {
		Map<Object, Object> dataMap = this.dataMap.get();
		if (dataMap == null) {
			dataMap = new HashMap<Object, Object>();
			this.dataMap.set(dataMap);
		}
		return dataMap;
	}

	@Override
	public void onCallTreeFinished(MonitoredEvent node) {
		dataMap.remove();
	}

}
