package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Video;

public interface IVideoDao {
	int count();

	List<Video> findAll(int page, int pagesize);

	List<Video> findByTitle(String videotitle);

	List<Video> findAll();

	Video findById(String vidid);

	void delete(String vidid) throws Exception;

	void update(Video video);

	void insert(Video video);
}
