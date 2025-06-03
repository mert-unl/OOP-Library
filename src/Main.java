
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

        library.getAuthorList().add(dostoyevski);
        library.getAuthorList().add(platon);
        library.getAuthorList().add(orwell);
        library.getAuthorList().add(herodotos);


        Member mert = new Member("mert", 1, "antalya", "5355598123");
        library.addMember(mert);

        Member cem = new Member("cem", 2, "ankara", "5372412831");
        library.addMember(cem);

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


        while (true) {
            System.out.println("\nGiriş Yap");
            System.out.println("1 - Kütüphaneci");
            System.out.println("2 - Üye");
            System.out.println("0 - Çıkış");
            System.out.print("Rol seçimi: ");
            int rolSecimi = input.nextInt();
            input.nextLine();

            if (rolSecimi == 0) {
                System.out.println("Sistemden çıkılıyor...");
                break;
            }

            Member aktifUye = null;

            if (rolSecimi == 2) {
                Set<Member> members = library.getMemberList();
                if (members.isEmpty()) {
                    System.out.println("📭 Hiç üye yok. Yeni üye oluşturuluyor...");
                    aktifUye = service.uyeEkle();
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

            while (true) {
                if (rolSecimi == 1) {
                    System.out.println("\n--- Kütüphaneci Menüsü ---");
                    System.out.println("1. 📚 Kitap Ekle");
                    System.out.println("2. 📚 Kitapları Listele");
                    System.out.println("3. 📚 ID ile Kitap Sil");
                    System.out.println("4. 📚 Kitap Güncelle");
                    System.out.println("5. 👤 Üye Ekle");
                    System.out.println("6. 👤 Üyeleri Getir");
                    System.out.println("7. 🔄 Rol Seçimine Dön");
                    System.out.println("0. ❌ Çıkış");
                    System.out.print("Seçimin: ");
                } else if (rolSecimi == 2) {
                    System.out.println("\n--- Üye Menüsü (" + aktifUye.getName() + ") ---");
                    System.out.println("1. 📚 Kitapları Listele");
                    System.out.println("2. 📚 Kitap Seç");
                    System.out.println("3. 🔄 Rol Seçimine Dön");
                    System.out.println("0. ❌ Çıkış");
                    System.out.print("Seçimin: ");
                } else {
                    System.out.println("❌ Geçersiz rol!");
                    break;
                }

                int secim = input.nextInt();
                input.nextLine();

                if (rolSecimi == 1) {
                    switch (secim) {
                        case 1 -> service.kitapEkle();
                        case 2 -> service.kitapListele();
                        case 3 -> service.kitapSil();
                        case 4 -> service.kitapGuncelle();
                        case 5 -> service.uyeEkle();
                        case 6 -> service.uyeleriGetir();
                        case 7 -> {
                            System.out.println("🔁 Rol seçimine dönülüyor...");
                            break;
                        }
                        case 0 -> {
                            System.out.println("Sistemden çıkılıyor...");
                            return;
                        }
                        default -> System.out.println("❌ Geçersiz komut!");
                    }
                } else if (rolSecimi == 2) {
                    switch (secim) {
                        case 1 -> service.kitapListele();
                        case 2 -> service.kitapSec(aktifUye);
                        case 3 -> {
                            System.out.println("🔁 Rol seçimine dönülüyor...");
                        }
                        case 0 -> {
                            System.out.println("Sistemden çıkılıyor...");
                            return;
                        }
                        default -> System.out.println("❌ Geçersiz komut!");
                    }
                }

                if ((rolSecimi == 1 && secim == 8) || (rolSecimi == 2 && secim == 3)) {
                    break;
                }
            }
        }
    }

}
