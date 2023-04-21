package library_DB.com.yulim.entity;

public class Book {
    private String id;
    private String name;
    private String author;
    private String publishedDate;
    private boolean canBorrow;
    private String currentOwnerId;

    // 생성자
    public Book(String name, String author, String publishedDate) {
        this.name = name;
        this.author = author;
        this.publishedDate = publishedDate;
        this.canBorrow = true;
        this.currentOwnerId = null;
    }

    public String getCurrentOwnerId() {
        return currentOwnerId;
    }

    public void setCurrentOwnerId(String currentOwnerId) {
        this.currentOwnerId = currentOwnerId;
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

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean getCanBorrow() {
        return canBorrow;
    }

    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

}
