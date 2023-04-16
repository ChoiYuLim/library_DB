package library_DB.com.yulim;

import library_DB.com.yulim.controller.Controller;

public class Main {

    public static void main(String[] args) {
        // 처음 시작
        Controller manager = new Controller();
        System.out.println("도서 대출 관리 프로그램");
        try {
            manager.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
