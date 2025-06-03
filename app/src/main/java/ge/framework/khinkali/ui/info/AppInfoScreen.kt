package ge.framework.khinkali.ui.info

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "აპლიკაციის შესახებ",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "ხინკალი",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
            
            Text(
                text = "ვერსია 1.0.0",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "აპლიკაცია შექმნილია დასკვნითი გამოცდის ფარგლებში. გამოყენებულია შპს ფრეიმვორკის მიერ შექმნილი API, რომლის გამოყენების უფლება მინიჭებულია კომპანიის მიერ.",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "© 2025 Khinkali App | Framework LLC",
                style = MaterialTheme.typography.bodySmall
            )

        }

    }
} 