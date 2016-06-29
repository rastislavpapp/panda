package eu.nyerel.panda.agent.server;

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

    public void start() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            }, "AgentServer").start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            serverSocket = new ServerSocket(Constants.RMI_PORT);
            Log.debug("Agent server started");
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    OutputStream os = clientSocket.getOutputStream();
                    out = new PrintWriter(os, true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String inputLine;
                    inputLine = in.readLine();
                    Log.debug("Socket: incoming message: " + inputLine);
                    if (inputLine.equals("getCallTree")) {
                        List<CallTreeNode> callTree = MonitoringResultServiceImpl.INSTANCE.getCallTree();
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        oos.writeObject(new CallTreeList(callTree));
                    } else if (inputLine.equals("clearData")) {
                        MonitoringResultServiceImpl.INSTANCE.clear();
                    } else if (inputLine.equals("ping")) {
                        out.println("pong");
                    } else if (inputLine.equals("stop")) {
                        break;
                    } else {
                        throw new IllegalArgumentException("Unknown command from client: " + inputLine);
                    }
                    Log.debug("Socket: finished processing message: " + inputLine);
                } finally {
                    IOUtils.closeQuietly(clientSocket);
                    IOUtils.closeQuietly(out);
                    IOUtils.closeQuietly(in);
                }
            }
            stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(serverSocket);
        }
    }

    private void stop() {
        Log.debug("Agent server stopped");
    }

}
