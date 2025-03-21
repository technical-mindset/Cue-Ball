package com.cueballdb.repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class AbstractPersistenceManager<F extends Serializable>   {


    @PersistenceContext
    EntityManager entityManager;

    Class<F> persistentClass;
    public AbstractPersistenceManager() {

        Type t = getClass().getGenericSuperclass();
        Type arg;

        if (t instanceof ParameterizedType) {
            arg = ((ParameterizedType) t).getActualTypeArguments()[0];
        } else if (t instanceof Class) {
            arg = ((ParameterizedType) ((Class<?>) t).getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("Can not handle type construction for '" + getClass() + "'!");
        }

        if (arg instanceof Class) {
            this.persistentClass = (Class<F>) arg;
        } else if (arg instanceof ParameterizedType) {
            this.persistentClass = (Class<F>) ((ParameterizedType) arg).getRawType();
        } else {
            throw new RuntimeException("Problem dtermining generic class for '" + getClass() + "'! ");
        }

    }

    public List<F> query(String sql, Map<String, Object> parameters) throws PersistenceException {

        try {
            Query query= entityManager.createQuery(sql);

            if (null != parameters) {
                for (String key : parameters.keySet()) {
                    Object param = parameters.get(key);
                    if (param instanceof Collection) {
                        query.setParameter(key, (Collection<?>) param);
                    } else {
                        query.setParameter(key, param);

                    }
                }
            }
            List<?> list = (List<?>) query.getResultList();
            return (List<F>) list;
        } catch (Throwable e) {
            throw new PersistenceException(e);
        }

    }

    public List<F> getMaxResults(String where, String orderBy, Map<String, Object> params, int start, int max, long[] count) throws PersistenceException {
        count[0] = count(where, params);
        return getMaxResults(where + orderBy, params, start, max);
    }

    public List<F> getMaxResults(String criteria, Map<String, Object> parameters, int startIndex, int max) throws PersistenceException {

        try {
            String name = getPersistentClass().getSimpleName().toLowerCase();
            Query query = entityManager.createQuery("select " + name + " from " + getPersistentClass().getCanonicalName() + " as " + name + "  " + criteria);

            if (null != parameters) {
                for (String key : parameters.keySet()) {
                    Object param = parameters.get(key);
                    if (param instanceof Collection) {
                        query.setParameter(key, (Collection<?>) param);
                    } else {
                        query.setParameter(key, param);

                    }
                }
            }
            query.setFirstResult(startIndex);
            query.setMaxResults(max);
            List<?> list = (List<?>) query.getResultList();
            return (List<F>) list;
        } catch (Exception e) {
            throw new PersistenceException(e);
        }

    }

    public long count(String criteria, Map<String, Object> parameters) throws PersistenceException {

        try {
            String name = getPersistentClass().getSimpleName();
            Query query = entityManager.createQuery("select count(*) FROM " + getPersistentClass().getCanonicalName() + " as " + name.toLowerCase() + "  " + criteria);

            if (null != parameters) {
                for (String key : parameters.keySet()) {
                    Object param = parameters.get(key);
                    if (param instanceof Collection) {
                        query.setParameter(key, (Collection<?>) param);
                    } else {
                        query.setParameter(key, param);

                    }
                }
            }
            List<?> list = query.getResultList();
            return list.size() > 0 ? (Long) list.get(0) : 0;
        } catch (Exception e) {
            throw new PersistenceException(e);
        }

    }



    public List<F> findByCriteria(String where, Map<String, Object> parameters) throws PersistenceException {

        try {
            String name = getPersistentClass().getSimpleName().toLowerCase();
            Query query = entityManager.createQuery(
                    "select " + name + " from "
                            + getPersistentClass().getCanonicalName() + " as "
                            + name + "  " + where);
            if (null != parameters) {
                for (String key : parameters.keySet()) {
                    Object param = parameters.get(key);
                    if (param instanceof Collection) {
                        query.setParameter(key, (Collection<?>) param);
                    } else {
                        query.setParameter(key, param);

                    }
                }
            }
            List<?> list = (List<?>) query.getResultList();
            return (List<F>) list;
        } catch (Throwable e) {
            throw new PersistenceException(e);
        }

    }

    public List<?> findByCriteria(String field, String where, String orderBy, Map<String, Object> parameters)
            throws PersistenceException {
        try {
            String name = getPersistentClass().getSimpleName().toLowerCase();
            Query query = entityManager.createQuery("select " + field + " from "
                    + getPersistentClass().getCanonicalName() + " as " + name + "  " + where + "  " + orderBy);

            if (null != parameters) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }


            List<?> list = (List<?>) query.getResultList();
            return list;
        } catch (Throwable e) {
            throw new PersistenceException(e);
        }
    }

    public Long agg(String fnc, String field, String criteria, Map<String, Object> parameters){
        try {

            String name = getPersistentClass().getSimpleName().toLowerCase();

            Query query = entityManager.createQuery(
                    "select "+fnc+"("+field+") FROM "
                            + getPersistentClass().getCanonicalName() + " as "
                            + name + "  " + criteria);

            if (null != parameters) {
                for (String key : parameters.keySet()) {
                    Object param = parameters.get(key);
                    if (param instanceof Collection) {
                        query.setParameter(key, (Collection<?>) param);
                    } else {
                        query.setParameter(key, param);

                    }
                }
            }
            List<?> list = query.getResultList();
            if(list.get(0) != null) {
                return list.size() > 0 ? (Long) list.get(0) : 0;
            }
            else {
                return 0L;
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public List<F> getMaxResults(String where, Map<String, Object> params,
                                 int start, int max, long[] count) throws PersistenceException {

        count[0] = count(where, params);
        return getMaxResults(where, params, start, max);
    }


    protected Class<F> getPersistentClass() {
        return persistentClass;
    }


}
