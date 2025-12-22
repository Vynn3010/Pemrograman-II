# UTS SEMESETER 3 MATA KULIAH PEMROGRAMAN 2
# Game Account Manager
Java GUI Swing – NetBeans 8.2

## -Deskripsi Project
Project ini saya buat berdasarkan penugasan UTS dengan tema Game Account Manager. Aplikasi ini saya buat untuk memudahkan pengelolaan data akun game secara sederhana dan terstruktur. Project ini dikembangkan menggunakan Java GUI Swing dengan NetBeans IDE 8.2, serta menerapkan konsep Object Oriented Programming (OOP). Melalui aplikasi ini, pengguna dapat mengelola akun game berdasarkan peran Admin dan Buyer (Tamu), mulai dari login, melihat daftar akun, hingga simulasi pembelian akun game. Project ini dibuat sebagai sarana belajar dan latihan dalam membuat aplikasi desktop menggunakan Java Swing.

## -Java GUI Swing
Aplikasi ini menggunakan berbagai komponen Java Swing, antara lain:
- Containers
Digunakan untuk menampung dan mengatur komponen lain, seperti JFrame, JPanel, dan JScrollPane.
- Controls
Digunakan sebagai media interaksi pengguna, seperti JButton, JTextField, JPasswordField, JComboBox, dan JLabel.
- Menus
Digunakan sebagai navigasi aplikasi menggunakan JMenuBar, JMenu, dan JMenuItem untuk fitur seperti Logout, Exit, Change Akun, dan About.

## -Object Oriented Programming (OOP)
Project ini menerapkan beberapa konsep utama OOP, yaitu:
- Inheritance (Pewarisan)
Class antarmuka seperti LoginFrame, MainMenuFrame, MainMenuTamu, dan PaymentFrame mewarisi class JFrame.
- Encapsulation (Enkapsulasi)
Atribut pada class AkunGame dibuat bersifat private dan diakses melalui getter dan setter.
- Polymorphism (Polimorfisme)
Digunakan untuk membedakan perilaku dan tampilan fitur berdasarkan peran Admin dan Buyer.
- Method Berparameter
Method menerima parameter objek, seperti createCardPanel(AkunGame akun) dan constructor PaymentFrame(MainMenuTamu parent, AkunGame akun).
- Objek dalam Class
Data akun game direpresentasikan sebagai objek AkunGame dan disimpan dalam ArrayList pada class SystemManager.

## -Alur Kerja Singkat Aplikasi
- 1.Aplikasi dimulai dari LoginFrame
- 2.Pengguna login sebagai Admin atau Buyer
- 3.Admin mengelola data akun game
- 4.Buyer membeli akun melalui PaymentFrame
- 5.Setelah pembayaran berhasil, status akun berubah menjadi Sold Out
