import enums.Category;
import enums.Status;

import java.util.*;

public class LibraryService {
    private final Library library;
    private final Scanner input;

    public LibraryService(Library library, Scanner input) {
        this.library = library;
        this.input = input;
    }

    public void kitapEkle() {

        System.out.print("Kitap ID: ");
        long bookId = input.nextLong();
        input.nextLine();

        Author selectedAuthor = null;

        System.out.println("Yazar Seçimi:");
        System.out.println("1. Mevcut yazardan seç");
        System.out.println("2. Yeni yazar oluştur");
        System.out.print("Seçiminiz: ");
        int authorChoice = input.nextInt();
        input.nextLine();

        if (authorChoice == 1 && !library.getAuthorList().isEmpty()) {
            List<Author> authors = new ArrayList<>(library.getAuthorList());
            for (int i = 0; i < authors.size(); i++) {
                System.out.println((i + 1) + ". " + authors.get(i).getName());
            }
            System.out.print("Seçmek istediğiniz yazarın numarası: ");
            int selectedIndex = input.nextInt();
            input.nextLine();

            if (selectedIndex > 0 && selectedIndex <= authors.size()) {
                selectedAuthor = authors.get(selectedIndex - 1);
            } else {
                System.out.println("❌ Geçersiz seçim. Yeni yazar oluşturulacak.");
            }
        }

        if (selectedAuthor == null) {
            System.out.print("Yeni yazar ismi girin: ");
            String newAuthorName = input.nextLine();
            selectedAuthor = new Author(newAuthorName);
            library.addAuthor(selectedAuthor);
        }


        System.out.print("Kitap Adı: ");
        String bookName = input.nextLine();

        System.out.println("Kategori Seçiniz:");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Kategori numarası: ");
        int categoryIndex = input.nextInt() - 1;
        input.nextLine();

        Category category;
        if (categoryIndex >= 0 && categoryIndex < categories.length) {
            category = categories[categoryIndex];
        } else {
            System.out.println("Geçersiz kategori, varsayılan olarak StudyBooks atanıyor.");
            category = Category.StudyBooks;
        }


        System.out.print("Fiyat: ");
        int price = input.nextInt();
        input.nextLine();


        System.out.print("Durum Seçiniz: " + "\n");
        Status[] statusValues = Status.values();

        for (int i = 0; i < statusValues.length; i++) {
            System.out.println((i + 1) + ". " + statusValues[i]);
        }

        int statusIndex = input.nextInt() - 1;
        input.nextLine();

        Status selectedStatus;
        if (statusIndex >= 0 && statusIndex < statusValues.length) {
            selectedStatus = statusValues[statusIndex];
        } else {
            System.out.println("Geçersiz seçim, varsayılan olarak MEVCUT atanıyor.");
            selectedStatus = Status.MEVCUT;
        }


        System.out.print("Baskı No: ");
        int edition = input.nextInt();
        input.nextLine();

        Date dateOfPurchase = new Date();

        Book book = new Book(bookId, selectedAuthor, bookName, category, price, selectedStatus, edition, dateOfPurchase);
        library.getBookList().add(book);
        selectedAuthor.newBook(book);

        System.out.println("✅ Kitap başarıyla eklendi.");

    }

