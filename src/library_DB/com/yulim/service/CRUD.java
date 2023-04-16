package library_DB.com.yulim.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import library_DB.com.yulim.util.JDBCUtil;

public interface CRUD<E> {
    Connection conn = JDBCUtil.getConnection();

    String create(E e) throws ParseException;

    void read();

    void update(String s, E e);

    void delete(String s) throws SQLException;

}
