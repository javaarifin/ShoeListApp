# ShoeListApp 

ShoeListApp adalah aplikasi katalog sepatu modern berbasis Android yang dikembangkan menggunakan **Kotlin** dan **Jetpack Compose**. Aplikasi ini dibuat untuk memenuhi projek akhir semester dengan mengimplementasikan berbagai arsitektur dan teknologi Android modern.

## Fitur & Kriteria yang Terpenuhi
Aplikasi ini telah memenuhi seluruh kriteria projek akhir:
- **Composable UI:** Menggunakan komponen dasar Jetpack Compose seperti `Text`, `Image`, dan `Card`.
- **REST API:** Mengambil data katalog sepatu secara langsung dari internet (Web Service/API).
- **Image Loading:** Memuat dan menampilkan gambar dari internet menggunakan library **Coil**.
- **MVVM Architecture:** Memisahkan logika aplikasi dengan tampilan menggunakan View, Model, dan ViewModel (`StateFlow`).
- **Navigasi v2/v3:** Memiliki 2 layar terpisah dengan sistem navigasi (Halaman Katalog -> Halaman Detail Produk).
- **List View:** Menampilkan kumpulan data dalam bentuk daftar yang bisa di-scroll menggunakan `LazyColumn`.
- **Material Design 3:** Mengimplementasikan standar desain Material 3 (TopAppBar, Scaffold, warna dinamis).
- **Typografi:** Menggunakan skala tipografi Material 3 secara terstruktur (`headlineMedium`, `titleLarge`, `bodyMedium`, dll).

---

## Dokumentasi API (Web Service)
Aplikasi ini menggunakan **DummyJSON API** sebagai sumber data publik.

- **Base URL:** `https://dummyjson.com/`
- **Endpoint:** `products/category/mens-shoes`
- **Method:** `GET`
- **Deskripsi:** Endpoint ini mengembalikan daftar produk dengan kategori sepatu pria, lengkap dengan nama produk, merek, harga, deskripsi, dan URL gambar (thumbnail).

### DOKUMENTASI APLIKASI

![](Images/3.png)

![](Images/2.png)

![](Images/1.png)

**Contoh Response API:**
```json
{
  "products": [
    {
      "id": 56,
      "title": "Sneakers Shoes",
      "description": "Synthetic Leather Casual Sneaker shoes for Women/girls Sneakers For Women",
      "price": 120,
      "brand": "Fashion",
      "thumbnail": "[https://cdn.dummyjson.com/product-images/56/thumbnail.jpg](https://cdn.dummyjson.com/product-images/56/thumbnail.jpg)"
    }
  ]
}

