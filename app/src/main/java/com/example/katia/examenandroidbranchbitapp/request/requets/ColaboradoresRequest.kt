package com.example.katia.examenandroidbranchbitapp.request.requets

import android.content.Context
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.example.katia.examenandroidbranchbitapp.data.AppDatabase
import com.example.katia.examenandroidbranchbitapp.request.RetrofitSingleton
import com.example.katia.examenandroidbranchbitapp.request.WebServices
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.request.dto.ResponseDTO
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.util.zip.ZipFile

class ColaboradoresRequest(val context: Context) : Callback<ResponseDTO> {

    private lateinit var jsonFileName: String
    private val FILENAME_KEY: String = "employees_data"
    private lateinit var zipFile: File

    private lateinit var mListener: OnColaboradoresRequestResponse

    interface OnColaboradoresRequestResponse : ResponseInterface {
        fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?)
        fun onColaboradoresObtenidosError(message: String)
        fun onColaboradorAgregado()
    }

    fun addEmployee(employeeDTO: EmployeeDTO, listener: OnColaboradoresRequestResponse) {
        mListener = listener
        GlobalScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                val db = AppDatabase.getInstance(context)
                val employeeDAO = db?.employeeDao()
                employeeDAO?.insert(employeeDTO)
                mListener.onColaboradorAgregado()
            }
        }
    }

    fun getColaboradores(listener: OnColaboradoresRequestResponse) {
        mListener = listener
        val employees = getLocalInfo()
        if (employees?.size?.compareTo(0)!! > 0) {
            mListener.onColaboradoresObtenidos(employees)
        } else {
            //request info from the server
            val getMethod =
                RetrofitSingleton.getInstance().create(WebServices.Methods.GetMethods::class.java)
            val request = getMethod.getDataEmployees()
            request.enqueue(this)
        }
    }

    //El error  radica en la función asincrona ya que regresa un arreglo de datos vacio de momento y se vuelve a descargar y por tanto
    //almacenar de manera local los mismos empleados causando la excepción de id ya existente en la tabla empleados
    fun getLocalInfo(): ArrayList<EmployeeDTO>? {
        val employees = ArrayList<EmployeeDTO>()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                val employeeDAO = db?.employeeDao()
                employeeDAO?.getAll()?.let { employees.addAll(it) }
            }
        }
        return employees
    }

    override fun onResponse(call: Call<ResponseDTO>?, response: Response<ResponseDTO>?) {
        var responseDTO: ResponseDTO = response?.body() as ResponseDTO
        //descargar zip
        downloadZipFile(responseDTO.data.urlFile)
    }

    /**
     * Método ejecutado para obtener la información del archivo json
     */
    private fun getDataFromJson() {
        val gson = Gson()
        //open file json
        val jsonFile = File("${context.filesDir}/$jsonFileName")
        //read content
        val bufferReader = jsonFile.bufferedReader()
        val inputString = bufferReader.use { it.readText() }
        Log.d("json_result", inputString)
        //parse info
        val jsonObject = JSONObject(inputString)
        Log.d("json_result", jsonObject.toString())
        val dataObject = jsonObject.optJSONObject("data")
        val jsonArray: JSONArray = dataObject.opt("employees") as JSONArray
        val employees = ArrayList<EmployeeDTO>()

        for (i in 0..(jsonArray.length() - 1)) {
            val jsonObject = jsonArray.optJSONObject(i)
            employees.add(gson.fromJson(jsonObject.toString(), EmployeeDTO::class.java))
        }
        //persistimos la info
        storeEmployees(employees)
    }

    /**
     * Método ejecutado para persistir la información de la lista de empleado
     */
    private fun storeEmployees(employees: ArrayList<EmployeeDTO>) {
        val db = AppDatabase.getInstance(context);
        val employeeDAO = db?.employeeDao()
        for (employee in employees) {
            employeeDAO?.insert(employee)
        }
        mListener.onColaboradoresObtenidos(employees)
    }

    /**
     * Método ejecutado para descargar el archivo zip del servidor
     * @param url direción url de descarga
     */
    fun downloadZipFile(url: String) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                zipFile = File.createTempFile(FILENAME_KEY, ".zip", context.filesDir)
                val (_, result, error) =
                    Fuel.download(url).fileDestination { response, request ->
                        zipFile
                    }.progress { readBytes, totalBytes ->
                        println("Downloading $readBytes bytes")
                    }.response()
                if (result.statusCode == 200) {
                    println("Download completed")
                    unzipFile()
                } else {
                    println("Download error: $error")
                    Log.d("Error", error.toString())
                }
            }
        }
    }


    /**
     * Método ejecutado para desempaquetar el archivo zip descargado del servidor
     */
    private fun unzipFile() {
        val zip = ZipFile("${context.filesDir}/${zipFile.name}")
        val enumeration = zip.entries()
        while (enumeration.hasMoreElements()) {
            val entry = enumeration.nextElement()
            jsonFileName = entry.name
            val destFilePath = File(context.filesDir, jsonFileName)
            destFilePath?.parentFile.mkdirs()
            if (entry.isDirectory)
                continue
            val bufferedIs = BufferedInputStream(zip.getInputStream(entry))
            bufferedIs.use {
                destFilePath.outputStream().buffered(1024).use { bos ->
                    bufferedIs.copyTo(bos)
                }
            }

        }
        //delete tempFile
        zipFile.deleteOnExit()
        getDataFromJson()
    }

    override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {
        mListener.onColaboradoresObtenidosError(t!!.message.toString())
    }

}