    public void kitapListele() {

        System.out.println("Kitapları neye göre listemek istiyorsunuz?");
        System.out.println("1- Kütüphanede ki tüm kitaplar.");
        System.out.println("2- Yazarın tüm kitapları.");
        System.out.println("3- Kategoride ki  tüm kitaplar");
        int listStyle = input.nextInt();
        if (listStyle == 1) {
            System.out.println("\n📚 Tüm Kitapların Listesi:");
            for (Book b : library.getBookList()) {
                System.out.println(b);
            }
        } else if (listStyle == 2) {
            System.out.println("Yazar seç");
            input.nextLine();
            Author selectedAuthor = null;

            if (!library.getAuthorList().isEmpty()) {
                List<Author> authors = new ArrayList<>(library.getAuthorList());
                for (int i = 0; i < authors.size(); i++) {
                    System.out.println((i + 1) + ". " + authors.get(i).getName());
                }
                System.out.print("Seçmek istediğiniz yazarın numarası: ");
                int selectedIndex = input.nextInt();
                input.nextLine();

                if (selectedIndex > 0 && selectedIndex <= authors.size()) {
                    selectedAuthor = authors.get(selectedIndex - 1);
                    selectedAuthor.showBooks();
                } else {
                    System.out.println("Geçersiz komut.");
                }
            }
        } else if (listStyle == 3) {
            if (!library.getBookList().isEmpty()) {
                System.out.println("Lütfen bir kategori seçiniz:");
                Category[] categories = Category.values();
                for (int i = 0; i < categories.length; i++) {
                    System.out.println((i + 1) + ". " + categories[i]);
                }

                int selected = input.nextInt();
                input.nextLine(); // buffer temizliği

                if (selected > 0 && selected <= categories.length) {
                    Category selectedCategory = categories[selected - 1];
                    List<Book> booksInCategory = library.getBooksByCategory(selectedCategory);
                    if (booksInCategory.isEmpty()) {
                        System.out.println("Bu kategoride henüz kitap yok.");
                    } else {
                        System.out.println("\n📂 " + selectedCategory + " kategorisindeki kitaplar:");
                        for (Book book : booksInCategory) {
                            System.out.println(book.toString());
                        }
                    }
                } else {
                    System.out.println("Geçersiz kategori seçimi.");
                }
            } else {
                System.out.println("Henüz hiç kitap eklenmemiş.");
            }
        } else {
            System.out.println("Geçersiz komut.");
        }

    }

    public void kitapSil() {
        System.out.print("Silmek istediğiniz kitabın ID'sini yazın: ");
        long deleteId = input.nextLong();
        input.nextLine();

        library.removeBookById(deleteId);

    }

    public void kitapGuncelle() {
        if (library.getBookList().isEmpty()) {
            System.out.println("Henüz hiç kitap eklenmemiş.");

        }

        System.out.println("Güncellemek istediğiniz kitabı seçin:");
        for (int i = 0; i < library.getBookList().size(); i++) {
            Book b = library.getBookList().get(i);
            System.out.println((i + 1) + ". " + b.getBookName() + " (ID: " + b.getBookId() + ")");
        }

        System.out.print("Seçim numarası: ");
        int selected = input.nextInt();
        input.nextLine();

        if (selected < 1 || selected > library.getBookList().size()) {
            System.out.println("❌ Geçersiz seçim.");
        }

        Book selectedBook = library.getBookList().get(selected - 1);
        System.out.println("Seçilen Kitap: " + selectedBook.getBookName());

        // Yeni bilgiler alınıyor
        System.out.print("Yeni kitap adı: ");
        String newName = input.nextLine();

        System.out.print("Yeni fiyat: ");
        int newPrice = input.nextInt();
        input.nextLine();

        System.out.println("Yeni Durum Seçiniz:");
        Status[] statusValues = Status.values();
        for (int i = 0; i < statusValues.length; i++) {
            System.out.println((i + 1) + ". " + statusValues[i]);
        }

        System.out.print("Seçiminiz: ");
        int statusIndex = input.nextInt() - 1;
        input.nextLine();

        Status newStatus;
        if (statusIndex >= 0 && statusIndex < statusValues.length) {
            newStatus = statusValues[statusIndex];
        } else {
            System.out.println("Geçersiz seçim, varsayılan olarak MEVCUT atanıyor.");
            newStatus = Status.MEVCUT;
        }

        System.out.print("Yeni baskı no: ");
        int newEdition = input.nextInt();
        input.nextLine();

        System.out.println("Yeni kategori seç:");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        int newCatIndex = input.nextInt() - 1;
        input.nextLine();

        Category newCategory = (newCatIndex >= 0 && newCatIndex < categories.length) ? categories[newCatIndex] : Category.StudyBooks;

        Author newAuthor = null;
        System.out.println("Yazar Seçimi:");
        System.out.println("1. Mevcut yazardan seç");
        System.out.println("2. Yeni yazar oluştur");
        int yazarSecim = input.nextInt();
        input.nextLine();

        if (yazarSecim == 1 && !library.getAuthorList().isEmpty()) {
            for (int i = 0; i < library.getAuthorList().size(); i++) {
                System.out.println((i + 1) + ". " + library.getAuthorList().get(i).getName());
            }
            System.out.print("Yazar numarası: ");
            int authorIndex = input.nextInt();
            input.nextLine();
            if (authorIndex >= 1 && authorIndex <= library.getAuthorList().size()) {
                newAuthor = library.getAuthorList().get(authorIndex - 1);
            } else {
                System.out.println("❌ Geçersiz seçim. Yeni yazar oluşturulacak.");
            }
        }

        if (newAuthor == null) {
            System.out.print("Yeni yazar ismi girin: ");
            String authorName = input.nextLine();
            newAuthor = new Author(authorName);
            library.getAuthorList().add(newAuthor);
        }

        Book updatedBook = new Book(
                selectedBook.getBookId(),
                newAuthor,
                newName,
                newCategory,
                newPrice,
                newStatus,
                newEdition,
                selectedBook.getDateOfPurchase()
        );

        library.getBookList().set(selected - 1, updatedBook);

        System.out.println("✅ Kitap başarıyla güncellendi.");
    }

