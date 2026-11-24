# Coroutine Demo

Tugas M17 - Kotlin Coroutines and Asynchronous Programming

- Nama: Muh. Daffa Ulhaq
- NIM: 24523151

## Tentang Aplikasi

Aplikasi MVVM yang memuat daftar tugas dan pengumuman secara asinkron pakai coroutine.

## Jawaban Latihan

### 1. Tambah satu permintaan lagi

Saya tambah getCourseCount() dengan delay(1500L) dan dimuat pakai async ketiga.

Total waktu masih sekitar 2 detik, soalnya ketiga async jalan bersamaan. Waktunya ngikutin yang paling lama (getTasks() = 2 detik), bukan dijumlahin.

### 2. Tombol Retry

Tombol Retry muncul di samping pesan error (cuma kalau errorMessage tidak null) dan manggil loadData() lagi.

### 3. Menangkap terlalu banyak

Setelah diganti ke catch (e: Exception), pas tekan Cancel muncul pesan Failed to load: StandaloneCoroutine was cancelled.

Sebabnya pembatalan melempar CancellationException, dan catch (e: Exception) ikut nangkap, jadi pesan Loading cancelled ketimpa. Kalau pakai catch (e: IOException) hal ini tidak terjadi.

### 4. Di mana macetnya?

- Thread.sleep() di dalam withContext(IO): layar tetap lancar, karena yang tidur thread IO.
- Thread.sleep() tanpa withContext: layar macet sekitar 2 detik, karena jalan di main thread.

Kesimpulannya, withContext yang mindahin pekerjaan dari main thread, bukan kata suspend.

### 5. Pertanyaan konsep

(a) launch menghasilkan Job tanpa nilai kembalian. async menghasilkan Deferred<T> yang nilainya diambil pakai await().

(b) GlobalScope tidak terikat lifecycle, jadi coroutine tetap jalan walaupun layar sudah ditutup dan bisa bocor.

(c) Kalau getTasks() gagal, getAnnouncement() ikut dibatalkan oleh coroutineScope, terus errornya diteruskan ke try/catch.

### 6. Tombol Sort

Daftar tugas diurutkan berdasarkan judul di Dispatchers.Default pakai withContext, karena pengurutan itu komputasi CPU. Hasilnya diupdate ke state.

## Modifikasi Tambahan

- Sort A-Z / Z-A: tombol Sort saya kembangkan jadi bolak-balik pakai field sortAscending di TasksUiState. Label tombolnya ikut berubah.
- Tombol Clear: buat ngosongin daftar tugas dan pengumuman. Kalau loading lagi jalan, Job-nya dibatalkan dulu pakai loadJob?.cancel().