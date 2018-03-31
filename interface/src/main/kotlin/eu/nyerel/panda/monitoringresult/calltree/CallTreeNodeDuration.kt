package eu.nyerel.panda.monitoringresult.calltree

import java.io.Serializable

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class CallTreeNodeDuration : Serializable {

    var total: Long = 0
    var self: Long = 0
    var children: Long = 0
    var percentage: Double = 0.toDouble()

    fun add(duration: CallTreeNodeDuration): CallTreeNodeDuration {
        val sum = CallTreeNodeDuration()
        sum.total = total + duration.total
        sum.self = self + duration.self
        sum.children = children + duration.children
        sum.percentage = percentage + duration.percentage
        return sum
    }

    fun divide(number: Int): CallTreeNodeDuration {
        val divided = CallTreeNodeDuration()
        if (number > 0) {
            divided.total = total / number
            divided.self = self / number
            divided.children = children / number
            divided.percentage = percentage / number
        }
        return divided
    }

    override fun toString(): String {
        return "total: " + formatTotal() + ", self: " + formatSelf()
    }

    fun formatTotal(): String {
        return total.toString() + " ms (" + formatPercentage(percentage) + ")"
    }

    private fun formatPercentage(percentage: Double): String {
        return Math.round(Math.round(100 * percentage).toFloat()).toString() + "%"
    }

    fun formatSelf(): String {
        val percentage = if (total == 0L) 0 else self / total
        return self.toString() + " ms (" + formatPercentage(percentage.toDouble()) + ")"
    }

    companion object {

        private const val serialVersionUID = 1L
    }

}
