package DAO;

import java.sql.SQLException;
import java.util.List;

import logic.Student;

public interface StudentDAO {
	public void addStudent(Student student) throws SQLException;   //добавить студента
	public void updateStudent(Student student) throws SQLException;//обновить студента
	public Student getStudentById(Long id) throws SQLException;	   //получить стедента по id
	public List getAllStudents() throws SQLException;			   //получить всех студентов
	public void deleteStudent(Student student) throws SQLException;//удалить студента
	public List testFunc() throws SQLException;//удалить студента
}
