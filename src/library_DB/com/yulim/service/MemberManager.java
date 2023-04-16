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
    public void create(Member newMember) throws ParseException {
        String sql = "INSERT INTO MEMBER(ID, NAME, GENDER, BIRTH, ADDRESS, PHONE) "
                + "VALUES (?,?,?, TO_DATE(?,'RRRR/MM/DD'), ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newMember.getId());
            pstmt.setString(2, newMember.getName());
            pstmt.setString(3, newMember.getGender());
            pstmt.setString(4, newMember.getBirth());
            pstmt.setString(5, newMember.getAddress());
            pstmt.setString(6, newMember.getPhone());

            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("<멤버 생성 실패>");
            } else {
                System.out.println("<멤버 생성 완료>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 멤버의 수 구하기
    public String countMember() {
        String sql = "SELECT COUNT(*) FROM MEMBER";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            return (String) rs.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // id순으로 전체 조회
    @Override
    public void read() {
        String sql = "SELECT * FROM MEMBER";

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
                String gender = rs.getString("GENDER");
                Date birth = rs.getDate("BIRTH");
                String address = rs.getString("ADDRESS");
                String phone = rs.getString("PHONE");
                Date joindate = rs.getDate("JOINDATE");
                String age = rs.getString("AGE");
                System.out.println(id + "\t " + name + "\t " + gender + "\t " + birth + "\t"
                        + address + "\t" + phone + "\t" + joindate + "\t" + age);
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
                System.out.println(result + " rows updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원 삭제
    @Override
    public void delete(String id) throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID = ?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM MEMBER WHERE ID=?");
            pstmt2.setString(1, id);
            pstmt2.executeUpdate();
            System.out.println("<삭제 완료>");
        } else {
            System.out.println("<삭제 실패>");
        }
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
                return new Member(rs.getString("ID"), rs.getString("NAME"), rs.getString("GENDER"),
                        rs.getString("BIRTH"), rs.getString("ADDRESS"), rs.getString("PHONE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}