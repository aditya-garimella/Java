<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div style="display: inline;float right;">
	<h4>Below are the financial instruments available for purchase</h4>
</div>
<sf:form method="post" action="${pageContext.request.contextPath}/purchaseInstruments" modelAttribute="instrumentForm">
	<table id="instrumentstable" class="instrumentstable" style="padding-bottom: 25px;">
		<tr>
			<td><input type="checkbox" id="checkBoxAll"></td>
			<td style="font-weight:bold;">Symbol ID</td>
			<td style="font-weight:bold;">Instrument Desc</td>
			<td style="font-weight:bold;">Price</td>
			<td style="font-weight:bold;">Quantity</td>
		</tr>

		<c:forEach var="instrument" items="${instrumentForm.instrumentList}" varStatus="status">
			<tr>
				<td><sf:checkbox path="instrumentList[${status.index}].check"
				class="chkCheckBoxId"/></td>
				<td>${instrument.symbolID}<sf:hidden path="instrumentList[${status.index}].symbolID"/></td>
                <td>${instrument.instrumentDesc}<sf:hidden path="instrumentList[${status.index}].instrumentDesc"/></td>
                <td>${instrument.price}<sf:hidden path="instrumentList[${status.index}].price"/></td>
                <td><sf:input path="instrumentList[${status.index}].quantity" type="text" style="width: 25px;"/></td>
			</tr>
		</c:forEach>
	</table>
	<tr></tr>
	<input class="control" value="Buy Selected" type="submit" />
</sf:form>
