package library_DB.com.yulim.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import library_DB.com.yulim.util.JDBCUtil;

public class LoanManager {

    Connection conn = JDBCUtil.getConnection();

    // 특정 회원의 모든 대출 이력, 최신 순으로 정렬
    public void readAllBook(String memberId) {
        String sql = "SELECT * FROM LOAN WHERE MEMBERID = ? ORDER BY LOADID DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (columnCount == 0) {
                System.out.println("<대출 이력이 없습니다.>");
                return;
            }
            for (int j = 1; j <= columnCount; j++) {
                System.out.print(rsmd.getColumnName(j) + "\t ");
            }
            System.out.println();
            while (rs.next()) {
                int id = rs.getInt("LOANID");
                String bookId = rs.getString("BOOKID");
                String bookName = rs.getString("BOOKNAME");
                String memberId2 = rs.getString("MEMBERID");
                Date borrowDate = rs.getDate("BORROWDATE");
                Date deadLine = rs.getDate("DEADLINE");
                String isExtended = rs.getString("ISEXTENDED");
                String isReturned = rs.getString("ISRETURNED");

                System.out.println(id + "\t " + bookId + "\t " + bookName + "\t \t " + memberId2
                        + "\t \t " + borrowDate + "\t " + deadLine + "\t " + isExtended + "\t \t "
                        + isReturned);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 회원의 현재 대출 중인 책, 반납 기간 임박한 순서
    public void readNowBook(String memberId) {
        String sql =
                "SELECT * FROM LOAN WHERE BOOKID IN (SELECT ID FROM BOOK WHERE CURRENTOWNERID = ?) AND LOAN.ISRETURNED = 'F' ORDER BY DEADLINE DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (columnCount == 0) {
                System.out.println("<현재 대출 중인 책이 없습니다.>");
            }
            for (int j = 1; j <= columnCount; j++) {
                System.out.print(rsmd.getColumnName(j) + "\t ");
            }
            System.out.println();
            while (rs.next()) {
                int id = rs.getInt("LOANID");
                String bookId = rs.getString("BOOKID");
                String bookName = rs.getString("BOOKNAME");
                String memberId2 = rs.getString("MEMBERID");
                Date borrowDate = rs.getDate("BORROWDATE");
                Date deadLine = rs.getDate("DEADLINE");
                String isExtended = rs.getString("ISEXTENDED");
                String isReturned = rs.getString("ISRETURNED");

                System.out.println(id + "\t " + bookId + "\t " + bookName + "\t " + memberId2 + "\t"
                        + borrowDate + "\t" + deadLine + "\t" + isExtended + "\t" + isReturned);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 책 대출
    public void loanBook(String memberId, String bookId) {
        String sql = "INSERT INTO LOAN(MEMBERID,BOOKID,BOOKNAME) "
                + "SELECT ?, ID, NAME FROM BOOK WHERE CANBORROW = 'T' AND ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, bookId);

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<대출 실패>");
            } else {
                String sql2 =
                        "UPDATE BOOK SET CANBORROW='F', CURRENTOWNERID = (SELECT MEMBERID FROM LOAN WHERE BOOKID = ? AND ISRETURNED = 'F') WHERE ID= ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                    pstmt2.setString(1, bookId);
                    pstmt2.setString(2, bookId);

                    int result2 = pstmt2.executeUpdate();
                    if (result2 == 0) {
                        System.out.println("<대출 실패>");
                    } else {
                        System.out.println("<대출 성공>\n");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 책 반납
    public void returnBook(String memberId, String bookId) {
        String sql = "UPDATE LOAN SET ISRETURNED = 'T' WHERE BOOKID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<반납 실패>");
            } else {
                String sql2 =
                        "UPDATE BOOK SET CANBORROW = 'T' , CURRENTOWNERID = NULL WHERE ID = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                    pstmt2.setString(1, bookId);

                    int result2 = pstmt2.executeUpdate();
                    if (result2 == 0) {
                        System.out.println("<반납 실패>");
                    } else {
                        System.out.println("<반납 완료>\n");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 반납 연기
    public void extendBook(String memberId, String bookId) {
        String sql =
                "UPDATE LOAN SET ISEXTENDED = 'T' WHERE MEMBERID=? AND BOOKID=? AND ISEXTENDED='F'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, bookId);

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<연장 실패>");
            } else {
                System.out.println("<연장 완료>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
