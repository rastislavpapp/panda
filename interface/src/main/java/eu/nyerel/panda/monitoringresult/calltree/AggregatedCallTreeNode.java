package eu.nyerel.panda.monitoringresult.calltree;

import java.util.ArrayList;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class AggregatedCallTreeNode extends CallTreeNode {

    private int aggregateCount = 1;

    public AggregatedCallTreeNode() {
    }

    public AggregatedCallTreeNode(CallTreeNode node) {
        setId(node.getId());
        setType(node.getType());
        setDescription(node.getDescription());
        setDuration(node.getDuration());
        setParent(node.getParent());
        setChildren(new ArrayList<CallTreeNode>());

        for (CallTreeNode child : node.getChildren()) {
            getChildren().add(new AggregatedCallTreeNode(child));
        }
    }

    public int getAggregateCount() {
        return aggregateCount;
    }

    public void setAggregateCount(int aggregateCount) {
        this.aggregateCount = aggregateCount;
    }

}
