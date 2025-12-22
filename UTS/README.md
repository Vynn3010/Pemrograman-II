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

## UML Diagram Konektivitas
<img width="1087" height="897" alt="image" src="https://github.com/user-attachments/assets/a0b0a9d8-976d-49cd-8bf4-42ac86a65c23" />

## LINK YOUTUBE :

## Hasil GUI dari project
## 1. Tampilan Awal Panel Login
   <img width="800" height="647" alt="image" src="https://github.com/user-attachments/assets/673e5e13-ccbc-46c7-a02b-cf4b5503af04" />
   
## 2. Tampilan jika login sebagai Admin
   <img width="904" height="861" alt="Screenshot 2025-12-22 211423" src="https://github.com/user-attachments/assets/8694eb2b-fe6e-49a8-be91-594a5b2f2dde" />
   <img width="905" height="861" alt="image" src="https://github.com/user-attachments/assets/c1d2e882-f2b1-499e-b8e3-0232a22b9ce6" />
   
## 3. Tampilan jika login sebagai Tamu
   <img width="933" height="888" alt="Screenshot 2025-12-22 212800" src="https://github.com/user-attachments/assets/bf9e659e-2adf-477a-99e0-1b48a2090d74" />
   <img width="932" height="892" alt="image" src="https://github.com/user-attachments/assets/6b1ce382-da39-4fea-af32-833dea92caa0" />
   
## 4. Tampilan jika ingin beli akun dan membayar
   <img width="505" height="739" alt="image" src="https://github.com/user-attachments/assets/eb3c5510-6168-4ab8-b67c-3594b5bd550a" />
   <img width="511" height="739" alt="image" src="https://github.com/user-attachments/assets/1ef7c342-3f3e-4ed9-b859-d87223326f9b" />
   <img width="504" height="740" alt="image" src="https://github.com/user-attachments/assets/c4aa02e4-c986-4f3a-90f2-fae3a0191d15" />
   
## 5. Tampilan jika login sebagai Tamu dan sudah membayar akun yang dibeli
   <img width="932" height="890" alt="image" src="https://github.com/user-attachments/assets/42829fa9-5c89-48a2-af67-6c04448b78b1" />
   
## 6. Tampilan jika login sebagai Tamu yang ingin mencari stok akun
   <img width="932" height="888" alt="image" src="https://github.com/user-attachments/assets/fd7c016c-d106-43ad-a637-7a01531687ff" />
   <img width="935" height="892" alt="image" src="https://github.com/user-attachments/assets/cad8ab13-ed3a-4be3-9063-6de7d35cbeb1" />
   
## 7. Tampilan Swing Menus
   <img width="429" height="882" alt="image" src="https://github.com/user-attachments/assets/c9814c2e-131c-43cc-b18d-40f6f85dc4a1" />
   <img width="431" height="885" alt="image" src="https://github.com/user-attachments/assets/dce8912f-79c5-44b6-81d7-ca1d1835fac2" />

   

