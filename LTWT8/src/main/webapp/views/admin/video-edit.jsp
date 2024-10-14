<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="jakarta.tags.core"%>

<form action="${pageContext.request.contextPath}/admin/video/update" method="post" enctype="multipart/form-data">
    <!-- Hidden field for videoid -->
    <input type="text" id="videoid" name="videoid" value="${vid.videoid}" hidden="hidden"><br>

    <!-- Input field for title -->
    <label for="title">Title:</label><br>
    <input type="text" id="title" name="title" value="${vid.title}"><br>

    <!-- Input field for description -->
    <label for="description">Description:</label><br>
    <textarea id="description" name="description" rows="4" cols="50"></textarea><br>

    <!-- Input field for views -->
    <label for="views">Views:</label><br>
    <input type="number" id="views" name="views" value="${vid.views}"><br>

    <!-- Poster image display -->
    <label for="poster">Poster:</label><br>
    <c:if test="${vid.poster.substring(0, 5) != 'https'}">
        <c:url value="/image?fname=${vid.poster}" var="imgUrl"></c:url>
    </c:if>
    <c:if test="${vid.poster.substring(0, 5) == 'https'}">
        <c:url value="${vid.poster}" var="imgUrl"></c:url>
    </c:if>
    <img height="150" width="200" src="${imgUrl}" /><br>

    <!-- Input field for new poster upload -->
    <input type="file" id="poster" name="poster"><br><br>
    
	<label for="category">Category:</label><br>
    <select id="category" name="categoryid" required>
        <c:forEach items="${listcate}" var="cate">
            <option value="${cate.categoryid}">${cate.categoryname}</option>
        </c:forEach>
    </select><br><br>
    <label for="active">Active Status:</label><br>
	<select id="active" name="active">
	    <option value="1" <c:if test="${vid.active == 1}">selected</c:if>>Active</option>
	    <option value="0" <c:if test="${vid.active == 0}">selected</c:if>>Inactive</option>
	</select><br><br>
    <!-- Submit button -->
    <input type="submit" value="Submit">
</form>
