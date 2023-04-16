package library_DB.com.yulim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import library_DB.com.yulim.util.JDBCUtil;

public class Test {

    public static void main(String[] args) {
        Connection conn = JDBCUtil.getConnection();

        String id = "007";
        String name = "최유림";
        String gender = "여자";
        String birth = "19981223";
        String address = "경기도 광명시";
        String phone = "01020271910";

        String sql = "INSERT INTO MEMBER(ID, NAME, GENDER, BIRTH, ADDRESS, PHONE) "
                + ""
                + "VALUES (?,?,?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, gender);
            pstmt.setString(4, birth);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);

            int result = pstmt.executeUpdate();
            System.out.println(result + " rows inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
