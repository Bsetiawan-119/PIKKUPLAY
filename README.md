# PIKKUPLAY

PIKKUPLAY adalah aplikasi mini game desktop berbasis Java Swing yang menggabungkan tiga permainan dalam satu aplikasi, yaitu **Tebak-Tebakkan**, **Game Tebak Kata**, dan **Game Scrabble**.

Aplikasi ini dibuat dengan fokus pada tampilan yang sederhana, navigasi yang mudah dipahami, serta mekanisme permainan yang berbeda pada setiap game. Menu utama digunakan sebagai pusat navigasi agar pengguna dapat memilih permainan tanpa harus menjalankan file Java satu per satu.

## Daftar Permainan

### Tebak-Tebakkan

Game ini berisi kumpulan pertanyaan tebak-tebakan yang ditampilkan secara acak.

Fitur utama:

- Terdapat 10 pertanyaan.
- Urutan soal diacak setiap kali permainan dimulai.
- Setiap soal memiliki 3 nyawa.
- Jawaban yang benar akan menambah skor.
- Jawaban yang salah akan mengurangi nyawa.
- Riwayat jawaban ditampilkan selama permainan.
- Setelah seluruh soal selesai, aplikasi menampilkan skor akhir dan total percobaan.
- Pemain dapat memilih untuk bermain kembali atau kembali ke menu utama.

### Game Tebak Kata

Game ini meminta pemain menebak sebuah kata berdasarkan petunjuk yang diberikan.

Tersedia dua kategori:

- Hewan
- Buah

Fitur utama:

- Pemain memilih kategori sebelum permainan dimulai.
- Setiap stage menampilkan satu pertanyaan atau petunjuk.
- Jawaban diketik melalui kolom input.
- Jika tebakan salah, huruf bantuan akan terbuka secara bertahap.
- Riwayat tebakan ditampilkan selama permainan.
- Skor dan jumlah percobaan dicatat sampai permainan selesai.
- Setelah stage terakhir, aplikasi menampilkan hasil akhir permainan.

### Game Scrabble

Game ini menampilkan sebuah kata yang hurufnya sudah diacak. Pemain harus menyusun kembali huruf tersebut menjadi kata yang benar.

Fitur utama:

- Setiap stage memiliki satu kata yang sudah diacak.
- Setiap stage dimulai dengan 2 nyawa.
- Tebakan yang salah akan mengurangi nyawa.
- Clue akan muncul untuk membantu pemain.
- Jawaban yang benar akan menambah skor.
- Skor dan jumlah nyawa ditampilkan selama permainan.
- Setelah seluruh stage selesai, aplikasi menampilkan skor akhir dan jumlah soal yang berhasil dijawab.

## Teknologi yang Digunakan

Project ini dibuat menggunakan:

- Java
- Java Swing
- Java AWT
- Visual Studio Code
- JDK
- Custom Swing Component
- ImageIO untuk membaca background image

## Struktur Project

File utama pada project ini terdiri dari:

- `src/MenuUtama1.java` sebagai menu utama aplikasi.
- `src/TebakTebakkan.java` untuk game Tebak-Tebakkan.
- `src/GameTebakKata.java` untuk game Tebak Kata.
- `src/GameScrabble.java` untuk game Scrabble.
- `bin/` sebagai folder hasil compile file Java.
- `.vscode/settings.json` sebagai konfigurasi project Java di Visual Studio Code.
- `bg_pikku.jpg` sebagai background menu utama.
- `bg_pikku2.jpg` sebagai background game Tebak-Tebakkan.
- `bg_pikku3.jpg` sebagai background Game Tebak Kata.
- `bg_pikku4.jpg` sebagai background Game Scrabble.
- `Poppins-Bold.ttf` sebagai font tambahan yang digunakan pada tampilan aplikasi.
- `README.md` sebagai dokumentasi project.

## Konfigurasi Visual Studio Code

Agar file hasil compile disimpan ke folder `bin`, file `.vscode/settings.json` menggunakan konfigurasi berikut:

```json
{
    "java.project.sourcePaths": [
        "src"
    ],
    "java.project.outputPath": "bin"
}
```

Dengan konfigurasi tersebut, source code tetap berada di folder `src`, sedangkan file `.class` hasil compile berada di folder `bin`.

## Persyaratan

Sebelum menjalankan project, pastikan Java Development Kit sudah terpasang.

Cek instalasi Java dengan perintah:

```bash
java -version
```

Cek Java compiler dengan perintah:

```bash
javac -version
```

Jika kedua perintah menampilkan versi Java, project sudah dapat dikompilasi dan dijalankan.

## Cara Menjalankan Project

### 1. Buka folder project

Buka folder utama `PIKKUPLAY` menggunakan Visual Studio Code.

Folder yang dibuka harus folder utama project, bukan folder `src`.

Contoh lokasi terminal yang benar:

```powershell
PS C:\PIKKUPLAY>
```

### 2. Buka terminal

Pada Visual Studio Code, pilih menu:

```text
Terminal
New Terminal
```

### 3. Compile seluruh source code

Jalankan perintah berikut dari folder utama project:
powershell
```
javac -d bin src\*.java
```

Perintah tersebut akan mengompilasi seluruh file `.java` yang ada di folder `src` dan menyimpan hasil compile ke folder `bin`.

Jika proses compile berhasil, terminal tidak akan menampilkan pesan error.

### 4. Jalankan aplikasi

