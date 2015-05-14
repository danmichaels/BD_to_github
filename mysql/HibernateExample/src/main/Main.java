package main;

import java.sql.SQLException;
import java.util.List;

import logic.Student;
import DAO.Factory;

public class Main {
	
	public static void main(String[] args) throws SQLException {
	    //Выведем всех студентов из бд
		List<Student> studs = Factory.getInstance().getStudentDAO().testFunc();
		System.out.println("========Результат запроса=========");
	    for(int i = 0; i < studs.size(); ++i) {
		     	System.out.println("Имя студента : " + studs.get(i).getName() + ", Возраст : " + studs.get(i).getAge() +",  id : " + studs.get(i).getId());
		     	System.out.println("=============================");		      
	    }	    
	}
}
