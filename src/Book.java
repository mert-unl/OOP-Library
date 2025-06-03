import enums.Category;
import enums.Status;

import java.util.Date;
import java.util.Objects;

public class Book {


    private long bookId;
    private Author author;
    private String bookName;
    private Category category;
    private int price;
    private Status status;
    private int edition;
    private Date dateOfPurchase;


    @Override
    public String toString() {
        return "\n--- 📚 " + bookName  + " ---\n" +
                " Kitap ID: " + bookId +
                " ---  Yazar: " + author.getName() +
                " --- Kategori: " + category +
                " --- Fiyat: " + price + " TL\n" +
                " Durum: " + status +
                " --- Baskı Sayısı: " + edition +
                " --- Eklenme Tarihi: " + dateOfPurchase;
    }


    public Book(long bookId, Author author, String bookName, Category category, int price, Status status, int edition, Date dateOfPurchase) {
        this.bookId = bookId;
        this.author = author;
        this.bookName = bookName;
        this.category = category;
        this.price = price;
        this.status = status;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public long getBookId() {
        return bookId;
    }

    public Author getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public int getEdition() {
        return edition;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }


}
