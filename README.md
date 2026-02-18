# Queens LinkedIn Solver

## Deskripsi Program
Program Tucil1 Strategi Algoritma IF2211 merupakan program algoritma untuk memecahkan game LinkedIn "Queens" dengan menggunakan pendekatan brute force yang harus memenuhi aturan:
1. Satu Ratu per baris.
2. Satu Ratu per kolom.
3. Satu Ratu per wilayah warna (region).
4. Tidak ada dua Ratu yang bersentuhan (termasuk diagonal, tergantung variasi aturan spesifik yang diimplementasikan).

Aplikasi ini memvisualisasikan papan dan solusi yang ditemukan kepada user.

## Requirement Program

*   **Java Development Kit (JDK)**: Versi 25 (Sesuai konfigurasi *project*, namun dapat disesuaikan ke versi LTS seperti 21). Cara menyesuaikan versi dengan JDK dapat mengganti kode pada 
build.gradle
```cmd
languageVersion = JavaLanguageVersion.of(25) <- ganti sesuai versi Anda 
```
*   **Gradle**: Digunakan sebagai *build tool*. Versi *wrapper* sudah disertakan dalam repositori.

## Cara Mengkompilasi Program
Program ini menggunakan Gradle untuk manajemen dependensi dan kompilasi. Pastikan berada di direktori root proyek saat menjalankan perintah berikut.

### Windows
```cmd
.\gradlew.bat build
```

### Linux / macOS
Perlu memberikan izin eksekusi pada skrip wrapper terlebih dahulu jika belum tersedia:
```bash
chmod +x gradlew
./gradlew build
```

## Cara Menjalankan dan Menggunakan Program
Setelah proses kompilasi berhasil, Anda dapat menjalankan aplikasi.

### Gradle
**Windows:**
```cmd
.\gradlew.bat run
```

**Linux / macOS:**
```bash
./gradlew run
```

### Cara Menggunakan
1.  Saat aplikasi terbuka, Anda akan diberikan pilihan untuk memilih dalam mengupload via .txt atau membuat secara manual.
2.  Pilih tombol untuk mengunggah file papan permainan (`.txt`) atau menginput manual via keybaord.
3.  Format file input `.txt` diharapkan merepresentasikan papan permainan, di mana karakter yang berbeda (huruf) mewakili wilayah warna yang berbeda.
4.  Setelah file dipilih, program akan memproses solusi via brute force dan menampilkan solusi visualisasi pada papan.

## Struktur Folder
*   `src/main/java`: Berisi kode sumber utama (Algorithm, GUI, dll).
*   `data/`: Berisi contoh file tes (`tc_1.txt`, dll).
*   `test/`: Berisi contoh solusi berdasarkan test case yang diberikan.
*   `docs/`: Berisi dokumen laporan tugas kecil 1 IF2211.

## Penulis
Anindya Naufal Pinasthika - 13524013

