<%@taglib prefix="panda" uri="http://nyerel.eu/panda" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="node" type="eu.nyerel.panda.model.CallTreeNode" required="true" %>

<li data-call-tree-node-id="${node.id}">

    <div class="node-row">
        <div class="node-description"><c:out value="${node.description}"/></div>
        <div class="node-duration">
            <span><c:out value="${node.selfDuration}"/>ms</span>
            <span><c:out value="${node.duration}"/>ms</span>
        </div>
    </div>

    <c:if test="${!empty node.children}">
        <ul>
            <c:forEach items="${node.children}" var="child">
                <panda:call-tree-node node="${child}"/>
            </c:forEach>
        </ul>
    </c:if>

</li>