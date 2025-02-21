package com.julianvelandia.bizorder.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.julianvelandia.bizorder.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateScreen(
    viewModel: PreOrderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var productName by rememberSaveable { mutableStateOf("") }
    var clientName by rememberSaveable { mutableStateOf("") }
    var isButtonEnabled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CreateEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Preorden guardada y enviada correctamente al servidor.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CreateEvent.Error -> {
                    Toast.makeText(
                        context,
                        "Preorden almacenada localmente. El envío al servidor falló",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            productName = ""
            clientName = ""
            isButtonEnabled = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_image),
            contentDescription = "formulario",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(bottom = 24.dp),
            contentScale = ContentScale.Crop
        )

        TextField(
            value = productName,
            onValueChange = {
                productName = it
                isButtonEnabled = productName.isNotBlank() && clientName.isNotBlank()
            },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = clientName,
            onValueChange = {
                clientName = it
                isButtonEnabled = productName.isNotBlank() && clientName.isNotBlank()
            },
            label = { Text("Nombre del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onSavePreOrder(clientName, productName) },
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}