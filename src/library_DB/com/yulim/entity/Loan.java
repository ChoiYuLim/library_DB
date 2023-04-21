package library_DB.com.yulim.entity;

import java.util.Date;
import library_DB.com.yulim.util.DateUtil;

/**
 * 대출 클래스: 회원 id, 책 id, 대출 날짜, 반납 날짜, 연장 여부
 */

public class Loan {
    private String bookId;
    private String bookName;
    private String memberId;
    private Date borrowDate;
    private Date deadLine;
    private Boolean isExtended;
    private Boolean isReturned;


    // 생성자
    public Loan(String bookId, String bookName, String memberId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.memberId = memberId;
        this.borrowDate = new Date();
        this.deadLine = DateUtil.addDate(new Date(), 14);
        this.isExtended = false;
        this.isReturned = false;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }


    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Boolean getIsExtended() {
        return isExtended;
    }

    public void setIsExtended(Boolean isExtended) {
        this.isExtended = isExtended;
    }

    public Boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Boolean isReturned) {
        this.isReturned = isReturned;
    }
}
