/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.DAO;

import bank.entity.Card;
import bank.entity.Clients;
import bank.entity.Operations;
import bank.utils.HibernateUtil;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Danya
 */
public class OperationsDAO {
    
    public List<Operations> listOperations(int bill_id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (List<Operations>) session.createCriteria(Operations.class)
                    .add(Restrictions.eq("id_local_side", bill_id))
                    .addOrder(Order.desc("for_id"))
                    .list();
        } finally {
            session.close();
        }
    }
    public List<Operations> listOperations() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Operations> result = session.createQuery("from Operations order by for_id").list();
        session.getTransaction().commit();
        return result;
    }
    /////////////////////////////////////
    public List<Operations> listFirstOperations(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Operations> result = session.createCriteria(Operations.class)
                    .addOrder(Order.asc("for_id"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public Long countOperations(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ///////////int result = session.createSQLQuery("select count(*) from Operations").executeUpdate();
        Criteria criteria = session.createCriteria(Operations.class);
	criteria.setProjection(Projections.rowCount());
	Long result = (Long) criteria.list().get(0);
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public void displayResultOperations(List resultList) {
        for(Object o : resultList) {
            Operations operations = (Operations)o;
            System.out.println("for_id: "+operations.getFor_id()+" op_type: "+operations.getOp_type()+
                    " id_local_side: "+operations.getId_local_side()+" amount: "+operations.getAmount()+
                    " date: "+operations.getDate());
        }
    }
}
