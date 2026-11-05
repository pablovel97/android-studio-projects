package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PantallaPedido()
            }
        }
    }
}

@Composable
fun PantallaPedido() {
    // Variables y constantes:
    var nombreCliente: String by remember { mutableStateOf("") }
    var cantidad: Int by remember { mutableStateOf(1) }
    var producto: String by remember { mutableStateOf("Sin seleccionar") }
    var tamano: String by remember { mutableStateOf("Sin seleccionar") }
    var suplementoLeche: Boolean by remember { mutableStateOf(false) }
    var suplementoAzucar: Boolean by remember { mutableStateOf(false) }
    var suplementoLlevar: Boolean by remember { mutableStateOf(false) }
    var total = CalcularTotal(
        producto,
        cantidad,
        tamano,
        suplementoLeche,
        suplementoLlevar)

    // El enunciado declara 7 filas.
    // Las incluimos todas ordenadas dentro de una columna principal:
    Column(modifier = Modifier.padding(35.dp)) {

        // Fila 1: Nombre Cliente
        Row(modifier = Modifier.padding(6.dp)) {
            CampoEditable(
                texto = nombreCliente,
                alEditar = { nombreNuevo -> nombreCliente = nombreNuevo },
                etiqueta = "Nombre del cliente"
            )
        }

        // Fila 2: Productos
        Row(modifier = Modifier.padding(6.dp)) {
            Row() {

                // Botón para café
                MiBoton(
                    "Café\n1,50€",
                    alPulsar = { cantidad = 1; producto = "Café"})

                // Botón para té
                MiBoton(
                    "Té\n1,30€",
                    alPulsar = { cantidad= 1; producto = "Té"})

                // Botón para chocolate
                MiBoton(
                    "Chocolate\n2,00€",
                    alPulsar = { cantidad = 1; producto = "Chocolate"})
            }
        }

        // Fila 3: Tamaño
        Row(modifier = Modifier.padding(6.dp)) {
            Row() {

                // Botón para pequeño
                MiBoton(
                    "S\n+0,00€",
                    alPulsar = { tamano = "S"})

                // Botón para mediano
                MiBoton(
                    "M\n+0,50€",
                    alPulsar = { tamano = "M"})

                // Botón para grande
                MiBoton(
                    "L\n+1,00€",
                    alPulsar = { tamano = "L"})
            }
        }

        // Fila 4: Suplementos
        Row(modifier = Modifier.padding(2.dp)) {
            Column(modifier = Modifier.padding(2.dp)) {

                // Casilla para leche vegetal:
                Row() {
                    MiCheck(
                        suplementoLeche,
                        alMarcar = { suplementoLeche = it }
                    )
                    MiTexto("Leche vegetal (+0,40€)")
                }

                // Casilla para añadir extra de azúcar:
                Row() {
                    MiCheck(
                        suplementoAzucar,
                        alMarcar = { suplementoAzucar = it }
                    // Pedir aclarar el funcionamiento de la palabra reservada "it" en clase.
                    )
                    MiTexto("Extra azúcar (+0,00€)")
                }

                // Switch para pedir para llevar o no:
                Row() {
                    MiSwitch (
                        suplementoLlevar,
                        alMarcar = { suplementoLlevar = it }
                    )
                    MiTexto("Para llevar (+0,20€)")
                }

            }

        }

        // Fila 5: Cantidad pedida
        Row(modifier = Modifier.padding(6.dp)) {
            // Tenemos 3 columnas (boton - texto - boton)
            Row() {

                // Botón para restar cantidad:
                MiBoton(
                    "-",
                    alPulsar = {
                        if (cantidad > 1) // Se limita el mínimo al restar
                            cantidad -= 1
                    }
                )

                // Contador de cantidad en el selector:
                MiTexto("Cantidad: $cantidad")

                // Botón para sumar cantidad:
                MiBoton(
                    "+",
                    alPulsar = {
                        if (cantidad < 10) // Se limita el máximo para sumar
                        cantidad += 1
                    }
                )

            }
        }

        // Fila 6: Resumen global
        Row(modifier = Modifier.padding(6.dp)) {

            // Card para mostrar a tiempo real el resumen de todo el pedido:
            MiResumen(
                nombreCliente,
                cantidad,
                producto,
                tamano,
                suplementoLeche,
                suplementoAzucar,
                suplementoLlevar,
                total
            )
        }

        // Fila 7: Reset / Limpiar
        Row(modifier = Modifier.padding(6.dp)) {

            // Botón para limpiar todo el contenido actual:
            MiBoton(
                "Reiniciar pedido",
                alPulsar = {
                    nombreCliente = ""
                    cantidad = 1
                    producto = ""
                    tamano = "Sin seleccionar"
                    suplementoLeche = false
                    suplementoAzucar = false
                    suplementoLlevar = false
                })
        }

    }
}

