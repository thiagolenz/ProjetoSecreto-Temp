package com.redfire.nutrieduc.commonsvc.svc.dao;

import com.redfire.nutrieduc.entities.common.AbstractEntity;

/**
 * Generic DAO interface
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 * @param <T> Any class that extends {@link AbstractEntity}
 */
public interface GenericDAO <T extends AbstractEntity> {
	void insert (T obj);

	void remove (T obj);

	void update (T obj, Long id);

	T findById(Class<T>clazz, Long id);
}
