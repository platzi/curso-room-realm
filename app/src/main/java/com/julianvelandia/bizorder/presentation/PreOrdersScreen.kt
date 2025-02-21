package com.julianvelandia.bizorder.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.julianvelandia.bizorder.domain.PreOrder

@Composable
fun PreOrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: PreOrderViewModel = hiltViewModel()
) {
    val state by viewModel.preOrderState.collectAsState()

    when {
        state.isLoading -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        state.isError -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Error DATA",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        else -> {

            LazyColumn(
                modifier = modifier
                    .padding(start = 8.dp, top = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.data) { item ->
                    PreOrderItem(item, {
                        viewModel.onSync(it)
                    }, {
                        viewModel.onDelete(it)
                    })
                }
            }
        }
    }
}

@Composable
fun PreOrderItem(preOrder: PreOrder, onSync: (Long) -> Unit, onDelete: (Long) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = preOrder.customerName,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = preOrder.product,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = if (preOrder.isSent) Icons.Default.CheckCircle else Icons.Default.Warning,
            contentDescription = if (preOrder.isSent) "Sent" else "Not Sent",
            tint = if (preOrder.isSent) Color.Green else Color.Red
        )

        if (preOrder.isSent) {
            Spacer(modifier = Modifier.width(48.dp))
        } else {
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Sincronizar") },
                        onClick = {
                            expanded = false
                            onSync(preOrder.id)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            expanded = false
                            onDelete(preOrder.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPreOrderItem() {
    PreOrderItem(
        preOrder = PreOrder(
            id = 1,
            customerName = "Julian Velandia",
            product = "MacBook Pro 2021",
            isSent = true
        ),
        onSync = {},
        onDelete = {}
    )
}