<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<h2>Add User</h2>
<form action="${pageContext.request.contextPath}/admin/user/insert" method="post" enctype="multipart/form-data">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br><br>
    
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br><br>
    
    <c:if test="${not empty error}">
        <span style="color: red;">${error}</span><br><br>
    </c:if>
    
    <label for="avatar">Avatar:</label><br>
    <input type="file" id="avatar" name="avatar"><br><br>
    
    <label for="isseller">Is Seller</label><br>
    <select id="isseller" name="isseller">
        <option value="1">True</option>
        <option value="0">False</option>
    </select><br><br>
    
    <label for="isadmin">Is Admin</label><br>
    <select id="isadmin" name="isadmin">
        <option value="1">True</option>
        <option value="0">False</option>
    </select><br><br>
    
    <label for="status">Status:</label><br>
    <select id="status" name="status">
        <option value="1">Active</option>
        <option value="0">Inactive</option>
    </select><br><br> 
    <input type="submit" value="Submit">
</form>
