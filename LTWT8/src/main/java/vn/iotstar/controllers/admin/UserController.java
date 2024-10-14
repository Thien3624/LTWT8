package vn.iotstar.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserService;
import vn.iotstar.utils.Constant;
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1 MB
	    maxFileSize = 1024 * 1024 * 10,  // 10 MB
	    maxRequestSize = 1024 * 1024 * 50 // 50 MB
	)
@WebServlet(urlPatterns = {"/admin/users", 
		"/admin/user/add", 
		"/admin/user/insert", 
		"/admin/user/edit", 
		"/admin/user/update",
		"/admin/user/delete",
		"/admin/user/search"})
public class UserController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public IUserService userService= new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if(url.contains("users")) {
			List<User> list = userService.findAll();
			req.setAttribute("listuser", list);
			req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
		}else if(url.contains("add")) {
			req.getRequestDispatcher("/views/admin/user-add.jsp").forward(req, resp);
		}else if(url.contains("edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			User user = userService.findById(id);
			
			req.setAttribute("usr", user);
			req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
		}else if (url.contains("delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				userService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/users");
		}else if (url.contains("search")) {
			String keyword = req.getParameter("keyword");
			if(keyword != null) {
				List<User> list = userService.findByUserName(keyword);
				req.setAttribute("listuser", list);
			}else {
				List<User> list = userService.findAll();
				req.setAttribute("listuser", list);
			}
			req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if(url.contains("insert")) {
			User user = new User();
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			int isseller = Integer.parseInt(req.getParameter("isseller"));
			int isadmin = Integer.parseInt(req.getParameter("isadmin"));
			int status = Integer.parseInt(req.getParameter("status"));
			
		    user.setIsseller(isseller);
		    user.setIsadmin(isadmin);
		    user.setStatus(status);
			user.setUsername(username);
			user.setPassword(password);
			
			
			
			//Upload Image
			String fname = "";
			String uploadPath = Constant.DIR; //Lấy đường dẫn để lưu ảnh
			File uploadDir = new File(uploadPath);
			//Kiểm tra nếu thư mục chưa tồn tại thì tạo thư mục
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("avatar"); //Truyền tham số trên views xuống
				if(part.getSize()>0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					//Đổi tên file
					int index =  filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis()+ "." + ext;
					
					//Up
					part.write(uploadPath + "/" + fname);
					//Ghi tên file vào data
					user.setAvatar(fname);
				}else {
					user.setAvatar("avatar.png");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			userService.insert(user);
			resp.sendRedirect(req.getContextPath() + "/admin/users");
		}else if(url.contains("update")) {
			
			int userid = Integer.parseInt(req.getParameter("userid"));
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			int status = Integer.parseInt(req.getParameter("status"));
			int isseller = Integer.parseInt(req.getParameter("isseller"));
			int isadmin = Integer.parseInt(req.getParameter("isadmin"));
			
			User user = new User();
			user.setUserid(userid);
			user.setUsername(username);
			user.setPassword(password);
			user.setIsseller(isseller);
			user.setIsadmin(isadmin);
			user.setStatus(status);
			//Lưu hình cũ
			User userold = userService.findById(userid);
			String fileold = userold.getAvatar();
			//Upload Image
			String fname = "";
			String uploadPath = Constant.DIR; 
			//Lấy đường dẫn để lưu ảnh
			File uploadDir = new File(uploadPath);
			//Kiểm tra nếu thư mục chưa tồn tại thì tạo thư mục
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("avatar"); //Truyền tham số trên views xuống
				if(part.getSize()>0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					//Đổi tên file
					int index =  filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis()+ "." + ext;
					
					//Up
					part.write(uploadPath + "/" + fname);
					//Ghi tên file vào data
					user.setAvatar(fname);
				}else {
					user.setAvatar(fileold); //Lấy lại ảnh cũ
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			userService.update(user);
			resp.sendRedirect(req.getContextPath() + "/admin/users");
		}
	}

}
