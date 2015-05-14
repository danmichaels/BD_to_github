/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.DAO;

import bank.entity.Bill;
import bank.entity.Card;
import bank.entity.Clients;
import org.hibernate.Session;
import bank.utils.HibernateUtil;
import java.sql.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author Danya
 */
public class CardDAO {
    
    public CardDAO(){}
    // Пример запросов в HQL вручную
    
    // Метод добавляет новую запись в таблицу Card
    public void addCard(int bill_id, Date date, Clients clients) {
        
        //Session session = HibernateUtil.getSessionFactory().openSession();
        /////////////////////
        Bill bill;
        
        /*try {
            bill = (Bill) session.createCriteria(Bill.class)
                    .add(Restrictions.eq("id", bill_id))
                    .uniqueResult();
        
            session.beginTransaction();*/
            
            //List<Card> result = session.createQuery("from Card card order by card.number desc").list();
            //session.getTransaction().commit();
            /*System.out.println(result.get(0));
            r.setNumber(result.get(0).getNumber()+1);*/
            //r.setNumber(1);
            
            /*session.getTransaction().commit();
        } finally {
            session.close();
        }*/
        
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            bill = (Bill) session.createCriteria(Bill.class)
                    .add(Restrictions.eq("id", bill_id))
                    .uniqueResult();
            Card r= new Card(clients);
            r.setClient_id(bill.getClient_id());
            r.setBill_id(bill.getId());
            r.setStart_date(date);
            System.out.println(r.getNumber());
            session.save(r);
            session.getTransaction().commit();
    }

    // Метод возвращает список карт
    public List<Card> listCard() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Card> result = session.createQuery("from Card order by number").list();
        session.getTransaction().commit();
        return result;
    }
    ///////////////////
    
    public List<Card> listFirstCard(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Card> result = session.createCriteria(Card.class)
                    .addOrder(Order.asc("number"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public List<Card> listLastCard(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Card> result = session.createCriteria(Card.class)
                    .addOrder(Order.desc("number"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    // Метод удаляет по очереди все записи, которые ему переданы в виде списка
    public void deleteCards(List<Card> result) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        for(Card p : result) {
            System.out.println("Delete:"+p.getNumber()+":"+p.getStart_date());
            session.delete(p);
            //session.flush();
        }
        session.createSQLQuery("delete from card where number = 1").executeUpdate();
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
    
    public void displayResultCard(List resultList) {
        for(Object o : resultList) {
            Card card = (Card)o;
            System.out.println("Number: "+card.getNumber()+" Client_id: "+card.getClient_id()+
                    " Bill_id: "+card.getBill_id()+" Start_Date: "+card.getStart_date()+
                    " End_Date: "+card.getEnd_date()+" cvc: "+card.getCvc()+
                    " pin: "+card.getPin());
        }
    }
}
