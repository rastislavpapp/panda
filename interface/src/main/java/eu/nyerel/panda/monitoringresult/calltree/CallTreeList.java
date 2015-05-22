package eu.nyerel.panda.monitoringresult.calltree;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeList implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CallTreeNode> nodes;

    public CallTreeList() {
    }

    public CallTreeList(List<CallTreeNode> nodes) {
        this.nodes = nodes;
    }

    public List<CallTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<CallTreeNode> nodes) {
        this.nodes = nodes;
    }

}
