import enums.Category;
import enums.Status;

import java.util.*;
import java.util.stream.Collectors;

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
                input.nextLine();

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

    public void kitapSec(Member uye) {
        System.out.println("Hangi değere göre kitap seçmek istiyorsunuz?");
        System.out.println("1- ID'ye göre kitap seç.");
        System.out.println("2- İsme göre kitap seç.");
        System.out.println("3- Yazara göre kitap seç.");
        int secenek = input.nextInt();
        input.nextLine();

        Book secilenKitap = null;

        switch (secenek) {
            case 1:
                System.out.print("Kitap ID giriniz: ");
                int id = input.nextInt();
                input.nextLine();
                for (Book b : library.getBookList()) {
                    if (b.getBookId() == id) {
                        secilenKitap = b;
                        break;
                    }
                }
                break;

            case 2:
                System.out.print("Kitap adını giriniz: ");
                String isim = input.nextLine();
                for (Book b : library.getBookList()) {
                    if (b.getBookName().equalsIgnoreCase(isim)) {
                        secilenKitap = b;
                        break;
                    }
                }
                break;

            case 3:
                List<Author> yazarListesi = library.getAuthorList();
                for (int i = 0; i < yazarListesi.size(); i++) {
                    System.out.println((i + 1) + "- " + yazarListesi.get(i).getName());
                }
                System.out.print("Yazarı seçiniz: ");
                int yazarSecim = input.nextInt();
                input.nextLine();

                if (yazarSecim >= 1 && yazarSecim <= yazarListesi.size()) {
                    Author secilenYazar = yazarListesi.get(yazarSecim - 1);
                    List<Book> yazarKitaplari = library.getBookList().stream()
                            .filter(b -> b.getAuthor().equals(secilenYazar))
                            .collect(Collectors.toList());

                    if (!yazarKitaplari.isEmpty()) {
                        for (int i = 0; i < yazarKitaplari.size(); i++) {
                            System.out.println((i + 1) + "- " + yazarKitaplari.get(i).getBookName());
                        }
                        System.out.print("Bir kitap seçiniz: ");
                        int kitapSecim = input.nextInt();
                        input.nextLine();

                        if (kitapSecim >= 1 && kitapSecim <= yazarKitaplari.size()) {
                            secilenKitap = yazarKitaplari.get(kitapSecim - 1);
                        }
                    }
                }
                break;

            default:
                System.out.println("Geçersiz seçim!");
                return;
        }

        if (secilenKitap != null) {
            System.out.println("\nSeçilen Kitap:");
            System.out.println(secilenKitap);
            kitapIslemMenusu(uye, secilenKitap);
        } else {
            System.out.println("Kitap bulunamadı.");
        }
    }

    private void kitapIslemMenusu(Member uye, Book kitap) {
        while (true) {
            System.out.println("\nKitap İşlemleri:");
            System.out.println("Ödünç alınan kitap sayısı:" + uye.getBorrowBooks().size());
            System.out.println("Satın alınan kitap sayısı:" + uye.getPurchasedBooks().size());

            System.out.println("1- Ödünç Al");
            System.out.println("2- Satın Al");
            System.out.println("3- Ödünç Alınan Kitapları Listele");
            System.out.println("4- Satın Alınan Kitapları Litele");
            System.out.println("5- Tüm Kitaplarını Listele");
            System.out.println("6- Ana Menüye Dön");

            int secim = input.nextInt();
            input.nextLine();

            switch (secim) {
                case 1:
                    if (kitap.getStatus() == Status.MEVCUT && uye.getCurrentBorrow() < uye.getMaxLimit()) {
                        kitap.setStatus(Status.KİRALANDI);
                        kitap.setOwnerMember(uye);
                        uye.borrowBook(kitap);
                        uye.increaseCurrentBorrow();
                        System.out.println("Kitap ödünç alındı.");
                    } else {
                        System.out.println("Kitap mevcut değil veya kitap limiti dolu.");
                    }
                    break;

                case 2:
                    if (kitap.getStatus() == Status.MEVCUT) {
                        kitap.setStatus(Status.SATILDI);
                        kitap.setOwnerMember(uye);
                        uye.getPurchasedBooks().add(kitap);
                        System.out.println("Kitap satın alındı. Fatura oluşturuldu.");
                    } else {
                        System.out.println("Kitap mevcut değil.");
                    }
                    break;

                case 3:
                    uye.showBorrowedBooks();
                case 4:
                    uye.showPurchasedBooks();

                case 5:
                    uye.showAllBooks();
                case 6:
                    return;

                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }


    public Member uyeEkle() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Üye adı: ");
        String name = scanner.nextLine();

        System.out.print("Üye ID: ");
        long id = 0;
        while (true) {
            try {
                id = Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Geçersiz ID. Lütfen tekrar sayı girin: ");
            }
        }

        System.out.print("Adres: ");
        String adress = scanner.nextLine();

        System.out.print("Telefon numarası: ");
        String phoneNo = scanner.nextLine();

        Member newMember = new Member(name, id, adress, phoneNo);

        library.addMember(newMember);
        System.out.println("\n✅ Üye başarıyla eklendi: " + name);
        return newMember;
    }


    public void uyeleriGetir() {
        for (Member member : library.getMemberList()) {
            System.out.println(member.toString());
        }
    }

    public void uyeSec(Member aktifUye) {
        Set<Member> members = library.getMemberList();
        if (members.isEmpty()) {
            System.out.println("📭 Hiç üye yok. Yeni üye oluşturuluyor...");
            aktifUye = uyeEkle();
        } else {
            System.out.println("\n📋 Kayıtlı Üyeler:");
            for (Member member : members) {
                System.out.println("ID: " + member.getId() + " - Ad: " + member.getName());
            }

            while (aktifUye == null) {
                System.out.print("Lütfen giriş yapmak istediğiniz üyenin ID'sini girin: ");
                try {
                    long uyeId = Long.parseLong(input.nextLine());
                    Optional<Member> secilen = members.stream()
                            .filter(m -> m.getId() == uyeId)
                            .findFirst();
                    if (secilen.isPresent()) {
                        aktifUye = secilen.get();
                        System.out.println("✅ Giriş yapıldı: " + aktifUye.getName());
                    } else {
                        System.out.println("❌ Böyle bir ID bulunamadı. Tekrar deneyin.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Geçersiz sayı girdiniz!");
                }
            }
        }
    }

}
