package library_DB.com.yulim.controller;

import java.sql.SQLException;
import java.util.Scanner;
import library_DB.com.yulim.entity.Book;
import library_DB.com.yulim.entity.Member;
import library_DB.com.yulim.service.BookManager;
import library_DB.com.yulim.service.LoanManager;
import library_DB.com.yulim.service.MemberManager;

public class Controller {

    BookManager bm = new BookManager();
    MemberManager mm = new MemberManager();
    LoanManager lm = new LoanManager();

    Scanner sc = new Scanner(System.in);

    public void run() throws Exception {

        int firstOption;
        while (true) {
            System.out.println("\n<도서 관리 프로그램 시작>\n0. 프로그램 종료 1. 관리자로 시작하기 2. 회원 가입 3. 회원 로그인");
            // first depth option
            firstOption = sc.nextInt();

            switch (firstOption) {
                // 프로그램 종료
                case 0: {
                    break;
                }
                // 관리자로 시작
                case 1: {
                    while (true) {
                        // second depth option
                        System.out.println("\n<관리자로 시작>\n0. 뒤로 가기 1. 회원 관리 2. 도서 관리");
                        int secondOption = sc.nextInt();
                        switch (secondOption) {
                            case 0: {
                                break;
                            }
                            case 1: {
                                회원관리();
                                break;
                            }
                            case 2: {
                                도서관리();
                                break;
                            }
                            default: {
                                System.out.println("0~2 사이로 다시 입력해주세요");
                                continue;
                            }
                        }
                        if (secondOption == 0)
                            break;
                    }
                    break;
                }
                // 회원 가입
                case 2: {
                    System.out.println("\n<회원가입>\n아이디를 입력해주세요.");
                    String id = sc.next();
                    System.out.println("\n이름을 입력해주세요.");
                    String name = sc.next();
                    System.out.println("성별을 입력해주세요.(여자/남자)");
                    String gender = sc.next();
                    System.out.println("생일을 입력해주세요.(19980102의 형식)");
                    String birth = sc.next();
                    System.out.println("주소를 입력해주세요.");
                    String address = sc.next();
                    System.out.println("연락처를 입력해주세요.");
                    String phone = sc.next();

                    mm.create(new Member(id, name, gender, birth, address, phone));
                    System.out.println("\n<회원가입 완료>\n로그인 시 회원번호 " + id + "으로 입력해주세요.");

                    로그인(id);
                    break;

                }
                // 회원 로그인
                case 3: {
                    while (true) {
                        System.out.println("\n<회원 로그인>\n회원번호를 입력하세요.");
                        String memberId = sc.next();
                        if (isSuccessLogin(memberId)) {
                            System.out.println("\n로그인 성공");
                            로그인(memberId);
                            break;
                        } else {
                            System.out.println("<로그인 실패>");
                            break;
                        }
                    }
                    break;
                }
                default: {
                    System.out.println("0~3 사이로 다시 입력해주세요");
                    continue;
                }
            }
            if (firstOption == 0) {
                // 프로그램 종료시, 바뀐 데이터를 저장하고 마무리한다.
                System.out.println("프로그램을 종료합니다.");
                return;
            }
        }

    }

    public void 회원관리() throws SQLException {
        int thirdOption;
        while (true) {
            System.out.println("\n<회원 관리>\n0. 뒤로 가기 1. 회원 조회 2. 회원 수정 3. 회원 삭제 4. 삭제 취소");
            thirdOption = sc.nextInt();

            switch (thirdOption) {
                // 뒤로 가기
                case 0: {
                    break;
                }
                // 회원 조회(id 순으로)
                case 1: {
                    mm.read();
                    break;
                }
                // 회원 수정
                case 2: {
                    System.out.println("\n<회원 수정>\n수정하고 싶은 회원 번호를 입력하세요.");
                    String id = sc.next();
                    System.out.println("이름을 수정해주세요.");
                    String name = sc.next();
                    System.out.println("성별을 수정해주세요. (여자/남자)");
                    String gender = sc.next();
                    System.out.println("생일을 수정해주세요. (19980102의 형식)");
                    String birth = sc.next();
                    System.out.println("주소를 수정해주세요.");
                    String address = sc.next();
                    System.out.println("연락처를 수정해주세요.");
                    String phone = sc.next();
                    mm.update(id, new Member(id, name, gender, birth, address, phone));

                    break;
                }
                // 회원 삭제
                case 3: {
                    System.out.println("\n<회원 삭제>\n삭제하고 싶은 회원 번호를 입력하세요.");

                    String id = sc.next();
                    mm.delete(id);
                    break;
                }
                // 삭제 취소
                case 4: {
                    mm.redo();
                    break;
                }
                default: {
                    System.out.println("0~4 사이로 다시 입력해주세요");
                    continue;
                }
            }
            if (thirdOption == 0) {
                return;
            }
        }

    }

