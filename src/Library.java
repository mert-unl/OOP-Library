import enums.Category;

import java.util.*;

public class Library {

    private List<Author> authorList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();
    private Set<Member> memberList = new HashSet<>();

    public void addMember(Member member) {
        if (!memberList.contains(member)) {
            memberList.add(member);
        }
    }

    public Set<Member> getMemberList() {
        return memberList;
    }



    private Map<Category, List<Book>> categoryBookMap = new HashMap<>();

    public List<Author> getAuthorList() {
        return authorList;
    }

    public List<Book> getBookList() {
        return bookList;
    }


    public void addAuthor(Author author) {
        if (!authorList.contains(author)) {
            authorList.add(author);
        }
    }

    public void addBook(Book book) {
        if (!bookList.contains(book)) {
            bookList.add(book);
            categoryBookMap.computeIfAbsent(book.getCategory(), k -> new ArrayList<>()).add(book);
            book.getAuthor().newBook(book);
        }
    }

    public boolean removeBookById(long bookId) {
        Optional<Book> bookToRemove = bookList.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst();

        if (bookToRemove.isPresent()) {
            Book book = bookToRemove.get();
            bookList.remove(book);

            List<Book> categoryBooks = categoryBookMap.get(book.getCategory());
            if (categoryBooks != null) {
                categoryBooks.remove(book);

            }

            book.getAuthor().removeBook(book);

            System.out.println("\n✅" + book.getBookName() + ", adlı kitap başarıyla silindi.");
            return true;
        }

        System.out.println("❌ Bu ID'ye ait bir kitap bulunamadı.");
        return false;
    }

    public List<Book> getBooksByCategory(Category category) {
        return categoryBookMap.getOrDefault(category, new ArrayList<>());
    }


    private void rebuildCategoryMap() {
        categoryBookMap.clear();
        for (Book book : bookList) {
            categoryBookMap.computeIfAbsent(book.getCategory(), k -> new ArrayList<>()).add(book);
        }
    }
}
