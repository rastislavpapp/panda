package eu.nyerel.panda.agent.server;

import eu.nyerel.dolphin.core.server.DolphinServer;
import eu.nyerel.panda.Constants;
import eu.nyerel.panda.agent.monitoringresult.MonitoringResultServiceImpl;
import eu.nyerel.panda.agent.util.IOUtils;
import eu.nyerel.panda.agent.util.Log;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeList;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public enum AgentServer {

    INSTANCE;

    private DolphinServer dolphinServer = new DolphinServer();

    public void start() {
        dolphinServer.export(MonitoringResultServiceImpl.INSTANCE);
        dolphinServer.start();
    }

}
