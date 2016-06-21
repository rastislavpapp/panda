package eu.nyerel.panda.ijplugin.runner.calltree;

import eu.nyerel.panda.monitoringresult.calltree.AggregatedCallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeAggregator {

    public List<CallTreeNode> aggregate(List<CallTreeNode> nodes) {
        Map<String, AggregatedCallTreeNode> aggregatedNodes = new HashMap<String, AggregatedCallTreeNode>();
        for (CallTreeNode node : nodes) {
            AggregatedCallTreeNode aggregated = aggregatedNodes.get(node.getDescription());
            if (aggregated == null) {
                aggregated = new AggregatedCallTreeNode(node);
                aggregatedNodes.put(aggregated.getDescription(), aggregated);
            } else {
                aggregateNode(aggregated, node);
                ArrayList<CallTreeNode> childNodes = new ArrayList<CallTreeNode>(node.getChildren());
                childNodes.addAll(aggregated.getChildren());
                aggregated.setChildren(aggregate(childNodes));
            }

        }
        return new ArrayList<CallTreeNode>(aggregatedNodes.values());
    }

    private void aggregateNode(AggregatedCallTreeNode sum, CallTreeNode node) {
        sum.setDuration(sum.getDuration().add(node.getDuration()));
        sum.setAggregateCount(sum.getAggregateCount() + 1);
    }

}
