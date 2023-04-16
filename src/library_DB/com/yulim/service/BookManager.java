package library_DB.com.yulim.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import library_DB.com.yulim.entity.Book;

public class BookManager implements CRUD<Book> {

    // 모든 책 리스트 최신 출간 순으로 가져오기
    public void readAllBook() {
        String sql = "SELECT * FROM BOOK ORDER BY PUBLISHEDDATE DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int j = 1; j <= columnCount; j++) {
                System.out.print(rsmd.getColumnName(j) + "\t ");
            }
            System.out.println();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String author = rs.getString("AUTHOR");
                Date publishedDate = rs.getDate("PUBLISHEDDATE");
                String canborrow = rs.getString("CANBORROW");
                String currentOwnerId = rs.getString("CURRENTOWNERID");
                System.out.println(id + "\t " + name + "\t " + author + "\t " + publishedDate
                        + "\t " + canborrow + "\t \t " + currentOwnerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Id 값으로 책 있는지 없는지
    public boolean findBook(String id) {
        String sql = "SELECT * FROM BOOK WHERE ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // 최근 출간 순으로 빌릴 수 있는 책만 조회
    @Override
    public void read() {
        String sql = "SELECT * FROM BOOK WHERE CANBORROW = 'T' ORDER BY PUBLISHEDDATE DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int j = 1; j <= columnCount; j++) {
                System.out.print(rsmd.getColumnName(j) + "\t ");
            }
            System.out.println();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String author = rs.getString("AUTHOR");
                Date publishedDate = rs.getDate("PUBLISHEDDATE");
                String canborrow = rs.getString("CANBORROW");
                String currentOwnerId = rs.getString("CURRENTOWNERID");
                System.out.println(id + "\t " + name + "\t " + author + "\t " + publishedDate
                        + "\t " + canborrow + "\t \t " + currentOwnerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(String id, Book changeBook) {
        String sql =
                "UPDATE BOOK SET NAME = ?, AUTHOR = ?, PUBLISHEDDATE = TO_DATE(?,'RRRR/MM/DD') WHERE ID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, changeBook.getName());
            pstmt.setString(2, changeBook.getAuthor());
            pstmt.setString(3, changeBook.getPublishedDate());
            pstmt.setString(4, changeBook.getId());

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<수정 불가>");
            } else {
                System.out.println(result + " rows updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM BOOK WHERE ID=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
        System.out.println("<도서 삭제 완료>");
    }


    @Override
    public String create(Book newBook) throws ParseException {
        String sql = "INSERT INTO BOOK(NAME, AUTHOR, PUBLISHEDDATE) "
                + "VALUES (?,?, TO_DATE(?,'RRRR/MM/DD'))";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newBook.getName());
            pstmt.setString(2, newBook.getAuthor());
            pstmt.setString(3, newBook.getPublishedDate());

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<책 생성 실패>");
            } else {
                System.out.println("<책 생성 완료>");
                String sql2 = "SELECT ID FROM BOOK WHERE ROWNUM = 1 ORDER BY ID DESC";
                try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                    ResultSet rs = pstmt2.executeQuery();
                    if (rs.next()) {
                        return String.valueOf(rs.getInt("ID"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


}
