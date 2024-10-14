<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form action="${pageContext.request.contextPath}/admin/video/search" method="get">
    <input type="text" name="keyword" placeholder="Nhập từ khóa tìm kiếm" value="${param.keyword}"/>
    <button type="submit">Tìm kiếm</button>
</form>
<a href="${pageContext.request.contextPath}/admin/video/add">Add Category</a>
<table border = "1" width = "100%">
	<tr>
		<th>STT</th>
		<th>VideoID</th>
		<th>Poster</th>
		<th>Title</th>
		<th>Description</th>
		<th>Views</th>
		<th>Category</th>
		<th>Active</th>
		<th>Action</th>
	</tr>
	<c:forEach items="${listvideo}" var="vid" varStatus="STT">
		<tr>
			<td>${STT.index+1}</td>
			<td>${vid.videoid}</td>
			<td>
				<c:if test="${vid.poster.substring(0, 5) != 'https'}">
					<c:url value="/image?fname=${vid.poster}" var="imgUrl"></c:url>
				</c:if>
				<c:if test="${vid.poster.substring(0, 5) == 'https'}">
					<c:url value="${vid.poster}" var="imgUrl"></c:url>
				</c:if>
				<img height="150" width="200" src="${imgUrl}" />
			</td>
			
			<td>${vid.title}</td>
			<td>${vid.description}</td>
			<td>${vid.views}</td>
			<td>${vid.category.categoryname}</td>

			<td>
			<c:if test="${vid.active == 1}"><span>Hoạt động</span></c:if>
			<c:if test="${vid.active != 1}"><span>Khóa</span></c:if>
			</td>
			<td><a
				href="<c:url value='/admin/video/edit?id=${vid.videoid }'/>">Sửa</a> | <a
				href="<c:url value='/admin/video/delete?id=${vid.videoid }'/>">Xóa</a>
			</td>
		</tr>
	</c:forEach>
</table>