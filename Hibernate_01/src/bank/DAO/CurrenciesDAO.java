/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.DAO;

import bank.entity.Currencies;
import bank.entity.Operations;
import bank.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Danya
 */
public class CurrenciesDAO {
    public List<Currencies> listCurrencies() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Currencies> result = session.createQuery("from Currencies order by id").list();
        session.getTransaction().commit();
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
