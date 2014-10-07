<%@taglib prefix="panda" uri="http://nyerel.eu/panda" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="nodes" type="java.util.List<eu.nyerel.panda.monitoringresult.calltree.CallTreeNode>" required="true" %>
<%@attribute name="id" type="java.lang.String" required="true" %>
<%@attribute name="cssClass" type="java.lang.String" required="false"%>

<script type="text/javascript">

    $(function() {
        $("#${id}").jstree({
            plugins: [ "state", "wholerow" ],
            core: {
                themes: {
                    icons: false
                }
            }
        });
    });

</script>

<div id="${id}" class="call-tree">
    <ul>
        <c:forEach items="${nodes}" var="node">
            <panda:call-tree-node node="${node}"/>
        </c:forEach>
    </ul>
</div>