package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.entity.User;

public class UserDao implements IUserDao{

    @Override
    public int count() {
        EntityManager enma = JPAConfig.getEntityManager();
		String jpql = "SELECT count(c) FROM User c";
		Query query = enma.createQuery(jpql);
		
		return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public List<User> findAll(int page, int pagesize) {
        EntityManager enma = JPAConfig.getEntityManager();
		TypedQuery<User> query = enma.createNamedQuery("User.findAll", User.class);
		query.setFirstResult(page*pagesize);
		query.setMaxResults(pagesize);
		
		return query.getResultList();
    }

    @Override
    public List<User> findByUserName(String usrname) {
        EntityManager enma = JPAConfig.getEntityManager();
		String jpql = "SELECT c FROM User c WHERE c.username like :usrname";
		TypedQuery<User> query = enma.createQuery(jpql, User.class);
		query.setParameter("usrname", "%" + usrname + "%");
		
		return query.getResultList();
    }

    @Override
    public List<User> findAll() {
        EntityManager enma = JPAConfig.getEntityManager();
		TypedQuery<User> query = enma.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
    }

    @Override
    public User findById(int userid) {
        EntityManager enma = JPAConfig.getEntityManager();
		User user = enma.find(User.class, userid);
		return user;
    }

    @Override
    public void delete(int userid) throws Exception {
        EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			User user = enma.find(User.class,userid);
			if(user != null) {
				//gọi phương thức crud
				enma.remove(user);
			}else {
				throw new Exception("Không tìm thấy");
			}
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		}finally {
			enma.close();
		}
    }

    @Override
    public void update(User user) {
        EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			//gọi phương thức crud
			enma.merge(user);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		}finally {
			enma.close();
		}

    }

    @Override
    public void insert(User user) {
        EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			//gọi phương thức crud
			enma.persist(user);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		}finally {
			enma.close();
		}
    }

}
