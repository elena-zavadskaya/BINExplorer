package com.example.binexplorer.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.binexplorer.domain.model.BinInfo

@Composable
fun BinInfoCard(
    binInfo: BinInfo,
    onItemClick: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Информация",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Информация о карте",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            CardInfoSection("Карта", Icons.Default.CreditCard) {
                InfoRow("Схема", binInfo.scheme)
                InfoRow("Тип", binInfo.type)
                InfoRow("Бренд", binInfo.brand)
            }

            binInfo.country?.let { country ->
                Spacer(modifier = Modifier.height(16.dp))
                CardInfoSection("Страна", Icons.Default.Public) {
                    InfoRow("Название", country.name)
                    InfoRow("Код", country.code)
                    ClickableInfoRow(
                        label = "Координаты",
                        value = "${country.latitude}, ${country.longitude}",
                        icon = Icons.Default.LocationOn,
                        onClick = {
                            onItemClick("geo", "${country.latitude},${country.longitude}")
                        }
                    )
                }
            }

            binInfo.bank?.let { bank ->
                Spacer(modifier = Modifier.height(16.dp))
                CardInfoSection("Банк", Icons.Default.Business) {
                    InfoRow("Название", bank.name)
                    InfoRow("Город", bank.city)
                    bank.url?.let { url ->
                        ClickableInfoRow(
                            label = "Веб-сайт",
                            value = url,
                            icon = Icons.Default.Language,
                            onClick = { onItemClick("url", url) }
                        )
                    }
                    bank.phone?.let { phone ->
                        ClickableInfoRow(
                            label = "Телефон",
                            value = phone,
                            icon = Icons.Default.Phone,
                            onClick = { onItemClick("tel", phone) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardInfoSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
        }
        Divider(
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
        content()
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            "$label:",
            modifier = Modifier.width(100.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            value ?: "Недоступно",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ClickableInfoRow(
    label: String,
    value: String,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$label:",
            modifier = Modifier.width(100.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).padding(end = 4.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Перейти",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}