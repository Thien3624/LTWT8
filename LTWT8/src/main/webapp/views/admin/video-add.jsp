<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Thêm Video</h2>
<form action="${pageContext.request.contextPath}/admin/video/insert" method="post" enctype="multipart/form-data">
    <label for="videoid">Video ID:</label><br>
    <input type="text" id="videoid" name="videoid"><br>
    
    <label for="title">Title:</label><br>
    <input type="text" id="title" name="title"><br>
    
    <label for="description">Description:</label><br>
    <textarea id="description" name="description" rows="4" cols="50"></textarea><br>
    
    <label for="views">Views:</label><br>
    <input type="number" id="views" name="views" value="0"><br>
    
    <label for="poster">Poster Image:</label><br>
    <input type="file" id="poster" name="poster"><br><br>
    
    <label for="category">Category:</label><br>
    <select id="category" name="categoryid">
        <!-- Lấy danh sách category từ backend để hiển thị -->
        <c:forEach items="${listcate}" var="cate">
            <option value="${cate.categoryid}">${cate.categoryname}</option>
        </c:forEach>
    </select><br><br>
    
    <label for="active">Active Status:</label><br>
    <select id="active" name="active">
        <option value="1">Active</option>
        <option value="0">Inactive</option>
    </select><br><br>
    
    <input type="submit" value="Submit">
</form>
