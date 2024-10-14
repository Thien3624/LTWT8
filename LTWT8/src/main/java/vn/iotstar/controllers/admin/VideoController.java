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
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IVideoService;
import vn.iotstar.services.impl.CategoryService;
import vn.iotstar.services.impl.VideoService;
import vn.iotstar.utils.Constant;

@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1 MB
	    maxFileSize = 1024 * 1024 * 10,  // 10 MB
	    maxRequestSize = 1024 * 1024 * 50 // 50 MB
	)
@WebServlet(urlPatterns = {"/admin/videos", 
							"/admin/video/add", 
							"/admin/video/insert", 
							"/admin/video/edit", 
							"/admin/video/update",
							"/admin/video/delete",
							"/admin/video/search"})
public class VideoController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public IVideoService vidService = new VideoService();
	public ICategoryService cateService = new CategoryService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if(url.contains("videos")) {
			List<Video> list = vidService.findAll();
			req.setAttribute("listvideo", list);
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
		}else if(url.contains("add")) {
			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
		}else if(url.contains("edit")) {
			String videoid = req.getParameter("id");
			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			System.out.println("Received Video ID: " + videoid);
			if (videoid == null || videoid.isEmpty()) {
	            throw new IllegalArgumentException("Video ID is required for editing.");
	        }

	        Video video = vidService.findById(videoid);
	        if (video == null) {
	            throw new IllegalArgumentException("Video not found for ID: " + videoid);
	        }
			req.setAttribute("vid", video);
			req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
		}else if (url.contains("delete")) {
			String videoid = req.getParameter("id");
			try {
				vidService.delete(videoid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}else if (url.contains("search")) {
			String keyword = req.getParameter("keyword");
			if(keyword != null) {
				List<Video> list = vidService.findByTitle(keyword);
				req.setAttribute("listvideo", list);
			}else {
				List<Video> list = vidService.findAll();
				req.setAttribute("listvideo", list);
			}
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if(url.contains("insert")) {
			Video video = new Video();
			String videoid = req.getParameter("videoid");
			int active = Integer.parseInt(req.getParameter("active"));
			String description = req.getParameter("description");
			int views = Integer.parseInt(req.getParameter("views"));
			String title = req.getParameter("title");
			int categoryid =  Integer.parseInt(req.getParameter("categoryid"));

			video.setVideoid(videoid);
			video.setDescription(description);
			video.setViews(views);
			video.setTitle(title);
			video.setActive(active);
			Category cate = cateService.findById(categoryid);
			video.setCategory(cate);
			//Upload Image
			String fname = "";
			String uploadPath = Constant.DIR; //Lấy đường dẫn để lưu ảnh
			File uploadDir = new File(uploadPath);
			//Kiểm tra nếu thư mục chưa tồn tại thì tạo thư mục
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("poster"); //Truyền tham số trên views xuống
				if(part.getSize()>0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					//Đổi tên file
					int index =  filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis()+ "." + ext;
					
					//Up
					part.write(uploadPath + "/" + fname);
					//Ghi tên file vào data
					video.setPoster(fname);
				}else {
					video.setPoster("avatar.png");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			vidService.insert(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}else if(url.contains("update")) {
			
			Video video = new Video();
			String videoid = req.getParameter("videoid");
			int active = Integer.parseInt(req.getParameter("active"));
			String description = req.getParameter("description");
			int views = Integer.parseInt(req.getParameter("views"));
			String title = req.getParameter("title");
			int categoryid =  Integer.parseInt(req.getParameter("categoryid"));
			
			video.setVideoid(videoid);
			video.setDescription(description);
			video.setViews(views);
			video.setTitle(title);
			video.setActive(active);
			Category cate = cateService.findById(categoryid);
			video.setCategory(cate);
			//Lưu hình cũ
			Video videoold = vidService.findById(videoid);
			String fileold = videoold.getPoster();
			//Upload Image
			String fname = "";
			String uploadPath = Constant.DIR; //Lấy đường dẫn để lưu ảnh
			File uploadDir = new File(uploadPath);
			//Kiểm tra nếu thư mục chưa tồn tại thì tạo thư mục
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("poster"); //Truyền tham số trên views xuống
				if(part.getSize()>0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					//Đổi tên file
					int index =  filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis()+ "." + ext;
					
					//Up
					part.write(uploadPath + "/" + fname);
					//Ghi tên file vào data
					video.setPoster(fname);
				}else {
					video.setPoster(fileold); //Lấy lại ảnh cũ
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			vidService.update(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}
}
