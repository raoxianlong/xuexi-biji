
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + path + "/";
%>

<html>
<head>
    <title>员工添加</title>
</head>
<body>
<script>

</script>

<h2>员工信息添加</h2>
<form action="<%=basePath%>/admin/emp/add.action" method="post" enctype="multipart/form-data" >
    姓名：<input type="text" name="emp.name" /><br>
    年龄：<input type="text" name="emp.age" /><br>
    <input type="submit" value="提交" />
    <input type="reset" value="重置" />
</form>
</body>
</html>
