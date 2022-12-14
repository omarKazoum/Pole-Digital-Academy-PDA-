package com.example.pole_digital_academy.Dao.Responsible;

import com.example.pole_digital_academy.Entities.Responsible;
import com.example.pole_digital_academy.utils.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ResponsibleDaoImp implements IResponsibleDao{
    @Override
    public int delete(int id) throws Exception {
        return 0;
    }

    @Override
    public List<Responsible> getAll() throws Exception {
        return EntityManagerFactory.getEntityManager().createQuery("SELECT res FROM Responsible res ", Responsible.class).getResultList();

    }


    public List<Responsible> getNonOccupedResponsibles() throws Exception {
         return EntityManagerFactory.getEntityManager().createQuery("SELECT res FROM Responsible res WHERE NOT EXISTS ( SELECT ac FROM Activity ac where res.id = ac.responsible.id) ", Responsible.class).getResultList();
    }

    @Override
    public Responsible update1(Responsible responsible) throws Exception {
        EntityManager em = EntityManagerFactory.getEntityManager();
        em.getTransaction().begin();
        em.find(Responsible.class,responsible.getId());
        em.merge(responsible);
        em.getTransaction().commit();
        return responsible;

    }

    @Override
    public Responsible findById(int id) throws Exception {
        EntityManager em = EntityManagerFactory.getEntityManager();
        Responsible responsible = em.find(Responsible.class,id);
        return responsible;
    }

    @Override
    public int update(Responsible entity) throws Exception {
        return 0;
    }
}