Setelah proses compile selesai, jalankan:

```powershell
java -cp bin MenuUtama1
```

Aplikasi akan membuka halaman utama PIKKUPLAY.

Class utama yang digunakan untuk menjalankan program adalah:

```text
MenuUtama1
```

## Menjalankan Ulang Setelah Mengubah Kode

Jika ada perubahan pada source code, lakukan compile ulang sebelum menjalankan program.

Gunakan:

```powershell
javac -d bin src\*.java
```

Kemudian jalankan kembali:

```powershell
java -cp bin MenuUtama1
```

Dua perintah tersebut merupakan perintah utama yang digunakan selama proses pengembangan project.

## Cara Menggunakan Aplikasi

Setelah aplikasi terbuka, menu utama akan menampilkan tiga pilihan:

- Tebak-Tebakkan
- Game Tebak Kata
- Game Scrabble

Klik salah satu tombol untuk membuka permainan yang dipilih.

Pada Game Tebak Kata, aplikasi akan meminta pemain memilih kategori Hewan atau Buah terlebih dahulu.

Setiap permainan memiliki mekanisme skor, stage, nyawa, atau riwayat permainan sesuai dengan jenis game yang dimainkan.

Setelah permainan selesai, pemain dapat menggunakan tombol yang tersedia untuk kembali ke menu utama atau mengakhiri permainan.

## Cara Menutup Aplikasi

Untuk menutup aplikasi, gunakan tombol **X** pada title bar di bagian kanan atas jendela aplikasi. Tombol tersebut sama seperti tombol yang biasa digunakan untuk menutup jendela aplikasi di Windows.

Setelah tombol **X** ditekan, aplikasi akan menampilkan dialog konfirmasi.

Pilih:

- **Ya, Keluar** jika ingin benar-benar menutup aplikasi.
- **Batal** jika ingin kembali ke aplikasi.

Disarankan menggunakan tombol **X** pada bagian kanan atas dan mengikuti dialog konfirmasi yang tersedia agar aplikasi ditutup melalui mekanisme yang memang sudah dibuat di dalam program.

Pada beberapa halaman permainan juga tersedia tombol **Keluar** atau **Menu Utama**. Tombol tersebut dapat digunakan sesuai kebutuhan ketika permainan sedang berlangsung.

## File Resource

Project menggunakan beberapa file tambahan agar tampilan aplikasi dapat berjalan dengan benar.

File berikut harus tetap berada di folder utama project:

```text
bg_pikku.jpg
bg_pikku2.jpg
bg_pikku3.jpg
bg_pikku4.jpg
Poppins-Bold.ttf
```

Jangan memindahkan atau mengganti nama file tersebut tanpa menyesuaikan path yang digunakan di dalam source code.

Jika file background tidak ditemukan, tampilan aplikasi dapat kehilangan gambar latar.

Jika file font tidak ditemukan, beberapa bagian program akan menggunakan font bawaan Java sebagai pengganti.

## Catatan Penggunaan

- Jalankan project dari folder utama `PIKKUPLAY`.
- Jangan menjalankan project dari dalam folder `src`.
- Simpan seluruh file source code Java di folder `src`.
- Simpan hasil compile di folder `bin`.
- Jangan memindahkan file resource tanpa mengubah path pada source code.
- Jika ada perubahan kode, compile ulang sebelum menjalankan program.
- Entry point aplikasi adalah `MenuUtama1`.

## Ringkasan File Java

### `MenuUtama1.java`

Berfungsi sebagai halaman utama aplikasi dan pusat navigasi ke seluruh game.

File ini menangani:

- Tampilan menu utama.
- Tombol menuju setiap game.
- Dialog pemilihan tema pada Game Tebak Kata.
- Dialog konfirmasi ketika aplikasi akan ditutup.
- Efek tampilan pada menu utama.

### `TebakTebakkan.java`

Berisi seluruh logika dan tampilan untuk game Tebak-Tebakkan.

File ini menangani:

- Daftar pertanyaan dan jawaban.
- Pengacakan urutan soal.
- Sistem nyawa.
- Sistem skor.
- Riwayat jawaban.
- Hasil akhir permainan.

### `GameTebakKata.java`

Berisi seluruh logika dan tampilan untuk Game Tebak Kata.

File ini menangani:

- Pemilihan data berdasarkan kategori.
- Sistem stage.
- Input tebakan.
- Bantuan huruf.
- Riwayat tebakan.
- Perhitungan skor dan percobaan.
- Tampilan hasil akhir.

### `GameScrabble.java`

Berisi seluruh logika dan tampilan untuk Game Scrabble.

File ini menangani:

- Pengacakan huruf.
- Input jawaban.
- Sistem nyawa.
- Sistem clue.
- Sistem skor.
- Riwayat permainan.
- Tampilan hasil akhir.

## Tentang Project

PIKKUPLAY dikembangkan sebagai project aplikasi desktop berbasis Java dengan penerapan antarmuka grafis dan pemrograman berorientasi objek.

Project ini menggunakan beberapa konsep utama Java, seperti class, object, inheritance dari komponen Swing, event handling, collection, custom component, file resource, dan pengelolaan tampilan menggunakan layout manager.

Seluruh game dibuat agar tetap terhubung dalam satu aplikasi sehingga pengguna dapat berpindah dari menu utama ke permainan yang dipilih tanpa harus menjalankan class secara terpisah.
