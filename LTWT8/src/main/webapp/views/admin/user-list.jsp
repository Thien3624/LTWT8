<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<form action="${pageContext.request.contextPath}/admin/user/search" method="get">
    <input type="text" name="keyword" placeholder="Nhập từ khóa tìm kiếm" value="${param.keyword}"/>
    <button type="submit">Tìm kiếm</button>
</form>
<a href="${pageContext.request.contextPath}/admin/user/add">Add User</a>
<table border = "1" width = "100%">
    <tr>
        <th>STT</th>
        <th>UserID</th>
        <th>Username</th>
        <th>Password</th>
        <th>Avatar</th>
        <th>Seller</th>
        <th>Admin</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${listuser}" var="user" varStatus="STT">
        <tr>
            <td>${STT.index + 1}</td>
            <td>${user.userid}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
			
			<td>
				<c:if test="${user.avatar.substring(0, 5) != 'https'}">
					<c:url value="/image?fname=${user.avatar}" var="imgUrl"></c:url>
				</c:if>
				<c:if test="${user.avatar.substring(0, 5) == 'https'}">
					<c:url value="${user.avatar}" var="imgUrl"></c:url>
				</c:if>
				<img height="150" width="200" src="${imgUrl}" />
			</td>
			<td>
			<c:if test="${user.isseller == 1}"><span>TRUE</span></c:if>
			<c:if test="${user.isseller != 1}"><span>FALSE</span></c:if>
			</td>
			<td>
			<c:if test="${user.isadmin == 1}"><span>TRUE</span></c:if>
			<c:if test="${user.isadmin != 1}"><span>FALSE</span></c:if>
			</td>
			<td>
			<c:if test="${user.status == 1 }"><span>Active</span></c:if>
			<c:if test="${user.status != 1 }"><span>Inactive</span></c:if>
			</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/user/edit?id=${user.userid}">Edit</a> | 
                <a href="${pageContext.request.contextPath}/admin/user/delete?id=${user.userid}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
    