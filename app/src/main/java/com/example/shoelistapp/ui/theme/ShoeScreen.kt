package com.example.shoelistapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.shoelistapp.model.Shoe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoeApp() {
    // KRITERIA: Navigasi v2/v3 (Menyiapkan alat buat pindah halaman)
    val navController = rememberNavController()

   //enghubungkan UI (View) dengan logika data (ViewModel)
    val shoeViewModel: ShoeViewModel = viewModel()

    // KRITERIA: Minimal 2 screen (Ini wadah utamanya buat ngatur rute antar layar)
    NavHost(navController = navController, startDestination = "halaman_utama") {

        // --- SCREEN 1: HALAMAN UMUM (LIST KATALOG) ---
        composable("halaman_utama") {
            // Ambil data dari internet (Mantau status data dari API)
            val shoeUiState by shoeViewModel.shoeUiState.collectAsState()

            // Material 3 (Pakai Scaffold sebagai kerangka standar M3)
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Katalog Sepatu Modern") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (shoeUiState) {
                        is ShoeUiState.Loading -> LoadingScreen()
                        is ShoeUiState.Error -> ErrorScreen(onRetry = { shoeViewModel.getShoeList() })
                        is ShoeUiState.Success -> ShoeListScreen(
                            shoes = (shoeUiState as ShoeUiState.Success).shoes,
                            onItemClick = { sepatuYangDiklik ->
                                // Simpan sepatu yang dipilih, lalu navigasi pindah ke halaman detail
                                shoeViewModel.selectShoe(sepatuYangDiklik)
                                navController.navigate("halaman_detail")
                            }
                        )
                    }
                }
            }
        }

        // --- SCREEN 2: HALAMAN DETAIL ---
        composable("halaman_detail") {
            val shoe by shoeViewModel.selectedShoe.collectAsState()

            // Kalau datanya ada, tampilkan layar detailnya
            shoe?.let { dataSepatu ->
                ShoeDetailScreen(
                    shoe = dataSepatu,
                    onBackClick = { navController.popBackStack() } // Perintah buat tombol back (kembali)
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorScreen(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gagal Memuat Data dari API.", modifier = Modifier.padding(bottom = 16.dp))
        Button(onClick = onRetry) { Text("Coba Lagi") }
    }
}

@Composable
fun ShoeListScreen(
    shoes: List<Shoe>,
    onItemClick: (Shoe) -> Unit,
    modifier: Modifier = Modifier
) {
    //Menampilkan hasil dalam bentuk list (Pakai LazyColumn biar performa aman)
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shoes, key = { shoe -> shoe.id }) { shoe ->
            ShoeCard(shoe = shoe, onClick = { onItemClick(shoe) })
        }
    }
}

// Composable UI (Card)
@Composable
fun ShoeCard(shoe: Shoe, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Kartu bisa diklik buat pindah halaman
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Jika dibuka di HP jadi potrait, jika di tablet gambar pindah ke kiri
        if (isTablet) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Load Gambar / Image (Pakai Coil buat load gambar dari URL)
                AsyncImage(
                    model = shoe.imageUrl,
                    contentDescription = shoe.name,
                    modifier = Modifier.weight(1f).height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f).padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Composable UI (Text)
                    Text(text = shoe.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = "Brand: ${shoe.brand ?: "-"}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "$ ${shoe.price}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Column {
                AsyncImage(
                    model = shoe.imageUrl,
                    contentDescription = shoe.name,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = shoe.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = "Brand: ${shoe.brand ?: "-"}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "$ ${shoe.price}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoeDetailScreen(shoe: Shoe, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Produk") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = shoe.imageUrl,
                contentDescription = shoe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(20.dp)) {
                // Typografi Material 3 (Pakai headlineMedium, titleMedium, bodyLarge)
                Text(
                    text = shoe.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Brand Resmi: ${shoe.brand ?: "Generic Brand"}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$ ${shoe.price}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = "Deskripsi Produk :",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = shoe.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}