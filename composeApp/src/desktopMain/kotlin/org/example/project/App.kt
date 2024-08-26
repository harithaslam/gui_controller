package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.mp.KoinPlatform.getKoin


@Composable
@Preview
fun App(viewModel: SerialViewModel =viewModel { SerialViewModel(sensorDataQueries = getKoin().get(),getKoin().get()) }) {
    val serialData by viewModel.serialData.collectAsState()
    val snackbarHostState by viewModel.SnackbarHostState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(

                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary ,
                title = {
                    Text("GUI Controller")
                }
            )
        }
    ) {
    Row {
        Column(
            modifier = Modifier.padding(10.dp).weight(0.5f)

        ){
            COMPortSetting()
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Interrupt()
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            DataProcessing(onExportToExcel = { viewModel.exportToExcel()
                coroutineScope.launch {
                    viewModel.showMessage("Data has been exported to Excel", "VIEW"){
                        viewModel.viewExcelFile()
                    }
                }
                                             }, onResetDatabase = {viewModel.resetDatabase()})

        }
        Column(
            modifier = Modifier.padding(10.dp).weight(0.5f)

        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier.padding(10.dp)
                ){
                    Text(serialData?:"DATA IS EMPTY")
                    Spacer(modifier = Modifier.height(25.dp))

                }
            }
        }
    }
}
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun COMPortSetting(viewModel: SerialViewModel =viewModel { SerialViewModel(getKoin().get(),getKoin().get()) }){
    var cScope = rememberCoroutineScope()
    var options = viewModel.listAvailableComPorts()
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    Card(
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()

    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ) {


            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = selectedOptionText?:"",
                    onValueChange = { },
                    label = { Text("Label") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        ){
                            Text(text = selectionOption)
                        }
                    }
                }
            }

            FlowRow (
                modifier = Modifier.fillMaxWidth().align(Alignment.End),

                ){
                Button(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    onClick = {

                            cScope.launch {
                                options = viewModel.listAvailableComPorts()
                                options.forEach {
                                    println(it)
                                }
                                viewModel.showMessage("COM Ports Refreshed")
                            }

                    },
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Refresh COM Ports")
                }
                Button(onClick = {
                    cScope.launch {
                        viewModel.disconnectSerialPort()
                    }
                },
                    modifier = Modifier.padding(horizontal = 5.dp),

                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Disconnect")
                }
                Button(
                    onClick = {
                            try {
                                cScope.launch {
                                    var res = viewModel.connectToSerialPort(selectedOptionText)
                                    println(viewModel.currentComPort.value)
                                   when(res){
                                       true -> viewModel.showMessage("Device Connected")
                                       false -> viewModel.showMessage("Device Failed To Connect")
                                   }


                                }
                            }catch (e:Exception){
                                e.printStackTrace()

                            }


                    },
                    modifier = Modifier.padding(horizontal = 5.dp),

                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Apply")
                }

            }
        }
    }
}



@Composable
fun Interrupt(viewModel: SerialViewModel =viewModel { SerialViewModel(getKoin().get(), getKoin().get()) }) {
    val toggleState by viewModel.isMachineOn.collectAsState()
    Card(
        modifier = Modifier.fillMaxWidth(),
                elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Text(
                "Toggle Operation State",
                style = MaterialTheme.typography.h5
            )
            Switch(
                checked = toggleState,
                onCheckedChange = {
                    viewModel.setMachineState(it)
                    println(viewModel.isMachineOn.value)

                }
            )
            Button(
                shape = RoundedCornerShape(25.dp),
                        onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.resetMachineState()
                    }
                }

            ) {
                Text("Reset Operation State")
            }

        }
    }
}

@Composable
fun DataProcessing(
    onExportToExcel: () -> Unit,
    onResetDatabase: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Export to Excel Button
            Button(
                shape = RoundedCornerShape(25.dp),
                onClick =
                    onExportToExcel
                    ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Text("Export to Excel")
            }

            // Reset Database Button
            Button(
                shape = RoundedCornerShape(25.dp),
                onClick = onResetDatabase,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("Reset Database", color = Color.White)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectableDropDown(options: List<SerialPort>) {
    var expanded by remember { mutableStateOf(false) }
    lateinit var selectedOptionText: String
    selectedOptionText = remember { mutableStateOf("") }.value

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Label") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption.portLocation
                        expanded = false
                    }
                ){
                    Text(text = selectionOption.portLocation)
                }
            }
        }
    }
}