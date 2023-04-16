package library_DB.com.yulim.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import library_DB.com.yulim.entity.Member;

public class MemberManager implements CRUD<Member> {

    // 회원 가입
    @Override
    public String create(Member newMember) throws ParseException {
        String sql = "INSERT INTO MEMBER(NAME, GENDER, BIRTH, ADDRESS, PHONE) "
                + "VALUES (?,?, TO_DATE(?,'RRRR/MM/DD'), ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newMember.getName());
            pstmt.setString(2, newMember.getGender());
            pstmt.setString(3, newMember.getBirth());
            pstmt.setString(4, newMember.getAddress());
            pstmt.setString(5, newMember.getPhone());

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<멤버 생성 실패>");
            } else {
                System.out.println("<멤버 생성 완료>");
                String sql2 = "SELECT ID FROM MEMBER WHERE ROWNUM = 1 ORDER BY ID DESC";
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

    // id순으로 전체 조회
    @Override
    public void read() {
        String sql = "SELECT * FROM MEMBER ORDER BY ID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int j = 1; j <= columnCount; j++) {
                System.out.print(rsmd.getColumnName(j) + "\t\t");
            }
            System.out.println();
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String gender = rs.getString("GENDER");
                Date birth = rs.getDate("BIRTH");
                String address = rs.getString("ADDRESS");
                String phone = rs.getString("PHONE");
                Date joindate = rs.getDate("JOINDATE");
                String age = rs.getString("AGE");
                System.out.println(id + "\t\t" + name + "\t\t" + gender + "\t\t" + birth + "\t"
                        + address + "\t" + phone + "\t" + joindate + "\t\t" + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원 수정
    @Override
    public void update(String id, Member changeMember) {
        String sql =
                "UPDATE MEMBER SET NAME = ?, GENDER = ?, BIRTH = ?, ADDRESS = ?, PHONE = ? WHERE ID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, changeMember.getName());
            pstmt.setString(2, changeMember.getGender());
            pstmt.setString(3, changeMember.getBirth());
            pstmt.setString(4, changeMember.getAddress());
            pstmt.setString(5, changeMember.getPhone());
            pstmt.setString(6, id);

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<수정 불가>");
            } else {
                System.out.println("<수정 완료>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원 삭제
    @Override
    public void delete(String id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM MEMBER WHERE ID=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
        System.out.println("<회원 삭제 완료>");
    }


    // 삭제 취소
    public void redo() {

    }

    // id 값으로 멤버 정보 반환
    public Member findMember(String id) {
        String sql = "SELECT * FROM MEMBER WHERE ID=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Member(rs.getString("NAME"), rs.getString("GENDER"),
                        rs.getString("BIRTH"), rs.getString("ADDRESS"), rs.getString("PHONE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
