package com.example.dataclases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dataclases.ui.theme.DataClasesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataClasesTheme {
                PantallaPedido()
            }
        }
    }
}

// Modelo de datos que representa cada producto
data class Productos(
    val producto: String,
    val precio: Double,
    val descripcion: String,
    val cantidad: Int = 0
)

@Composable
fun PantallaPedido() {
    /*
    by remember y mutableStateOf guardan la lista de productos y avisan a Compose
    cuando cambia para redibujar la pantalla.
     */
    var listaProductos by remember {
        mutableStateOf(
            listOf(
                Productos("Café", 1.5, "Café normal"),
                Productos("Napolitana", 2.5, "Napolitana de chocolate"),
                Productos("Té", 2.8, "Té menta-poleo")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Cafetería", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn es como un RecyclerView eficiente: solo muestra en pantalla lo que cabe
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(listaProductos) { prod ->
                TarjetaProducto(
                    producto = prod,
                    // onIncrement: recorre la lista y actualiza la cantidad del producto pulsado sumándole 1
                    onIncrement = {
                        listaProductos = listaProductos.map {
                            if (it.producto == prod.producto) {
                                it.copy(cantidad = it.cantidad + 1) // copy() crea una copia cambiando solo la cantidad
                            } else {
                                it
                            }
                        }
                    },
                    // onDecrement: hace lo mismo pero comprobando que la cantidad sea mayor que 0 antes de restar
                    onDecrement = {
                        listaProductos = listaProductos.map {
                            if (it.producto == prod.producto && it.cantidad > 0) {
                                it.copy(cantidad = it.cantidad - 1)
                            } else {
                                it
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // sumOf calcula el precio total multiplicando el precio por la cantidad de cada producto
        val total = listaProductos.sumOf { it.precio * it.cantidad }
        Text(text = "Total: $total €", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TarjetaProducto(
    producto: Productos,
    // Función lambda (callback) que se ejecuta al pulsar el botón de sumar (+)
    onIncrement: () -> Unit,
    // Función lambda (callback) que se ejecuta al pulsar el botón de restar (-)
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = "Producto: ${producto.producto}")
            Text(text = "Precio: ${producto.precio}€")
            Text(text = "Descripción: ${producto.descripcion}")
            Text(text = "Cantidad: ${producto.cantidad}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Botón para restar (se deshabilita automáticamente si la cantidad es 0)
                Button(
                    onClick = onDecrement,
                    enabled = producto.cantidad > 0
                ) {
                    Text(text = "-")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón para sumar
                Button(
                    onClick = onIncrement
                ) {
                    Text(text = "+")
                }
            }
        }
    }
}
