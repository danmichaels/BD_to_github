/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.DAO;

import bank.entity.Bill;
import bank.entity.Card;
import bank.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Danya
 */
public class BillDAO {
    public BillDAO(){}
    // Пример запросов в HQL вручную
    
    // Метод добавляет новую запись в таблицу Card
    public void addBill(int client_id) {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        /////////////////////
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        session.beginTransaction();
        
        Bill r= new Bill();
        
        /*Card card = new Card();
        System.out.println("ClientsDAO: new client");
        card.setNumber(1);
        card.setStart_date(Date.valueOf("2015-04-28"));*/
        
        /*List<Clients> result = session.createQuery("from Clients clients order by clients.id desc").list();
        session.getTransaction().commit();
        System.out.println(result.get(0));
        r.setId(result.get(0).getId()+1);*/
        
        //r.setId(1);
        r.setClient_id(client_id);
        
        // last r.getCard().add(card);
        
        session.save(r);
        session.getTransaction().commit();
        //session.close();
    }
    
    // Метод возвращает список клиентов
    public List<Bill> listBill() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Bill> result = session.createQuery("from Bill order by id").list();
        session.getTransaction().commit();
        return result;
    }
    ////////////////////////////
    public List<Bill> listFirstBill(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Bill> result = session.createCriteria(Bill.class)
                    .addOrder(Order.asc("id"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public List<Bill> listLastBill(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Bill> result = session.createCriteria(Bill.class)
                    .addOrder(Order.desc("id"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    // Метод удаляет по очереди все записи, которые ему переданы в виде списка
    public void deleteBill(List<Bill> result) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(Bill p : result) {
            System.out.println("Delete:"+p.getId()+":"+p.getStart_date());
            session.delete(p);
            //session.flush();
        }
        //session.createSQLQuery("delete from bill where id = 1").executeUpdate();
        session.getTransaction().commit();
    }

    // Методу удаляет одну запись
    public void deleteEntity(Object o) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(o);
        session.flush();
        session.getTransaction().commit();
    }
    
    public void displayResultBill(List resultList) {
        for(Object o : resultList) {
            Bill bill = (Bill)o;
            System.out.println("Id: "+bill.getId()+" client_id: "+bill.getClient_id()+
                    " balance: "+bill.getBalance()+" start_date: "+bill.getStart_date()+
                    " is_active: "+bill.getIs_active()+ " currency_id: "+bill.getCurrency_id());
        }
    }
}
