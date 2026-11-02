## Janji
Saya Muhammad Rian Anugrah dengan NIM 2507241 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Struktur Direktori
```
TP6DPBO2526C2/
├── FlappyBird/
│   ├── src/
│   │   ├── App.java
│   │   ├── Logic.java
│   │   ├── View.java
│   │   ├── Player.java
│   │   ├── Pipe.java
│   │   ├── Background.java
│   │   ├── Footer.java
│   │   └── Sprite.java
│   └── assets/
│       ├── pipe.png
│       ├── bird.png
│       ├── background.png
│       └── footer.png
│
├── Documentation/
└── README.md
```

## Fitur Utama
- Gameplay Flappy Bird klasik dengan fisika gravitasi.
- Deteksi tabrakan untuk pipa dan batas layar.
- Penghitungan skor setiap kali berhasil melewati sepasang pipa.
- Fungsi restart dengan menekan tombol **R** setelah game over.
- Menu utama dengan tombol **Continue** dan **Exit**.
- Background music (BGM) yang diputar di menu utama.
- Efek suara untuk kepakan sayap, melewati pipa, dan tabrakan.

## Kontrol
| Input | Fungsi |
| --- | --- |
| **Space** | Membuat burung melompat |
| **R** | Restart permainan (hanya saat game over) |
| **Klik Continue** | Memulai permainan dari menu utama |
| **Klik Exit** | Menutup aplikasi |

## Deskripsi Kelas

### `App.java`
Kelas utama yang menjalankan aplikasi. Membuat window (`JFrame`), menghubungkan `Logic` dengan `View`, lalu menampilkan game.

### `Logic.java`
Mengatur seluruh jalannya permainan: state (menu, bermain, game over), pergerakan objek, skor, efek suara, serta menangani input keyboard dan mouse.

### `View.java`
Panel yang menggambar semua elemen visual ke layar: background, burung, pipa, footer, tombol menu, dan tampilan game over.

### `Player.java`
Menyimpan data burung: posisi, ukuran, gambar, dan kecepatan vertikal.

### `Pipe.java`
Menyimpan data pipa: posisi, ukuran, gambar, dan status apakah sudah dilewati pemain.

### `Background.java`
Menyimpan data background: posisi, ukuran, gambar, dan kecepatan geser.

### `Footer.java`
Menyimpan data tanah di bagian bawah layar: posisi, ukuran, gambar, dan kecepatan geser.

### `Sprite.java`
Objek gambar umum untuk elemen statis seperti tombol menu dan tulisan game over.

### `Score.java`
Menampilkan skor sebagai `JLabel` di atas layar.

### `Sound.java`
Memutar efek suara dan BGM menggunakan voice pool agar suara bisa tumpang tindih.

## Alur Program

1. **Aplikasi Dijalankan**
   - `App` membuat window dan menginisialisasi `Logic` serta `View`.
   - Game langsung menampilkan menu utama dengan tombol Continue dan Exit.
   - BGM mulai diputar di menu utama.

2. **Menu Utama**
   - Pemain bisa klik **Continue** untuk mulai bermain, atau **Exit** untuk keluar.
   - Saat Continue diklik, BGM berhenti dan game dimulai.

3. **Gameplay**
   - Game loop berjalan ~60 FPS, menggerakkan background, footer, dan pipa.
   - Pipa baru muncul setiap 1,7 detik dengan celah acak.
   - Pemain menekan **Space** untuk membuat burung melompat melawan gravitasi.
   - Skor bertambah setiap kali burung berhasil melewati sepasang pipa.

4. **Game Over**
   - Terjadi saat burung menabrak pipa atau menyentuh tanah.
   - Game loop dan timer pipa dihentikan.
   - Tampilan game over dan instruksi restart ditampilkan.

5. **Restart**
   - Pemain menekan **R** untuk memulai ulang.
   - Posisi burung, skor, dan daftar pipa direset.
   - Game loop kembali berjalan dari awal.

## Tapilan dan Gameplay