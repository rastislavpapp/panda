package eu.nyerel.panda.ijplugin.runner.calltree.support

import eu.nyerel.panda.ijplugin.model.AggregatedCallTreeNode
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import java.util.ArrayList
import java.util.HashMap
import java.util.stream.Collectors

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object CallTreeNodeUtils {

    fun sortByDuration(nodes: List<CallTreeNode>) {
        val comparator = compareBy<CallTreeNode>(
                { it.duration.total },
                { it.description },
                { it.id })

        for (node in nodes) {
            sortByDuration(node.children)
        }
        nodes.sortedWith(comparator)
    }

    fun flatten(nodes: List<CallTreeNode>): List<CallTreeNode> {
        val result = ArrayList<CallTreeNode>()
        for (node in nodes) {
            result.add(node)
            result.addAll(flatten(node.children))
        }
        return result
    }

    fun flatAggregate(originalNodes: List<CallTreeNode>): List<CallTreeNode> {
        var aggregatedNodes: MutableMap<String, AggregatedCallTreeNode> = HashMap()
        aggregatedNodes = flatAggregateInternal(originalNodes, aggregatedNodes)
        return ArrayList<CallTreeNode>(aggregatedNodes.values)
    }

    fun aggregate(originalNodes: List<CallTreeNode>): List<CallTreeNode> {
        val aggregatedNodes = HashMap<String, AggregatedCallTreeNode>()
        for (original in originalNodes) {
            var aggregated: AggregatedCallTreeNode? = aggregatedNodes[original.description]
            if (aggregated == null) {
                aggregated = AggregatedCallTreeNode(original)
                aggregatedNodes[aggregated.description] = aggregated
                aggregated.children = aggregate(original.children)
            } else {
                aggregateNode(aggregated, original)
                val childNodes = ArrayList(original.children)
                childNodes.addAll(aggregated.children)
                aggregated.children = aggregate(childNodes)
            }
        }
        return ArrayList<CallTreeNode>(aggregatedNodes.values)
    }

    private fun aggregateNode(sum: AggregatedCallTreeNode, node: CallTreeNode) {
        sum.duration = sum.duration.add(node.duration)
        sum.aggregateCount = sum.aggregateCount + 1
    }

    private fun flatAggregateInternal(originalNodes: List<CallTreeNode>, aggregateMap: MutableMap<String, AggregatedCallTreeNode>): MutableMap<String, AggregatedCallTreeNode> {
        for (original in originalNodes) {
            var aggregated: AggregatedCallTreeNode? = aggregateMap[original.description]
            if (aggregated == null) {
                aggregated = AggregatedCallTreeNode(original)
                aggregated.children = ArrayList()
                aggregateMap[aggregated.description] = aggregated
            } else {
                aggregateNode(aggregated, original)
            }
            flatAggregateInternal(original.children, aggregateMap)
        }
        return aggregateMap
    }

}
