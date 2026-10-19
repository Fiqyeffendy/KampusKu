package com.example.kampusku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Palet warna Ungu/Violet elegan dengan kontras tinggi yang memenuhi standar WCAG AAA (aksesibilitas)
private val BrandPurple = Color(0xFF5B21B6)          // Warna utama (Primary Violet/Ungu Dalam, rasio kontras 10.5:1)
private val BrandPurpleContainer = Color(0xFFF3E8FF)   // Kontainer ungu muda / lavender
private val PageBackground = Color(0xFFFAF5FF)      // Background halaman lavender ultra-terang
private val Ink = Color(0xFF1F1135)                 // Teks utama plum/slate sangat gelap (rasio kontras > 16:1)
private val MutedInk = Color(0xFF584E69)            // Teks sekunder plum/slate sedang (rasio kontras > 6:1)

// Pasangan warna kartu & aksen kontras tinggi (Latar kontainer lembut + Aksen teks/ikon gelap)
private val DarkPurpleAccent = Color(0xFF6B21A8)

private val SoftTealContainer = Color(0xFFCCFBF1)
private val DarkTealAccent = Color(0xFF0F766E)

private val SoftAmberContainer = Color(0xFFFEF3C7)
private val DarkAmberAccent = Color(0xFFB45309)

private val SoftRoseContainer = Color(0xFFFFE4E6)
private val DarkRoseAccent = Color(0xFFBE123C)

private val SoftBlueContainer = Color(0xFFE0E7FF)
private val DarkBlueAccent = Color(0xFF1E40AF)

private val SoftGreenContainer = Color(0xFFDCFCE7)
private val DarkGreenAccent = Color(0xFF15803D)

private val SoftFuchsiaContainer = Color(0xFFFAE8FF)
private val DarkFuchsiaAccent = Color(0xFFA21CAF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KampusKuTheme {
                AdaptiveDashboard()
            }
        }
    }
}

@Composable
private fun KampusKuTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = BrandPurple,
        onPrimary = Color.White,
        primaryContainer = BrandPurpleContainer,
        onPrimaryContainer = Ink,
        secondary = DarkPurpleAccent,
        onSecondary = Color.White,
        background = PageBackground,
        surface = Color.White,
        onSurface = Ink,
        onSurfaceVariant = MutedInk,
        tertiary = DarkTealAccent
    )
    MaterialTheme(colorScheme = colors, content = content)
}

/**
 * Satu halaman dashboard yang mengubah penempatan elemen berdasarkan
 * lebar ruang aplikasi yang tersedia, bukan semata-mata tipe perangkat.
 * Compact < 600 dp, Medium 600-839 dp, Expanded >= 840 dp.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdaptiveDashboard() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "KampusKu",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Dashboard Perkuliahan",
                            style = MaterialTheme.typography.labelMedium,
                            color = MutedInk
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = "Logo KampusKu",
                        tint = BrandPurple,
                        modifier = Modifier.padding(horizontal = 16.dp).size(28.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Ink
                )
            )
        },
        containerColor = PageBackground
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            val currentWidth = maxWidth
            val widthClass = when {
                currentWidth < 600.dp -> "COMPACT · PONSEL"
                currentWidth < 840.dp -> "MEDIUM · LAYAR SEDANG"
                currentWidth < 1200.dp -> "EXPANDED · TABLET/LAPTOP"
                else -> "LARGE · LAYAR BESAR"
            }
            val showSideRail = currentWidth >= 840.dp

            Row(modifier = Modifier.fillMaxSize()) {
                if (showSideRail) {
                    DashboardSideRail()
                }
                DashboardContent(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    widthClassLabel = widthClass
                )
            }
        }
    }
}

/** Area navigasi hanya ilustrasi visual pada pertemuan layout. Navigasi aktif dibahas pada pertemuan berikutnya. */
@Composable
private fun DashboardSideRail() {
    Column(
        modifier = Modifier.width(88.dp).fillMaxHeight()
            .background(Color.White)
            .padding(top = 24.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        SideRailItem(Icons.Filled.Dashboard, "Beranda", selected = true)
        SideRailItem(Icons.Filled.CalendarMonth, "Jadwal", selected = false)
        SideRailItem(Icons.AutoMirrored.Filled.MenuBook, "Materi", selected = false)
        SideRailItem(Icons.Filled.BarChart, "Nilai", selected = false)
    }
}

@Composable
private fun SideRailItem(icon: ImageVector, label: String, selected: Boolean) {
    val tileColor = if (selected) BrandPurpleContainer else Color.Transparent
    val iconColor = if (selected) BrandPurple else MutedInk
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            color = tileColor,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.size(width = 56.dp, height = 40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = label, tint = iconColor)
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = iconColor)
    }
}

