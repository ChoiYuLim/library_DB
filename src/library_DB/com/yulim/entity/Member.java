package library_DB.com.yulim.entity;

import library_DB.com.yulim.util.DateUtil;

public class Member {

    private String name;
    private String id;
    private String joinDate;
    private String address;
    private String birth;
    private String gender;
    private String phone;

    // 회원 가입
    public Member(String name, String gender, String birth, String address,
            String phone) {
        this.name = name;
        this.address = address;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.joinDate = DateUtil.getToday();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