    public void kitapSec() {
        System.out.println("Hangi değere göre kitap seçmek istiyorsunuz?");
        System.out.println("1- ID'ye göre kitap seç.");
        System.out.println("2- İsme göre kitap seç.");
        System.out.println("3- Yazara göre kitap seç.");
        int secenek = input.nextInt();
        input.nextLine();

        if (secenek == 1) {
            System.out.print("Kitap ID giriniz: ");
            int id = input.nextInt();
            input.nextLine();

            boolean bulundu = false;
            for (Book b : library.getBookList()) {
                if (b.getBookId() == id) {
                    System.out.println(b);
                    bulundu = true;
                    break;
                }
            }
            if (!bulundu) System.out.println("Bu ID'ye sahip kitap bulunamadı.");
        } else if (secenek == 2) {
            System.out.print("Kitap adını giriniz: ");
            String isim = input.nextLine();

            boolean bulundu = false;
            for (Book b : library.getBookList()) {
                if (b.getBookName().equalsIgnoreCase(isim)) {
                    System.out.println(b);
                    bulundu = true;
                }
            }
            if (!bulundu) System.out.println("Bu isme sahip kitap bulunamadı.");
        } else if (secenek == 3) {
            System.out.println("Yazarlar:");
            List<Author> yazarListesi = library.getAuthorList();
            for (int i = 0; i < yazarListesi.size(); i++) {
                System.out.println((i + 1) + "- " + yazarListesi.get(i).getName());
            }

            System.out.print("Yazarı seçiniz: ");
            int yazarSecim = input.nextInt();
            input.nextLine();

            if (yazarSecim < 1 || yazarSecim > yazarListesi.size()) {
                System.out.println("Geçersiz seçim.");
            } else {
                Author secilenYazar = yazarListesi.get(yazarSecim - 1);
                List<Book> yazarKitaplari = new ArrayList<>();

                for (Book b : library.getBookList()) {
                    if (b.getAuthor().equals(secilenYazar)) {
                        yazarKitaplari.add(b);
                    }
                }

                if (yazarKitaplari.isEmpty()) {
                    System.out.println("Bu yazara ait kitap bulunamadı.");
                } else {
                    System.out.println("\n" + secilenYazar.getName() + " adlı yazara ait kitaplar:");
                    for (int i = 0; i < yazarKitaplari.size(); i++) {
                        System.out.println((i + 1) + "- " + yazarKitaplari.get(i).getBookName());
                    }

                    System.out.print("Bir kitap seçiniz: ");
                    int kitapSecim = input.nextInt();
                    input.nextLine();

                    if (kitapSecim < 1 || kitapSecim > yazarKitaplari.size()) {
                        System.out.println("Geçersiz kitap seçimi.");
                    } else {
                        Book secilenKitap = yazarKitaplari.get(kitapSecim - 1);
                        System.out.println("\nSeçilen Kitap Bilgisi:");
                        System.out.println(secilenKitap);
                    }
                }
            }
        }
    }

}
