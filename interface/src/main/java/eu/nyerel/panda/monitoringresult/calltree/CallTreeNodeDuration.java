package eu.nyerel.panda.monitoringresult.calltree;

import java.io.Serializable;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNodeDuration implements Serializable {

    private static final long serialVersionUID = 1L;

    private long total;
    private long self;
    private long children;
    private double percentage;

    public CallTreeNodeDuration add(CallTreeNodeDuration duration) {
        CallTreeNodeDuration sum = new CallTreeNodeDuration();
        sum.setTotal(total + duration.total);
        sum.setSelf(self + duration.self);
        sum.setChildren(children + duration.children);
        sum.setPercentage(percentage + duration.percentage);
        return sum;
    }

    public CallTreeNodeDuration divide(int number) {
        CallTreeNodeDuration divided = new CallTreeNodeDuration();
        divided.setTotal(total / number);
        divided.setSelf(self / number);
        divided.setChildren(children / number);
        divided.setPercentage(percentage / number);
        return divided;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSelf() {
        return self;
    }

    public void setSelf(long self) {
        this.self = self;
    }

    public long getChildren() {
        return children;
    }

    public void setChildren(long children) {
        this.children = children;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

}
