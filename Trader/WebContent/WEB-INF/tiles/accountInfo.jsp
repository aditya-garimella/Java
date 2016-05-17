<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<table class="formtable">

<tr><td>First Name: <c:out value="${user.firstName}"></c:out></td></tr>

<tr><td>Last Name: <c:out value="${user.lastName}"></c:out></td></tr>

<tr><td>Email Name: <c:out value="${user.email}"></c:out></td></tr>

<tr><td>Balance: <c:out value="${user.initialBalance}"></c:out></td></tr>
   
</table>
<br/>
<sf:form method="post" action="${pageContext.request.contextPath}/purchaseInstruments" modelAttribute="instrumentForm">
	<table id="instrumentstable" class="instrumentstable">
		<tr>
			<td style="font-weight:bold;">Symbol ID</td>
			<td style="font-weight:bold;">Instrument Desc</td>
			<td style="font-weight:bold;">Price</td>
			<td style="font-weight:bold;">Quantity</td>
		</tr>

		<c:forEach var="instrument" items="${instruments}" varStatus="status">
			<tr>
				<td>${instrument.symbolID}</td>
                <td>${instrument.instrumentDesc}</td>
                <td>${instrument.price}</td>
                <td>${instrument.quantity}</td>
			</tr>
		</c:forEach>
	</table>
</sf:form>

<a target="_blank" href="${pageContext.request.contextPath}/downloadAccountInfo">
    <button style="color:green;">Download AccountInfo</button>
</a>