package eu.nyerel.panda;

import eu.nyerel.panda.instrumentation.jdbc.JdbcClassFileTransformer;
import eu.nyerel.panda.instrumentation.monitoring.MonitorClassFileTransformer;
import eu.nyerel.panda.monitoring.DataStorage;
import eu.nyerel.panda.monitoring.MonitoredEventRecorderFactory;
import eu.nyerel.panda.monitoring.MonitoringEventListenerRegistry;
import eu.nyerel.panda.monitoringresult.MonitoringResultService;
import eu.nyerel.panda.monitoringresult.MonitoringResultServiceImpl;

import java.lang.instrument.Instrumentation;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class Bootstrap {

	private final Instrumentation inst;

	public Bootstrap(Instrumentation inst) {
		this.inst = inst;
	}

	public void init() {
		System.out.println("Initializing Panda monitoring...");
		System.out.println("Monitored classes = " + Configuration.getMonitoredClasses());
		inst.addTransformer(new MonitorClassFileTransformer());
		inst.addTransformer(new JdbcClassFileTransformer());
		MonitoringEventListenerRegistry.register(createMonitoringResultServiceInstance());
		MonitoringEventListenerRegistry.register(MonitoredEventRecorderFactory.getInstance());
		MonitoringEventListenerRegistry.register(DataStorage.getInstance());
	}

	private MonitoringResultServiceImpl createMonitoringResultServiceInstance() {
		try {
			MonitoringResultServiceImpl instance = MonitoringResultServiceImpl.getInstance();
			MonitoringResultService exportedInstance = (MonitoringResultService) UnicastRemoteObject.exportObject(
					instance, Constants.RMI_PORT);
			Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT);
			registry.bind(Constants.RMI_ID, exportedInstance);
			System.out.println("Monitoring result service RMI bound");
			return instance;
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		} catch (AlreadyBoundException e) {
			throw new RuntimeException(e);
		}
	}

}
