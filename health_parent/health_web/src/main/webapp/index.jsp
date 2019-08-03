<%--
  Created by IntelliJ IDEA.
  User: Eric
  Date: 2019/7/29
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}
</body>
</html>
