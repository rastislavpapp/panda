package eu.nyerel.panda.agent.monitoringresult;

import eu.nyerel.panda.Constants;
import eu.nyerel.panda.agent.monitoring.MonitoredEvent;
import eu.nyerel.panda.agent.monitoring.MonitoringEventListener;
import eu.nyerel.panda.agent.monitoringresult.calltree.CallTreeCreator;
import eu.nyerel.panda.agent.util.Log;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum MonitoringResultServiceImpl implements MonitoringResultService, MonitoringEventListener {

	INSTANCE;

	private Registry registry;
	private List<MonitoredEvent> data = Collections.synchronizedList(new ArrayList<MonitoredEvent>());

	public List<CallTreeNode> getCallTree() {
		return constructCallTree(data);
	}

	public void startRemote() {
		try {
			Remote remote = UnicastRemoteObject.exportObject(this, Constants.RMI_PORT);
			getOrCreateRegistry().bind(Constants.RMI_ID, remote);
			Log.debug("Monitoring result service RMI bound");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void shutdownRemote() {
		if (registry != null) {
			try {
				UnicastRemoteObject.unexportObject(this, true);
				registry.unbind(Constants.RMI_ID);
				Log.debug("Monitoring result service RMI unbound");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Registry getOrCreateRegistry() {
		if (registry == null) {
			try {
				registry = LocateRegistry.createRegistry(Constants.RMI_PORT);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		}
		return registry;
	}

	private List<CallTreeNode> constructCallTree(List<MonitoredEvent> data) {
		return new CallTreeCreator().create(data);
	}

	public void clear() {
		data.clear();
	}

	@Override
	public void onCallTreeFinished(MonitoredEvent node) {
		data.add(node);
	}

	@Override
	public void shutdown() throws RemoteException {
		shutdownRemote();
	}

}