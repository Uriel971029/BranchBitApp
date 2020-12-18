package com.example.katia.examenandroidbranchbitapp.request.requets

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.katia.examenandroidbranchbitapp.ResponseInterface
import com.example.katia.examenandroidbranchbitapp.request.RetrofitSingleton
import com.example.katia.examenandroidbranchbitapp.request.WebServices
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.request.dto.ResponseDTO
import com.example.katia.examenandroidbranchbitapp.utils.ContextApplication
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.JsonObject
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
import java.io.FileWriter
import java.util.zip.ZipFile

class ColaboradoresRequest(context: Context) : Callback<ResponseDTO> {

    private lateinit var jsonFileName: String
    private val FILENAME_KEY: String = "employees_data"
    private lateinit var zipFile: File

    private lateinit var mListener: OnColaboradoresRequestResponse

    interface OnColaboradoresRequestResponse : ResponseInterface {
        fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?)
        fun onColaboradoresObtenidosError(message: String)
    }

    fun getColaboradores(listener: OnColaboradoresRequestResponse) {
        mListener = listener
        val getMethos =
            RetrofitSingleton.getInstance().create(WebServices.Methods.GetMethods::class.java)
        val request = getMethos.getDataEmployees()
        request.enqueue(this)
    }


    override fun onResponse(call: Call<ResponseDTO>?, response: Response<ResponseDTO>?) {
        var responseDTO: ResponseDTO = response?.body() as ResponseDTO
        //descargar zip
        downloadZipFile(responseDTO.data.urlFile)
    }

    private fun getDataFromJson() {
        val gson = Gson()
        //open file json
        val jsonFile = File("${ContextApplication.getContextApplication().filesDir}/$jsonFileName")
        //read content
        val bufferReader = jsonFile.bufferedReader()
        val inputString = bufferReader.use { it.readText() }
        Log.d("json_result", inputString)
        //parse info
        val jsonObject = JSONObject(inputString)
        Log.d("json_result", jsonObject.toString())
        val dataObject = jsonObject.optJSONObject("data")
        val jsonArray : JSONArray = dataObject.get("employees") as JSONArray
        val employees  = ArrayList<EmployeeDTO>()

        for(i in 0..(jsonArray.length() - 1)){
            val jsonObject = jsonArray.optJSONObject(i)
//            val employeeDTO = EmployeeDTO(jsonObject)
//            employees.add(employeeDTO)
            employees.add(gson.fromJson(jsonObject.toString(), EmployeeDTO::class.java))
        }

        mListener.onColaboradoresObtenidos(employees)
    }

    fun downloadZipFile(url: String) {
        zipFile = File.createTempFile(FILENAME_KEY,
            ".zip",
            ContextApplication.getContextApplication().filesDir)

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val (_, result, error) =
                    Fuel.download(url).fileDestination { response, request ->
                        //se persiste el archivo
                        zipFile
                    }.progress { readBytes, totalBytes ->
                        println("Downloading $readBytes bytes")
                    }.response()
                if (result.statusCode == 200) {
                    println("Download completed")
                    //unzip the file
                    unzipFile()
                } else {
                    println("Download error")
                }
            }
        }
    }


    private fun unzipFile() {
        val zip = ZipFile("${ContextApplication.getContextApplication().filesDir}/${zipFile.name}")
        val enumeration = zip.entries()
        while (enumeration.hasMoreElements()) {
            val entry = enumeration.nextElement()
            jsonFileName = entry.name
            val destFilePath = File(ContextApplication.getContextApplication().filesDir, jsonFileName)
            destFilePath.parentFile.mkdirs()
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
        //extract info from file
        getDataFromJson()
    }

    override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {
        mListener.onColaboradoresObtenidosError(t!!.message.toString())
    }

}