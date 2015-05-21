package eu.nyerel.panda.monitoringresult.calltree;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNode implements Serializable {

    private long id;
    private CallTreeNodeType type;
    private CallTreeNodeDuration duration;
    private String description;
    private CallTreeNode parent;
    private List<CallTreeNode> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CallTreeNodeType getType() {
        return type;
    }

    public void setType(CallTreeNodeType type) {
        this.type = type;
    }

    public CallTreeNodeDuration getDuration() {
        return duration;
    }

    public void setDuration(CallTreeNodeDuration duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CallTreeNode getParent() {
        return parent;
    }

    public void setParent(CallTreeNode parent) {
        this.parent = parent;
    }

    public List<CallTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<CallTreeNode> children) {
        this.children = children;
    }

}
