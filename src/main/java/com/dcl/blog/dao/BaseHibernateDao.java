package com.dcl.blog.dao;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * 所有dao的基础类
 * 
 * @author Administrator
 * 
 */
public class BaseHibernateDao extends DaoSupport {
	private static final Logger logger = LoggerFactory.getLogger(BaseHibernateDao.class);
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;

	// 获取sessionfactory
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 依赖注入
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		logger.info("注入sessionFactory");
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = createHibernateTemplate(sessionFactory);
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public Session getSession() {
		if (this.sessionFactory == null) {
			throw new HibernateException(
					"Session Create Fail,SessionFactory is null!");
		}
		return this.sessionFactory.getCurrentSession();
	}

	protected HibernateTemplate createHibernateTemplate(
			SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (this.hibernateTemplate == null) {
			throw new IllegalArgumentException(
					"'sessionFactory' or 'hibernateTemplate' is required");
		}
	}

	protected final Session getSession(boolean allowCreate)
			throws DataAccessResourceFailureException, IllegalStateException {
		return (!allowCreate ? SessionFactoryUtils.getSession(
				getSessionFactory(), false) : SessionFactoryUtils.getSession(
				getSessionFactory(),

				this.hibernateTemplate.getEntityInterceptor(),
				this.hibernateTemplate.getJdbcExceptionTranslator()));
	}

	protected final DataAccessException convertHibernateAccessException(
			HibernateException ex) {
		return this.hibernateTemplate.convertHibernateAccessException(ex);
	}

	/**
	 * 释放session
	 * 
	 * @param session
	 */
	protected final void releaseSession(Session session) {
		SessionFactoryUtils.releaseSession(session, getSessionFactory());
		if (null != session)
			session = null;
	}

	public final void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public final HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
}
