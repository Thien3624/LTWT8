<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<form action="${pageContext.request.contextPath}/admin/user/update" method="post" enctype="multipart/form-data">
 <input type="hidden" id="userid" name="userid" value="${usr.userid}">
    <label for="username">Tên Đăng Nhập:</label><br>
    <input type="text" id="username" name="username" value = "${usr.username }"><br><br>
    
    <label for="password">Mật Khẩu:</label><br>
    <input type="password" id="password" name="password" value = "${usr.password }"><br><br>
    
     <label for="images">Images:</label><br>
  	<c:if test="${usr.avatar.substring(0, 5) != 'https'}">
		<c:url value="/image?fname=${usr.avatar}" var="imgUrl"></c:url>
	</c:if>
	<c:if test="${usr.avatar.substring(0, 5) == 'https'}">
		<c:url value="${usr.avatar}" var="imgUrl"></c:url>
	</c:if>
	<img height="150" width="200" src="${imgUrl}" /> <br>
  <input type="file" id="avatar" name="avatar" value = "${usr.avatar}"><br><br>
    
    <label for="isseller">Người Bán:</label><br>
	<select id="isseller" name="isseller">
	    <option value="1" <c:if test="${usr.isseller == 1}">selected</c:if>>True</option>
	    <option value="0" <c:if test="${usr.isseller == 0}">selected</c:if>>False</option>
	</select><br><br>
    
    <label for="isadmin">Quản Trị Viên:</label><br>
	<select id="isadmin" name="isadmin">
	    <option value="1" <c:if test="${usr.isadmin == 1}">selected</c:if>>True</option>
	    <option value="0" <c:if test="${usr.isadmin == 0}">selected</c:if>>False</option>
	</select><br><br>
    
    <label for="status">Trạng Thái:</label><br>
	<select id="status" name="status">
	    <option value="1" <c:if test="${usr.status == 1}">selected</c:if>>Active</option>
	    <option value="0" <c:if test="${usr.status == 0}">selected</c:if>>Inactive</option>
	</select><br><br>

    
    <input type="submit" value="Submit">
</form>
