<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf"%>

<%--@elvariable id="callTree" type="java.util.List<eu.nyerel.panda.monitoringresult.calltree.CallTreeNode>"--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Panda App Monitoring</title>
    <link rel="stylesheet" href="<c:url value="/static/css/panda.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/static/css/jstree.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.css"/>"/>

    <script type="text/javascript" src="<c:url value="/static/js/jquery-1.11.1.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/jstree.js"/>"></script>

</head>
<body>

    <c:set var="page" value="call-tree"/>
    <%@include file="../navigation.jspf"%>

    <div class="container">
        <h1 class="page-header">Call Tree</h1>
        <p class="lead">
            <panda:call-tree id="callTree" nodes="${callTree}"/>
        </p>

    </div>

</body>
</html>