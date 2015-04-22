package eu.nyerel.panda.ijplugin.runner;

import eu.nyerel.panda.Constants;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum AgentFacade {

	INSTANCE;

	private MonitoringResultService monitoringResultService;
	private Registry registry;

	public List<CallTreeNode> getCallTree() {
		try {
			return getMonitoringResultService().getCallTree();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void shutdown() {
		try {
			getMonitoringResultService().shutdown();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	private void initMonitoringResultService() {
		if (registry == null) {
			try {
				registry = LocateRegistry.getRegistry("localhost", Constants.RMI_PORT);
				monitoringResultService = (MonitoringResultService) registry.lookup(Constants.RMI_ID);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			} catch (NotBoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private MonitoringResultService getMonitoringResultService() {
		if (monitoringResultService == null) {
			initMonitoringResultService();
		}
		return monitoringResultService;
	}

}
