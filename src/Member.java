import enums.Status;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Member extends Person {

    private long id;
    private int booksIssue = 0;
    private Date membershipDate;
    private String adress;
    private String phoneNo;
    private final int maxLimit = 5;
    private int currentBorrow = 0;

    private Set<Book> purchasedBooks = new HashSet<>();
    private Set<Book> borrowBooks = new HashSet<>();

    @Override
    public String toString() {
        return "\nÜye ---> " + super.getName() +
                "\nID: " + id + " --- Üyelik Tarihi: " + membershipDate +
                "Adres: " + adress + " --- Tel: " + phoneNo
                + "\n --- Kitap Sorunu: " + booksIssue + "--- Mevcut Ödünç Alınan: " + currentBorrow + " --- Satın Alınan Kitaplar: " + purchasedBooks + " --- Ödünç Alınanlar: " + borrowBooks;
    }


    public void increaseBookIssue() {
        booksIssue = booksIssue + 1;
    }

    public void decreaseBookIssue() {
        if (booksIssue > 0) {
            booksIssue = booksIssue - 1;
        }
    }

    public void increaseCurrentBorrow() {
        currentBorrow = currentBorrow + 1;
    }

    public void decreaseCurrentBorrow() {
        if (currentBorrow > 0) {
            currentBorrow = currentBorrow - 1;
        }
    }

    public Member(String name, long id, String adress, String phoneNo) {
        super(name);
        this.membershipDate = new Date();
        this.id = id;
        this.adress = adress;
        this.phoneNo = phoneNo;
    }

    public void setCurrentBorrow(int currentBorrow) {
        this.currentBorrow = currentBorrow;
    }

    public int getCurrentBorrow() {
        return currentBorrow;
    }


    public long getId() {
        return id;
    }

    public int getBooksIssue() {
        return booksIssue;
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public Set<Book> getPurchasedBooks() {
        return purchasedBooks;
    }

    public Set<Book> getBorrowBooks() {
        return borrowBooks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBooksIssue(int booksIssue) {
        this.booksIssue = booksIssue;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPurchasedBooks(Set<Book> purchasedBooks) {
        this.purchasedBooks = purchasedBooks;
    }

    public void setBorrowBooks(Set<Book> borrowBooks) {
        this.borrowBooks = borrowBooks;
    }

    public void purchaseBook() {

    }

    public boolean borrowBook(Book book) {
        if (currentBorrow >= maxLimit) {
            System.out.println(book.getBookName() + "kitap alınamaz, kitap limiti aşıldı!");
            return false;
        }
            book.setStatus(Status.KİRALANDI);
            book.setOwnerMember(this);
            borrowBooks.add(book);
            increaseCurrentBorrow();
            increaseBookIssue();

            System.out.println("📚 " + book.getBookName() + " başarıyla ödünç alındı.");
            System.out.println("💰 Fatura: " + book.getPrice() + " TL");

            return true;

    }

    public boolean returnBook(Book book) {
        if (borrowBooks.contains(book)) {
            borrowBooks.remove(book);
            book.setStatus(Status.MEVCUT);
            book.setOwnerMember(null);

            decreaseCurrentBorrow();
            decreaseBookIssue();

            System.out.println("📥 " + book.getBookName() + " başarıyla iade edildi.");
            System.out.println("💸 İade edilen ücret: " + book.getPrice() + " TL");

            return true;
        } else {
            System.out.println("Bu kitap bu kullanıcıya ait değil.");
            return false;
        }
    }

    public void showBorrowedBooks() {
        if (borrowBooks.isEmpty()) {
            System.out.println("Şu anda ödünç alınmış kitap yok.");
        } else {
            System.out.println("Ödünç alınan kitaplar:");
            borrowBooks.forEach(book -> System.out.println("• " + book.getBookName()));
        }
    }

    public void showPurchasedBooks() {
        if (purchasedBooks.isEmpty()) {
            System.out.println("🛒 Satın alınan kitap yok.");
        } else {
            System.out.println("🛒 Satın alınan kitaplar:");
            purchasedBooks.forEach(book -> book.toString());
        }
    }

    public void showAllBooks() {
        System.out.println("📚 Tüm Kitaplarınız:");
        showBorrowedBooks();
        showPurchasedBooks();
    }

    @Override
    void whoyouare() {
        System.out.println("Kullanıcnın adı:" + super.getName());
    }
}
