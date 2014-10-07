x<%@taglib prefix="panda" uri="http://nyerel.eu/panda" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="node" type="eu.nyerel.panda.monitoringresult.calltree.CallTreeNode" required="true" %>

<li data-call-tree-node-id="${node.id}">

    <div class="node-row">
        <c:choose>
            <c:when test="${node.duration.total > 0}">
                <c:set var="durationPercentageInt"><fmt:formatNumber value="${node.duration.percentage * 100}" maxIntegerDigits="3" maxFractionDigits="0"/></c:set>
                <c:set var="durationWidth" value="${durationPercentageInt}"/>
                <c:set var="childrenDurationWidth"><fmt:formatNumber value="${node.duration.children * durationWidth / node.duration.total}" maxIntegerDigits="3" maxFractionDigits="0"/></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="durationWidth" value="0"/>
                <c:set var="childrenDurationWidth" value="0"/>
            </c:otherwise>
        </c:choose>


        <div class="node-duration-wrapper">
            <div class="duration-box" style="width: ${durationWidth}px" title="Self time: ${node.duration.self}ms, internal calls: ${node.duration.children}ms">
                <div class="duration-box-children" style="width: ${childrenDurationWidth}px"></div>
                <div class="duration-box-self"></div>
            </div>
            <div class="duration-text">${durationPercentageInt}% - <fmt:formatNumber value="${node.duration.total}"/>ms</div>
        </div>
        <div class="node-description"><c:out value="${node.description}"/></div>
    </div>

    <c:if test="${!empty node.children}">
        <ul>
            <c:forEach items="${node.children}" var="child">
                <panda:call-tree-node node="${child}"/>
            </c:forEach>
        </ul>
    </c:if>

</li>