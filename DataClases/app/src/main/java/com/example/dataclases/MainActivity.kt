package com.example.dataclases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dataclases.ui.theme.DataClasesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaPedido()
        }
    }
}

// Modelo de datos que representa cada artículo de la cafetería
data class Productos(
    val producto: String,
    val precio: Double,
    val descripcion: String
)

@Composable
fun PantallaPedido() {

    // Estado observable: guarda la selección actual y dispara la recomposición al cambiar
    var seleccion by remember { mutableStateOf("Ninguno") }

    // Fuente de datos con el catálogo de productos a mostrar
    var listaProductos = listOf<Productos>(
        Productos("cafe", 1.5, "Café normal"),
        Productos("napolitana", 2.5, "Napolitana de chocolate"),
        Productos("te", 2.8, "Té menta-poleo")
    )

    Column(modifier = Modifier.padding(25.dp)) {
        // Lista vertical eficiente: solo renderiza en pantalla los elementos visibles
        LazyColumn(modifier = Modifier.padding(35.dp)) {
            items(listaProductos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text("Producto: ${producto.producto}")
                            Text("precio: ${producto.precio}")
                            Text(producto.descripcion)
                        }
                        // Evento de clic: actualiza el estado 'seleccion' con el producto pulsado
                        Button(onClick = { seleccion = producto.descripcion }) {
                            Text("Seleccionar")
                        }
                    }
                }
            }
        }

        // Este texto lee el estado 'seleccion' y se actualiza automáticamente al pulsar un botón
        Text("Pruducto seleccionado: $seleccion")
    }
}

@Composable
fun TarjetaProducto(p: Productos) {

    Card(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(15.dp))
        {
            // Nombre + precio
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(p.producto, fontWeight = FontWeight.Bold)
                Text("${p.precio}€")
            }
            // Descripcion
            Text(p.descripcion)
        }
    }

}