@Composable
fun CampoEditable(texto: String, alEditar: (String) -> Unit, etiqueta: String) {
    OutlinedTextField(
        value = texto,
        onValueChange = alEditar,
        label = { Text(etiqueta) }
    )
}

@Composable
fun MiBoton(accion: String, alPulsar: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.padding(6.dp),
        onClick = {
            alPulsar()
        }
        ) {
            Text(accion)
        }
}

@Composable
fun MiCheck (elemento: Boolean, alMarcar: (Boolean) -> Unit) {
    Checkbox(
        checked = elemento,
        onCheckedChange = alMarcar,
        modifier = Modifier.padding(6.dp),
        enabled = true
    )
}

@Composable
fun MiSwitch (elemento: Boolean, alMarcar: (Boolean) -> Unit) {
    Switch(
        checked = elemento,
        onCheckedChange = alMarcar,
        modifier = Modifier.padding(6.dp)
    )
}

@Composable
fun MiTexto(contenido: String) {
    Text(
        text = contenido,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun MiResumen(
    cliente: String,
    cantidad: Int,
    producto: String,
    tamano: String,
    vegetal: Boolean,
    azucar: Boolean,
    llevar: Boolean,
    total: Float
) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            """
            Cliente: $cliente
            Producto: $producto
            Tamaño: $tamano
            Cantidad: $cantidad
            Leche vegetal: ${if (vegetal) "Sí (+0,40€)" else "No"}
            Extra de azúcar: ${if (azucar) "Sí" else "No"}
            Para llevar: ${if (llevar) "Sí (+0,20€)" else "No"}
            
            Total: $total€
        """
        )
    }
}

fun CalcularTotal(
    producto: String,
    cantidad: Int,
    tamano: String,
    vegetal: Boolean,
    llevar: Boolean
): Float {
    // Se declaran variables y valores necesarios para esta función de cáclulo:
    var precioUnitario: Float = 0.00f
    val precioCafe: Float = 1.50f
    val precioTe: Float = 1.30f
    val precioChoco: Float = 2.00f
    val suplementoPeque: Float = 0.00f
    val suplementoMediano: Float = 0.50f
    val suplementoGrande: Float = 1.00f
    val suplementoLecheVegetal: Float = 0.40f
    val suplementoExtraAzuzar: Float = 0.00f
    val suplementoParaLlevar: Float = 0.20f
    var precioTamano: Float = 0.00f
    var precioSuplementos: Float = 0.00f

    // Se asgina el precio según el producto seleccionado en la funcion principal:
    precioUnitario = when(producto) {
        "Café" -> precioCafe
        "Té" -> precioTe
        "Chocolate" -> precioChoco
        else -> 0.0f
    }

    // Se aplica el suplemento del tamaño
    precioTamano = when(tamano) {
        "S" -> precioTamano + suplementoPeque
        "M" -> precioTamano + suplementoMediano
        "L" -> precioTamano + suplementoGrande
        else -> 0.0f

    }

    // Se suma el suplemento si es true:
    if (vegetal)
        precioSuplementos += suplementoLecheVegetal

    // Se suma el suplemento si es true:
    if (llevar)
        precioSuplementos += suplementoParaLlevar

    // Se suma todo para devolver un total final:
    var precioFinal = (precioUnitario + precioTamano + precioSuplementos) * cantidad

    return precioFinal
}
