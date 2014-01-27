<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

${xmlRootStartTag}
<c:forEach items="${itemList}" var="item">
    ${item}
</c:forEach>
${xmlRootEndTag}