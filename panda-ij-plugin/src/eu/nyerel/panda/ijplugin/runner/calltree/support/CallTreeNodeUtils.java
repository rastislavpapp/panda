package eu.nyerel.panda.ijplugin.runner.calltree.support;

import eu.nyerel.panda.monitoringresult.calltree.AggregatedCallTreeNode;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNodeUtils {

    public static List<CallTreeNode> sortByDuration(List<CallTreeNode> nodes) {
        nodes.forEach(n -> n.setChildren(sortByDuration(n.getChildren())));
        return nodes.stream()
                .sorted((n1, n2) -> {
                    long t1 = n1.getDuration().getTotal();
                    long t2 = n2.getDuration().getTotal();
                    if (t1 == t2) {
                        String d1 = n1.getDescription();
                        String d2 = n2.getDescription();
                        if (d1.equals(d2)) {
                            return Long.valueOf(n2.getId() - n1.getId()).intValue();
                        } else {
                            return d1.compareTo(d2);
                        }
                    } else {
                        return Long.valueOf(t2 - t1).intValue();
                    }
                }).collect(Collectors.toList());
    }

    public static List<CallTreeNode> flatten(List<CallTreeNode> nodes) {
        List<CallTreeNode> result = new ArrayList<>();
        for (CallTreeNode node : nodes) {
            result.add(node);
            result.addAll(flatten(node.getChildren()));
        }
        return result;
    }

    public static List<CallTreeNode> flatAggregate(List<CallTreeNode> originalNodes) {
        Map<String, AggregatedCallTreeNode> aggregatedNodes = new HashMap<>();
        aggregatedNodes = flatAggregateInternal(originalNodes, aggregatedNodes);
        return new ArrayList<>(aggregatedNodes.values());
    }

    public static List<CallTreeNode> aggregate(List<CallTreeNode> originalNodes) {
        Map<String, AggregatedCallTreeNode> aggregatedNodes = new HashMap<>();
        for (CallTreeNode original : originalNodes) {
            AggregatedCallTreeNode aggregated = aggregatedNodes.get(original.getDescription());
            if (aggregated == null) {
                aggregated = new AggregatedCallTreeNode(original);
                aggregatedNodes.put(aggregated.getDescription(), aggregated);
                aggregated.setChildren(aggregate(original.getChildren()));
            } else {
                aggregateNode(aggregated, original);
                ArrayList<CallTreeNode> childNodes = new ArrayList<>(original.getChildren());
                childNodes.addAll(aggregated.getChildren());
                aggregated.setChildren(aggregate(childNodes));
            }
        }
        return new ArrayList<>(aggregatedNodes.values());
    }

    private static void aggregateNode(AggregatedCallTreeNode sum, CallTreeNode node) {
        sum.setDuration(sum.getDuration().add(node.getDuration()));
        sum.setAggregateCount(sum.getAggregateCount() + 1);
    }

    private static Map<String, AggregatedCallTreeNode> flatAggregateInternal(List<CallTreeNode> originalNodes, Map<String, AggregatedCallTreeNode> aggregateMap) {
        for (CallTreeNode original : originalNodes) {
            AggregatedCallTreeNode aggregated = aggregateMap.get(original.getDescription());
            if (aggregated == null) {
                aggregated = new AggregatedCallTreeNode(original);
                aggregated.setChildren(new ArrayList<>());
                aggregateMap.put(aggregated.getDescription(), aggregated);
            } else {
                aggregateNode(aggregated, original);
            }
            flatAggregateInternal(original.getChildren(), aggregateMap);
        }
        return aggregateMap;
    }

}
