<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<p><a href="${pageContext.request.contextPath}/createInstrument">Create an Instrument</a></p>
<p><a href="${pageContext.request.contextPath}/instruments">Show Instruments</a></p>
<p><a href="${pageContext.request.contextPath}/accountInfo">View Account Info here</a></p>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<p><a href="<c:url value='/admin'/>">Admin</a></p>
</sec:authorize>
