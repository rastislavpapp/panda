package eu.nyerel.panda.ijplugin.runner.calltree.support

import eu.nyerel.panda.ijplugin.model.AggregatedCallTreeNode
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object CallTreeNodeUtils {

    val PROXY_CLASS_FILTER: (CallTreeNode) -> Boolean = {
        it.description.contains("\$\$EnhancerBySpringCGLIB") || it.description.contains("\$\$FastClassBySpringCGLIB")
    }

    private val NODE_DURATION_COMPARATOR = compareByDescending<CallTreeNode> { it.duration.total }
            .thenBy { it.description }.thenBy { it.id }

    fun sortByDuration(nodes: List<CallTreeNode>): List<CallTreeNode> {
        for (node in nodes) {
            node.children = sortByDuration(node.children)
        }
        return nodes.sortedWith(NODE_DURATION_COMPARATOR)
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
        var aggregatedNodes: MutableMap<String, AggregatedCallTreeNode> = LinkedHashMap()
        aggregatedNodes = flatAggregateInternal(originalNodes, aggregatedNodes)
        return ArrayList<CallTreeNode>(aggregatedNodes.values)
    }

    fun aggregate(originalNodes: List<CallTreeNode>): List<CallTreeNode> {
        val nodesByDescription = LinkedHashMap<String, MutableList<CallTreeNode>>()
        for (node in originalNodes) {
            var list = nodesByDescription[node.description]
            if (list == null) {
                list = mutableListOf()
                nodesByDescription[node.description] = list
            }
            list.add(node)
        }

        val aggregatedNodes = mutableListOf<CallTreeNode>()
        for (desc in nodesByDescription.keys) {
            val nodes = ArrayList<CallTreeNode>(nodesByDescription[desc])
            val aggregatedNode = aggregateNodesIntoOne(nodes)
            aggregatedNodes.add(aggregatedNode)
        }

        return aggregatedNodes
    }

    private fun aggregateNodesIntoOne(nodes: List<CallTreeNode>): AggregatedCallTreeNode {
        assert(!nodes.isEmpty())
        val aggregatedNode = AggregatedCallTreeNode(nodes.first())

        val children = mutableListOf<CallTreeNode>()
        for (node in nodes) {
            aggregateNodeValues(aggregatedNode, node)
            children.addAll(node.children)
        }
        aggregatedNode.children = aggregate(children)

        return aggregatedNode
    }

    private fun getNodesAtLevel(node: CallTreeNode, level: Int): List<CallTreeNode> {
        return if (level == 0) {
            listOf(node)
        } else if (level == 1) {
            node.children
        } else {
            if (node.children.isEmpty()) {
                emptyList()
            } else {
                val nodeList = mutableListOf<CallTreeNode>()
                node.children.forEach {
                    nodeList.addAll(getNodesAtLevel(it, level - 1))
                }
                nodeList
            }
        }
    }

    fun exclude(nodes: List<CallTreeNode>, exclusionFilter: (CallTreeNode) -> Boolean): List<CallTreeNode> {
        val newNodes = mutableListOf<CallTreeNode>()
        for (node in nodes) {
            val children = exclude(node.children, exclusionFilter)
            if (exclusionFilter(node)) {
                newNodes.addAll(children)
            } else {
                node.children = children
                newNodes.add(node)
            }
        }
        return newNodes
    }

    private fun aggregateNodeValues(sum: AggregatedCallTreeNode, node: CallTreeNode) {
        sum.duration = sum.duration.add(node.duration)
        sum.aggregateCount += if (node is AggregatedCallTreeNode) node.aggregateCount else 1
    }

    private fun flatAggregateInternal(originalNodes: List<CallTreeNode>, aggregateMap: MutableMap<String, AggregatedCallTreeNode>): MutableMap<String, AggregatedCallTreeNode> {
        for (original in originalNodes) {
            var aggregated: AggregatedCallTreeNode? = aggregateMap[original.description]
            if (aggregated == null) {
                aggregated = AggregatedCallTreeNode(original)
                aggregateMap[aggregated.description] = aggregated
            }
            aggregateNodeValues(aggregated, original)
            flatAggregateInternal(original.children, aggregateMap)
        }
        return aggregateMap
    }

}
