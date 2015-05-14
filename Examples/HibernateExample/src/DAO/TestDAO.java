package DAO;

import java.sql.SQLException;
import java.util.List;

import logic.Test;

public interface TestDAO {
	public void addTest(Test test) throws SQLException;		//добавить тест
	public void updateTest(Test test) throws SQLException;	//обновить тест
	public Test getTestById(Long t_id) throws SQLException;	//получить тест по id
	public List getAllTests() throws SQLException;			//получить все тесты
	public void deleteTest(Test test) throws SQLException;	//удалить тест

}
