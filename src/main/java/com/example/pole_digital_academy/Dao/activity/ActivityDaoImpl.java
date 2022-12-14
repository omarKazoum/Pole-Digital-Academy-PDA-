package com.example.pole_digital_academy.Dao.activity;
import com.example.pole_digital_academy.Entities.Activity;
import com.example.pole_digital_academy.utils.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

import java.time.LocalDate;
import java.util.List;

public class ActivityDaoImpl implements IActivityDao {
    @Override
    public int delete(int id) throws Exception {
        System.out.println("Dao got id of "+id);
        Activity activityToDelete= EntityManagerFactory.getEntityManager().find(Activity.class,id);
        EntityManager em=EntityManagerFactory.getEntityManager();
        em.getTransaction().begin();
        em.remove(activityToDelete);
        em.getTransaction().commit();
        return 1;
    }

    @Override
    public List<Activity> getAll() throws Exception {
        EntityManager em = EntityManagerFactory.getEntityManager();
        List<Activity> activities=em.createQuery("SELECT a FROM Activity a",Activity.class).getResultList();
        return activities;
    }

    @Override
    public Activity findById(int id) throws Exception {
        EntityManager em = EntityManagerFactory.getEntityManager();
        Activity activity=em.find(Activity.class,id);
        //em.detach(activity);
        return activity;
    }


    @Override
    public int update(Activity entity) throws Exception {
        try {
            EntityManager em = EntityManagerFactory.getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            em.close();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Activity> search(LocalDate startIntervalDate, LocalDate endIntervalDate, Activity.ActivityTypeEnum type) {
        StringBuilder queryString=new StringBuilder();
        queryString.append("SELECT a FROM Activity a ");
        if(startIntervalDate!=null && endIntervalDate!=null){
            queryString.append("WHERE a.startDate>='"+startIntervalDate.toString()+"' AND a.endDate<='"+endIntervalDate.toString()+"'");
        }
        if(startIntervalDate!=null && endIntervalDate!=null && type!=null){
            queryString.append(" AND a.activityType="+type.ordinal());
        }else if((startIntervalDate!=null || endIntervalDate!=null) && type!=null){
            queryString.append("WHERE a.activityType="+type.ordinal());
        }
        System.out.println("query : "+queryString.toString());
        Query q=EntityManagerFactory.getEntityManager().createQuery(queryString.toString(),Activity.class);
        return q.getResultList();
    }
}
