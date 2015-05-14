package bank;

import bank.DAO.BillDAO;
import bank.entity.Card;
import org.hibernate.Session;
import bank.utils.HibernateUtil;
import bank.DAO.CardDAO;
import bank.DAO.ClientsDAO;
import bank.DAO.OperationsDAO;
import bank.entity.Bill;
import bank.entity.Clients;
import bank.entity.Credits;
import bank.entity.Operations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

public class Main {

    // Данный метод просто показывает, как делается запрос при работе на уровне JDBC
    private void oldJDBC() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String databasepath = "bank_term";
        String host = "localhost";
        String user = "root";
        String passwd = "root";
        
        String URL = "jdbc:mysql://"+host+":3306/"+databasepath;
        
        try {
            //Class.forName("org.firebirdsql.jdbc.FBDriver");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,
                    user, passwd);
            statement = connection.createStatement();
            List<Card> list = new ArrayList<Card>();
            System.out.println(":::::::::select number, start_date from Card " +
                    "order by number");
            rs = statement.executeQuery("select number, start_date from Card " +
                    "order by number");
            while (rs.next()) {
                Card r = new Card();
                r.setNumber(rs.getInt("number"));
                r.setStart_date(rs.getDate("start_date"));
                list.add(r);
                System.out.println(r.getNumber() + ":" + r.getStart_date());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
    
    public void iotest(ClientsDAO clientsDAO, BillDAO billDAO, CardDAO cardDAO){
        
        System.out.println(":::::::::+ client");
        clientsDAO.addClients("101010101111");
        clientsDAO.addClients("101010111111");
        clientsDAO.addClients("101010111115");
        
        System.out.println(":::::::::Printing list of last Clients by now");
        clientsDAO.displayResultClients(clientsDAO.listLastClients(10));

        System.out.println(":::::::::+ bill");
        billDAO.addBill(1);
        billDAO.addBill(1);
        billDAO.addBill(2);
        billDAO.addBill(3);
        System.out.println(":::::::::Printing list of last Bill by now");
        billDAO.displayResultBill(billDAO.listLastBill(10));

        System.out.println(":::::::::Printing list of Cards by now");
        cardDAO.displayResultCard(cardDAO.listCard());

        // Добавление новых банковских карт
        System.out.println(":::::::::adding card");
        cardDAO.addCard(2,Date.valueOf("2015-04-27"),null);
        cardDAO.addCard(3,Date.valueOf("2015-04-26"),null);
        /*cardDAO.addCard(3,Date.valueOf("2015-04-25"));
        cardDAO.addCard(4,Date.valueOf("2015-04-24"));
        cardDAO.addCard(5,Date.valueOf("2015-04-23"));*/

        System.out.println(":::::::::Printing list of last Cards by now");
        cardDAO.displayResultCard(cardDAO.listLastCard(10));

        // Вариант вызова списка
        
        List<Card> result_cards = new ArrayList();

        // Вариант вызова удаления одной записи
        result_cards = cardDAO.listFirstCard(3);
        cardDAO.deleteEntity(result_cards.get(0));

        // Вариант вызова списка и последующее удаление
        result_cards = cardDAO.listCard();
        System.out.println(":::::::::Printing list of Cards by now (to delete)");
        cardDAO.displayResultCard(result_cards);
        if(!result_cards.isEmpty())
            cardDAO.deleteCards(result_cards);
        
        /*List<Bill> result_bill = billDAO.listBill();
        if(billDAO.listBill().size()>1)
            billDAO.deleteBill(result_bill);
        
        List<Clients> result_clients = clientsDAO.listClients();
        if(!clientsDAO.listClients().isEmpty())
            clientsDAO.deleteClients(result_clients);*/
        
    }

    public static void main(String[] args) {
        Main main = new Main();
        
        // Вызов "старого стиля"
        main.oldJDBC();
        
        ClientsDAO clientsDAO = new ClientsDAO();
        BillDAO billDAO = new BillDAO();
        CardDAO cardDAO = new CardDAO();
        OperationsDAO operationsDAO = new OperationsDAO();
        
        cardDAO.deleteCards(cardDAO.listCard());
        
        System.out.println(":::::::::Printing list of Cards by now");
        cardDAO.displayResultCard(cardDAO.listCard());

        try{
            main.iotest(clientsDAO,billDAO,cardDAO);
            
            // testing getting client from cards
            System.out.println(":::::::::Testing tables oop: many-to-one");
            // Добавление новых банковских карт
            System.out.println(":::::::::adding card");
            
            // Получаем первого по счету клиента
            System.out.println(":::::::::First of clients: ");
            List<Clients> result_clients = clientsDAO.listFirstClients(10);
            Clients client = result_clients.get(0);
            // Вывод
            List<Clients> l_clients = new ArrayList<Clients>();
            l_clients.add(client);
            clientsDAO.displayResultClients(l_clients);
            
            // Добавляются в базу
            // bill_id
            cardDAO.addCard(2,Date.valueOf("2015-04-27"),client);
            cardDAO.addCard(3,Date.valueOf("2015-04-26"),client);
            cardDAO.addCard(4,Date.valueOf("2015-04-26"),client);
            
            // Проверка отношения Many-to-One
            List<Card> result_cards = cardDAO.listFirstCard(10);
            Clients check_client = result_cards.get(0).getClients();
            
            //Вывод первой карты в списке
            System.out.println(":::::::::First of cards: ");
            List<Card> l_card = new ArrayList<Card>();
            l_card.add(result_cards.get(0));
            cardDAO.displayResultCard(l_card);
            
            // Вывод обладателя первой по счету карты
            System.out.println(":::::::::Check from clients: ");
            l_clients = new ArrayList<Clients>();
            l_clients.add(check_client);
            
            clientsDAO.displayResultClients(l_clients);
            
            /*for(Card p : check_client.getCard()) {
                System.out.println(":::::::::Number: "+p.getNumber()+" client_id: "+p.getClient_id()+" start_date: "+p.getStart_date());
            }*/
            
            // Проверка отношения One-To-Many
            System.out.println(":::::::::Selecting cards by client");
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            for(Card p : check_client.getCard()) {
                System.out.println(":::::::::Number: "+p.getNumber()+" client_id: "+p.getClient_id()+" start_date: "+p.getStart_date());
            }
            session.getTransaction().commit();
            
            // Проверка отношения One-To-Many до вызова процедуры ADD_PERCENTS_CREDIT
            
            System.out.println(":::::::::Selecting credits by bill");
            
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            List<Bill> first_bill = billDAO.listFirstBill(5);
            Bill check_bill = first_bill.get(3);
            Set<Credits> result_credits = check_bill.getCredits();
            session.getTransaction().commit();
            
            /*
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");
            
            
            EntityManager em2 = emf.createEntityManager();
            em2.getTransaction().begin();

            List<Credits> res = (Credits) em2.find(Credits.class, check_bill.getId());
            System.out.println(res.getMsg()
                            + res.getEntities2().iterator().next().getMsg());
            em2.getTransaction().commit();
            em2.close();
            */
            
            for(Credits p : result_credits) {
                System.out.println(":::::::::Bill_id: "+p.getBill_id()+
                        " rate: "+p.getRate()+
                        " start_date: "+p.getStart_date()+
                        " period:"+p.getPeriod()+
                        " last_update: "+p.getLast_update()
                );
            }
            
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("CALL ADD_PERCENTS_CREDIT("+check_bill.getId()+")").executeUpdate();
            session.getTransaction().commit();
            
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            first_bill = billDAO.listFirstBill(5);
            check_bill = first_bill.get(3);
            result_credits = check_bill.getCredits();
            session.getTransaction().commit();
            
            for(Credits p : result_credits) {
                System.out.println(":::::::::Bill_id: "+p.getBill_id()+
                        " rate: "+p.getRate()+
                        " start_date: "+p.getStart_date()+
                        " period:"+p.getPeriod()+
                        " last_update: "+p.getLast_update()
                );
            }
            
            /*
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Card u = (Card) session.find("from User u where u.name=?", "", Hibernate.string).get(0);
            Map permissions = u.getPermissions();
            session.getTransaction().commit();
            
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Clients res = (Clients) session.get(Clients.class, 1);
            
            for(Card p : res.getCard()) {
                System.out.println(":::::::::Number: "+p.getNumber()+" client_id: "+p.getClient_id()+" start_date: "+p.getStart_date());
            }
            session.getTransaction().commit();
            */
            /*
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Entity1 ent1 = new Entity1();
            ent1.setMsg("Hello");
            Set<Entity2> ent2s = new HashSet<Entity2>();
            Entity2 ent2 = new Entity2();
            ent2.setMsg(" World!!");
            ent2s.add(ent2);
            ent1.setEntities2(ent2s);
            em.persist(ent1);

            em.getTransaction().commit();
            em.close();

            EntityManager em2 = emf.createEntityManager();
            em2.getTransaction().begin();

            List<Card> res = (Entity1) em2.find(Entity1.class, ent1.getId());
            System.out.println(res.getMsg()
                            + res.getEntities2().iterator().next().getMsg());
            em2.getTransaction().commit();
            em2.close();
            */
            
            // Проверка связи Bill - Operations
            
            System.out.println(":::::::::First several of Bill: ");
            
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            List<Operations> l_oper = new ArrayList();
            List<Operations> out_operations = new ArrayList();
            
            first_bill = billDAO.listFirstBill(5);
            check_bill = first_bill.get(3);
            //Set<Operations> result_oper = check_bill.getOperations();
            
            
            // Получаем все операции для счета
            l_oper = operationsDAO.listOperations(check_bill.getId());
            check_bill.setOperations(l_oper);
            
            Operations oper = new Operations();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2010, 4 - 1, 24, 0, 0, 0);
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
            
            /*
            Long num = new Long (4999); //////operationsDAO.countOperations();
            oper.setId_local_side(result_operations.get(0).getId_local_side());
            oper.setFor_id(num+1);
            oper.setDate(timestamp);
            l_oper.add(oper);
            out_operations.add(oper);
            
            oper = result_operations.get(1);
            oper.setId_local_side(result_operations.get(1).getId_local_side());
            oper.setFor_id(num+2);
            oper.setDate(timestamp);
            l_oper.add(oper);
            out_operations.add(oper);
            
            oper = result_operations.get(1);
            oper.setId_local_side(result_operations.get(1).getId_local_side());
            oper.setFor_id(num+3);
            oper.setDate(timestamp);
            l_oper.add(oper);
            out_operations.add(oper);
            
            check_client.setOperations(l_oper);
            
            check_client.setId(2057);
            session.save(check_client);
            */
            
            session.getTransaction().commit();
            session.close();
            
            operationsDAO.displayResultOperations(l_oper);
            
            // Процедура перевода на другой счет
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("CALL LOCALMONEYTRANSFER("+"'info'"+","+10+","+check_bill.getId()+","+(check_bill.getId()+1)+")").executeUpdate();
            session.getTransaction().commit();
            
            System.out.println(":::::::::Selecting operations by bill");
            
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            first_bill = billDAO.listFirstBill(5);
            check_bill = first_bill.get(3);
            
            l_oper = operationsDAO.listOperations(check_bill.getId());
            check_bill.setOperations(l_oper);
            
            operationsDAO.displayResultOperations(l_oper);
            
            session.getTransaction().commit();
            
            
            try {
                System.in.read();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.exit(0);
            
        } catch( org.hibernate.exception.GenericJDBCException ex){
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
            System.exit(0);
        }catch( org.hibernate.QueryException ex){
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    
    
}
