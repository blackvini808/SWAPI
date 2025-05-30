package com.example.clon_fulanito

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.clon_fulanito.API.SWAPI.RepositorioSWAPI
import com.example.clon_fulanito.NaveResumen
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf

@Composable
fun PantallaHome(
    navController: NavController,
    listaItems: SnapshotStateList<String>
    ) {

    // Datos de ejemplo para el slider
    val scope = rememberCoroutineScope()
    var naves by remember { mutableStateOf<List<NaveResumen>>(emptyList()) }

    var imagenActual by remember { mutableStateOf(0) }
    var mostrarInfoImagen by remember { mutableStateOf(false) }

    // Lista fija de URLs de imágenes para el carrusel
    val imagenesCarrusel = listOf(
        R.drawable.corvette,
        R.drawable.stardestroyer,
        R.drawable.sentinel,
        R.drawable.deathstar,
        R.drawable.ywing,
        R.drawable.millenium,
        R.drawable.tie,
        R.drawable.executor,
        R.drawable.xwing,
        R.drawable.rebel
    )

    LaunchedEffect(Unit) {
        scope.launch {
            val repo = RepositorioSWAPI()
            val respuesta: PaginaContenedora = withContext(Dispatchers.IO) {
                repo.obtener_naves_espaciales(1)
            }
            naves = respuesta.results
        }
    }

    val imagenPresente = naves.getOrNull(imagenActual)?.name ?: ""

    val estaEnLista = imagenPresente.isNotEmpty() && imagenPresente in listaItems

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Slider
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    width = 3.dp,
                    color = Color.Black
                )
        ) {
            // Imagen del slider
            AsyncImage(
                model = imagenesCarrusel.getOrNull(imagenActual),
                contentDescription = "Slider Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            // Botones de navegación del slider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        imagenActual = if (imagenActual == 0) naves.size - 1 else imagenActual - 1
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Anterior")
                }

                IconButton(
                    onClick = {
                        imagenActual = if (imagenActual == naves.size - 1) 0 else imagenActual + 1
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente")
                }
            }
        }

        // Información de la imagen (aparece al hacer clic)
        if (mostrarInfoImagen && naves.isNotEmpty()) {
            val naveActual = naves[imagenActual]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = naveActual.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "UID: ${naveActual.uid}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Modelo: ${naveActual.model}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fabricante: ${naveActual.manufacturer}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Costo en créditos: ${naveActual.cost_in_credits}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (estaEnLista) {
                            listaItems.remove(imagenPresente)
                        } else if (imagenPresente.isNotEmpty()) {
                            listaItems.add(imagenPresente)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estaEnLista) Color.Red else Color.Green
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (estaEnLista) Icons.Default.Delete else Icons.Default.Add,
                            contentDescription = "Lista"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(if (estaEnLista) "Quitar de lista" else "Agregar a lista")
                    }
                }
            }
        }

        // Botón para mostrar información
        Button(
            onClick = { mostrarInfoImagen = !mostrarInfoImagen },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = "Info")
                Spacer(modifier = Modifier.size(8.dp))
                Text("Mostrar información")
            }
        }

        // Menú de navegación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Ya estamos en home */ }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("HOME")
                }
            }

            Button(onClick = { navController.navigate("lista") }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.List, contentDescription = "Lista")
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("LISTA")
                }
            }
        }
    }
}

