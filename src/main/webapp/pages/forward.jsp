
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + path + "/";
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    alert("<%=request.getAttribute("msg")%>");
    window.location = "<%=basePath%><%=request.getAttribute("path")%>";
</script>
</body>
</html>
