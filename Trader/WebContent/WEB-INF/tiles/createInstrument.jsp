<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2>Create New Instrument</h2>

<sf:form id="details" method="post"
	action="${pageContext.request.contextPath}/doCreateInstrument"
	commandName="instrument">

	<table class="formtable">
		<tr>
			<td class="label">Symbol ID:</td>
			<td><sf:input class="control" path="symbolID" name="symbolID"
					type="text" /><br />
				<div class="error">
					<sf:errors path="symbolID"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label">Instrument Description:</td>
			<td><sf:input class="control" path="instrumentDesc" name="instrumentDesc"
					type="text" /><br />
				<div class="error">
					<sf:errors path="instrumentDesc"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label">Price:</td>
			<td><sf:input class="control" path="price" name="price"
					type="text" />
				<div class="error">
					<sf:errors path="price"></sf:errors>
				</div></td>
		</tr>
		<tr><td class="label"> </td><td><input class="control"  value="Create Instrument" type="submit" /></td></tr>
	</table>

</sf:form>
