import java.util.HashSet;
import java.util.Set;

public class Author extends Person {

    private Set<Book> authorBooks = new HashSet<>();


    public Author(String name) {
        super(name);
    }

    @Override
   public void whoyouare() {
        System.out.println("Yazarın adı:" + super.getName());
        System.out.println("Kitap sayısı: " + authorBooks.size());
    }

    public void removeBook(Book book) {
authorBooks.remove(book);    }

    public void newBook(Book book) {
        authorBooks.add(book);
    }

    public void showBooks() {
        if (authorBooks.isEmpty()) {
            System.out.println(getName() + " henüz bir kitabı yok.");

        } else {

            System.out.println("\n" + getName() + " tarafından yazılan kitaplar:");
            for (Book book : authorBooks) {
                System.out.println(book.toString());
            }
        }
    }
}
