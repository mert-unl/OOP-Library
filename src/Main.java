
import enums.Category;
import enums.Status;

import java.util.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Library library = new Library();
        LibraryService service = new LibraryService(library, input);

        Author dostoyevski = new Author("Dostoyevski");
        Author platon = new Author("Platon");
        Author orwell = new Author("George Orwell");
        Author herodotos = new Author("Herodotos");

        Book book1 = new Book(1, dostoyevski, "Ahlak Üzerine Denemeler", Category.StudyBooks, 32, Status.MEVCUT, 5, new Date());
        Book book2 = new Book(2, platon, "Devlet Üzerine Makaleler", Category.Journals, 28, Status.MEVCUT, 2, new Date());
        Book book3 = new Book(3, orwell, "1984 - İnceleme", Category.Magazines, 25, Status.MEVCUT, 4, new Date());
        Book book4 = new Book(4, herodotos, "Tarihin Babası", Category.History, 21, Status.MEVCUT, 3, new Date());
        Book book5 = new Book(5, dostoyevski, "Suç ve Ceza Üzerine", Category.StudyBooks, 18, Status.MEVCUT, 2, new Date());

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        library.getAuthorList().add(dostoyevski);
        library.getAuthorList().add(platon);
        library.getAuthorList().add(orwell);
        library.getAuthorList().add(herodotos);

        while (true) {
            System.out.println("\n--- Menü ---");
            System.out.println("1. 📚 Kitap Ekle");
            System.out.println("2. 📚 Kitapları Listele");
            System.out.println("3. 📚 ID ile Kitap Sil");
            System.out.println("4. 📚 Kitap Güncelle");
            System.out.println("5. 📚 Kitap Seç");
            System.out.println("0. ❌ Çıkış");
            System.out.print("Seçimin: ");

            int secim = input.nextInt();
            input.nextLine();

            switch (secim) {
                case 1 -> service.kitapEkle();
                case 2 -> service.kitapListele();
                case 3 -> service.kitapSil();
                case 4 -> service.kitapGuncelle();
                case 5 -> service.kitapSec();
                case 0 -> {
                    System.out.println("Sistemden çıkılıyor..");
                    return;
                }
                default -> System.out.println("❌ Geçersiz komut!");
            }
        }
    }
}