    public void 도서관리() throws SQLException {
        int option;
        while (true) {
            System.out.println("\n<도서 관리>\n0. 뒤로 가기 1. 도서 추가 2. 도서 조회 3. 도서 수정 4. 도서 삭제");
            option = sc.nextInt();

            switch (option) {
                case 0: {
                    break;
                }
                case 1: {
                    try {
                        System.out.println("\n<도서 추가>\n추가할 책 번호를 입력하세요.");
                        String id = sc.next();
                        System.out.println("추가할 책 이름을 입력하세요.");
                        String name = sc.next();
                        System.out.println("책 저자를 입력해주세요.");
                        String author = sc.next();
                        System.out.println("책 출판일을 입력해주세요.");
                        String publishedDate = sc.next();
                        bm.create(new Book(id, name, author, publishedDate));
                    } catch (Exception e) {
                        System.out.println("<책 수정 실패>");
                        return;
                    }
                    break;
                }
                case 2: {
                    bm.readAllBook();
                    break;
                }
                case 3: {
                    System.out.println("\n<책 수정>\n수정하고 싶은 책 번호를 입력하세요.");
                    String id = sc.next();
                    System.out.println("책 이름을 수정해주세요.");
                    String name = sc.next();
                    System.out.println("책 저자를 수정해주세요.");
                    String author = sc.next();
                    System.out.println("책 출판일을 수정해주세요.");
                    String publishedDate = sc.next();
                    bm.update(id, new Book(id, name, publishedDate, author));
                    break;
                }
                case 4: {
                    System.out.println("\n<책 삭제>\n삭제하고 싶은 책 번호를 입력하세요.");
                    String id = sc.next();
                    bm.delete(id);
                    break;
                }
                default: {
                    System.out.println("0~4 사이로 다시 입력해주세요");
                    continue;
                }
            }
            if (option == 0) {
                return;
            }
        }

    }

    public boolean isSuccessLogin(String id) {
        // 만약 아이디값이 있으면 성공
        if (mm.findMember(id) != null)
            return true;
        else
            return false;
    }

    public void 로그인(String id) {
        int option;
        while (true) {
            System.out.println("\n<" + mm.findMember(id).getName()
                    + "님으로 로그인 상태>\n0. 로그아웃 1. 대출 가능한 책 조회 2. 현재 대출 중인 책 조회 3. 대출 이력 모두 보기");
            option = sc.nextInt();

            switch (option) {
                // 로그아웃
                case 0: {
                    break;
                }
                // 대출 가능한 책 조회
                case 1: {
                    System.out.println("\n<대출 가능한 책 조회>");
                    bm.read();
                    borrowBook(id);
                    break;
                }
                // 현재 대출 중인 책 조회
                case 2: {
                    System.out.println("\n<현재 회원님이 대출 중인 책>");
                    lm.readNowBook(id);
                    returnBook(id);
                    break;
                }
                // 대출 이력 모두 보기
                case 3: {
                    System.out.println("\n<대출 이력 조회>");
                    lm.readAllBook(id);
                    break;
                }
                default: {
                    System.out.println("0~2 사이로 다시 입력해주세요");
                    continue;
                }
            }
            if (option == 0) {
                return;
            }
        }
    }

    public void borrowBook(String memberId) {
        int option;
        while (true) {
            System.out.println("\n0. 뒤로 가기 1. 책 대출하기");
            option = sc.nextInt();

            switch (option) {
                // 뒤로 가기
                case 0: {
                    break;
                }
                // 책 대출하기
                case 1: {
                    System.out.println("\n<도서 대출>\n빌리고 싶은 책 번호를 입력해주세요");
                    String bookId = sc.next();

                    // 책이 없을 때
                    if (!bm.findBook(bookId)) {
                        System.out.println("\n<대출 실패, 해당하는 책이 없습니다.>");
                        return;
                    } else {
                        lm.loanBook(memberId, bookId);
                    }
                    break;
                }
                default: {
                    System.out.println("0 또는 1로 다시 입력해주세요");
                    continue;
                }
            }
            if (option == 0) {
                return;
            }
        }
    }

    public void returnBook(String memberId) {
        int option;
        while (true) {
            System.out.println("\n0. 뒤로 가기 1. 책 반납하기 2. 대출 연장하기");
            option = sc.nextInt();

            switch (option) {
                // 뒤로 가기
                case 0: {
                    break;
                }
                // 책 반납하기
                case 1: {
                    System.out.println("\n<도서 반납>\n반납하고 싶은 책 번호를 입력해주세요");

                    String bookId = sc.next();

                    if (bm.findBook(bookId)) {
                        lm.returnBook(memberId, bookId);
                    } else {
                        System.out.println("\n<반납 실패, 해당하는 책이 없습니다.>");
                    }
                    break;
                }
                // 대출 연장하기
                case 2: {
                    System.out.println("\n<대출 연장>\n대출 연장하고 싶은 책 번호를 입력해주세요");

                    String bookId = sc.next();

                    if (bm.findBook(bookId)) {
                        lm.extendBook(memberId, bookId);
                    } else {
                        System.out.println("\n<연장 실패, 해당하는 책이 없습니다.>");
                    }

                    break;
                }
                default: {
                    System.out.println("0~2 사이로 다시 입력해주세요");
                    continue;
                }
            }
            if (option == 0) {
                return;
            }
        }
    }
}