@Composable
private fun DashboardContent(modifier: Modifier, widthClassLabel: String) {
    val metrics = listOf(
        MetricData("Mata Kuliah", "7", "Semester berjalan aktif", Icons.AutoMirrored.Filled.MenuBook, BrandPurple, BrandPurpleContainer),
        MetricData("Tugas Selesai", "14 / 18", "78% tugas telah terkumpul", Icons.Filled.AssignmentTurnedIn, DarkGreenAccent, SoftGreenContainer),
        MetricData("Jadwal Hari Ini", "4", "Kelas terjadwal di kampus", Icons.Filled.Schedule, DarkTealAccent, SoftTealContainer)
    )

    // Kartu mata kuliah (Total 7 kartu) dengan teks lengkap tanpa terpotong
    val courses = listOf(
        CourseData(
            title = "Pemrograman Mobile Adaptif",
            subtitle = "Jetpack Compose, Kotlin Modern, dan Arsitektur Layout Adaptif",
            note = "Pertemuan 5 · Praktikum Grid Adaptif & Material Design 3",
            progress = 0.75f,
            icon = Icons.Filled.School,
            accent = BrandPurple,
            container = BrandPurpleContainer
        ),
        CourseData(
            title = "Basis Data Terdistribusi & Komputasi Awan",
            subtitle = "Perancangan Normalisasi ERD, Query Kompleks SQL, serta Manajemen NoSQL",
            note = "Tugas Besar Kelompok Dikumpulkan Jumat Pukul 23.59 WIB",
            progress = 0.60f,
            icon = Icons.Filled.Science,
            accent = DarkGreenAccent,
            container = SoftGreenContainer
        ),
        CourseData(
            title = "Struktur Data & Pemrograman Algoritma",
            subtitle = "Penerapan Array, Linked List, Stacks, Queues, Tree, dan Graph Kompleks",
            note = "Latihan Mandiri & Praktikum Laboratorium Komputer Mingguan",
            progress = 0.85f,
            icon = Icons.Filled.Code,
            accent = DarkAmberAccent,
            container = SoftAmberContainer
        ),
        CourseData(
            title = "Analisis Data & Visualisasi Statistik",
            subtitle = "Statistik Deskriptif, Probabilitas, Pengolahan Data, dan Pemodelan",
            note = "Materi Minggu Ini: Regresi Linear Lanjutan & Klasterisasi Data",
            progress = 0.45f,
            icon = Icons.Filled.BarChart,
            accent = DarkRoseAccent,
            container = SoftRoseContainer
        ),
        CourseData(
            title = "Jaringan Komputer & Keamanan Siber",
            subtitle = "Arsitektur Topologi Jaringan, Protokol TCP/IP, Router, dan Enkripsi Data",
            note = "Praktikum Konfigurasi Jaringan II di Laboratorium Jaringan",
            progress = 0.68f,
            icon = Icons.Filled.Computer,
            accent = DarkTealAccent,
            container = SoftTealContainer
        ),
        CourseData(
            title = "Kecerdasan Buatan & Pembelajaran Mesin",
            subtitle = "Pengenalan Konsep Neural Network, Deep Learning, dan Model Generatif",
            note = "Proyek Akhir Semester: Implementasi Algoritma Klasifikasi Teks",
            progress = 0.35f,
            icon = Icons.Filled.Security,
            accent = DarkFuchsiaAccent,
            container = SoftFuchsiaContainer
        ),
        CourseData(
            title = "Rekayasa Perangkat Lunak & Manajemen Agil",
            subtitle = "Metodologi Scrum, SDLC, Pengujian Perangkat Lunak, dan CI/CD",
            note = "Persiapan Sprint Planning untuk Proyek Kolaboratif",
            progress = 0.50f,
            icon = Icons.Filled.Dashboard,
            accent = DarkBlueAccent,
            container = SoftBlueContainer
        )
    )

    // Grid adaptif dengan minSize = 260.dp
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 260.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            WindowClassBanner(widthClassLabel)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            WelcomeCard()
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader("Ringkasan Akademik", "Kartu ringkasan berpindah otomatis sesuai lebar layar.")
        }
        items(metrics) { metric ->
            MetricCard(metric)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader("Mata Kuliah", "Grid menggunakan minSize 260 dp agar kartu dan teks nyaman dibaca.")
        }
        items(courses) { course ->
            CourseCard(course)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Data contoh untuk praktikum · Belum menggunakan database atau API",
                style = MaterialTheme.typography.bodySmall,
                color = MutedInk,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun WindowClassBanner(label: String) {
    Surface(
        color = BrandPurpleContainer,
        contentColor = Ink,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = BrandPurple)
            Spacer(Modifier.width(8.dp))
            Text("Mode layout: $label", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BrandPurple),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Selamat datang, Fauzan!",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Atur kegiatan kuliahmu dengan lebih terarah. Tampilan ini menyesuaikan ruang layar yang tersedia secara adaptif.",
                    color = Color(0xFFF3E8FF),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Surface(
                shape = CircleShape,
                color = Color(0xFF7C3AED),
                modifier = Modifier.size(58.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = "Ilustrasi pendidikan",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 1.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Ink
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MutedInk
        )
    }
}

private data class MetricData(
    val label: String,
    val value: String,
    val detail: String,
    val icon: ImageVector,
    val accent: Color,
    val container: Color
)

@Composable
private fun MetricCard(data: MetricData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = data.container, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(42.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(data.icon, contentDescription = null, tint = data.accent)
                    }
                }
                Spacer(Modifier.width(10.dp))
                Text(data.label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = MutedInk)
            }
            Spacer(Modifier.height(12.dp))
            Text(data.value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Ink)
            Spacer(Modifier.height(2.dp))
            Text(data.detail, style = MaterialTheme.typography.bodySmall, color = MutedInk)
        }
    }
}

