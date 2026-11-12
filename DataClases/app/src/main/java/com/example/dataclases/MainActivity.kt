package com.example.dataclases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dataclases.ui.theme.DataClasesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataClasesTheme {

                PantallaPedido()

            }
        }
    }
}

data class Productos(
    val producto: String,
    val precio: Double,
    val descripcion: String
)

@Composable
fun PantallaPedido() {


    // Variables:
    var seleccion by remember { mutableStateOf("Ninguno") }

    var listaProductos = listOf<Productos>(
        Productos("Café", 1.50, "Café normal"),
        Productos("Napolitana", 2.50, "Napolitana de chocolate"),
        Productos("Té", 2.80, "Té menta-poleo")
    )
    LazyColumn(modifier = Modifier.padding(35.dp)) {
        items(listaProductos) {
            Card(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Column() {
                    Row(modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Producto: ${it.producto}")
                        Text("Precio: ${it.precio}")
                        Text(it.descripcion)
                    }

                    Button(onClick = { seleccion = it.descripcion }) {
                        Text("Seleccionar:")
                    }
                }

            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun PantallaPreview() {
    DataClasesTheme {
        PantallaPedido()
    }
}