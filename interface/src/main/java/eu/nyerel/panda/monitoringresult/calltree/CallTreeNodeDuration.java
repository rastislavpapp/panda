package eu.nyerel.panda.monitoringresult.calltree;

import java.io.Serializable;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CallTreeNodeDuration implements Serializable {

    private long total;
    private long self;
    private long children;
    private double percentage;

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