private data class CourseData(
    val title: String,
    val subtitle: String,
    val note: String,
    val progress: Float,
    val icon: ImageVector,
    val accent: Color,
    val container: Color
)

@Composable
private fun CourseCard(course: CourseData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    color = course.container,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = course.icon,
                            contentDescription = null,
                            tint = course.accent,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Ink,
                        softWrap = true
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = course.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MutedInk,
                        softWrap = true
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progres belajar",
                    style = MaterialTheme.typography.labelMedium,
                    color = MutedInk
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${(course.progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = course.accent,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { course.progress },
                modifier = Modifier.fillMaxWidth().height(7.dp),
                color = course.accent,
                trackColor = course.container
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = course.note,
                style = MaterialTheme.typography.labelSmall,
                color = MutedInk,
                softWrap = true
            )
        }
    }
}

@Preview(name = "1. Ponsel (Compact)", showBackground = true, widthDp = 393, heightDp = 850)
@Composable
private fun PhonePreview() {
    KampusKuTheme { AdaptiveDashboard() }
}

@Preview(name = "2. Layar Sedang (Medium)", showBackground = true, widthDp = 720, heightDp = 850)
@Composable
private fun MediumPreview() {
    KampusKuTheme { AdaptiveDashboard() }
}

@Preview(name = "3. Tablet/Layar Lebar (Expanded)", showBackground = true, widthDp = 1024, heightDp = 768)
@Composable
private fun TabletPreview() {
    KampusKuTheme { AdaptiveDashboard() }
}
