<%@taglib prefix="panda" uri="http://nyerel.eu/panda" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="nodes" type="java.util.List<eu.nyerel.panda.model.CallTreeNode>" required="true" %>
<%@attribute name="id" type="java.lang.String" required="true" %>
<%@attribute name="cssClass" type="java.lang.String" required="false"%>

<script type="text/javascript">

    $(function() {
        $("#${id}").jstree({
            plugins: [ "state", "wholerow" ]
        });
    });

</script>

<div id="${id}">
    <ul>
        <c:forEach items="${nodes}" var="node">
            <panda:call-tree-node node="${node}"/>
        </c:forEach>
    </ul>
</div>