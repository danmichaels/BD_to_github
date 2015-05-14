/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.DAO;

import bank.entity.Card;
import bank.entity.Clients;
import org.hibernate.Session;
import bank.utils.HibernateUtil;
import java.sql.Date;
import java.util.List;
import org.hibernate.criterion.Order;
/**
 *
 * @author Danya
 */
public class ClientsDAO {
    
    public ClientsDAO(){}
    // Пример запросов в HQL вручную
    
    // Метод добавляет новую запись в таблицу Card
    public void addClients(String passport_No) {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        ///////////
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        session.beginTransaction();
        
        Clients r= new Clients();
        
        /*Card card = new Card();
        System.out.println("ClientsDAO: new client");
        card.setNumber(1);
        card.setStart_date(Date.valueOf("2015-04-28"));*/
        
        /*List<Clients> result = session.createQuery("from Clients clients order by clients.id desc").list();
        session.getTransaction().commit();
        System.out.println(result.get(0));
        r.setId(result.get(0).getId()+1);*/
        
        //r.setId(1);
        r.setPassport_No(passport_No);
        
        // last r.getCard().add(card);
        
        session.save(r);
        session.getTransaction().commit();
        //session.close();
    }

    // Метод возвращает список клиентов
    public List<Clients> listClients() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Clients> result = session.createQuery("from Clients order by name").list();
        session.getTransaction().commit();
        return result;
    }
    ////////////////////////////
    public List<Clients> listFirstClients(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Clients> result = session.createCriteria(Clients.class)
                    .addOrder(Order.asc("id"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public List<Clients> listLastClients(int n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Clients> result = session.createCriteria(Clients.class)
                    .addOrder(Order.desc("id"))
                    .setMaxResults(n)
                    .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    // Метод удаляет по очереди все записи, которые ему переданы в виде списка
    public void deleteClients(List<Clients> result) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(Clients p : result) {
            System.out.println("Delete:"+p.getId()+":"+p.getPassport_No());
            session.delete(p);
            //session.flush();
        }
        //session.createSQLQuery("delete from clients where id = 1").executeUpdate();
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
    
    public void displayResultClients(List resultList) {
        for(Object o : resultList) {
            Clients clients = (Clients)o;
            System.out.println("Id: "+clients.getId()+" Name: "+clients.getName()+
                    " Address: "+clients.getAddress()+" Phone: "+clients.getPhone()+
                    " Passport_No: "+clients.getPassport_No());
        }
    }
}
