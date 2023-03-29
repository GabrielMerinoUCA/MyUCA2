package ni.edu.uca.myuca2lavenganzadelauca

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import ni.edu.uca.myuca2lavenganzadelauca.Adapters.CoordinadorAdapter
import ni.edu.uca.myuca2lavenganzadelauca.Modelos.Coordinador
import ni.edu.uca.myuca2lavenganzadelauca.databinding.FragmentMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Este codigo fue hecho por Merino
 * A simple [Fragment] subclass.
 * Use the [main.newInstance] factory method to
 * create an instance of this fragment.
 */
class main : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentMainBinding
    private lateinit var coordinadores: ArrayList<Coordinador>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fbinding = FragmentMainBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        }
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        /* Cuando inserto algo y regreso a main, creo que el tiempo en cargar en rv es mas rapido
        * que el tiempo en enviar. Esto es un problema porque nuevos registros no cargaran
        * inmediatamente. Creo que si le doy un par de segundos para que los envie correctamente,
        * quisas no pase ese problema. Pero es solo en teoria*/
        runBlocking {
            delay(2000)
            loadCoordinador()
        }
        fbinding.btnNuevo.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.acMain_CrearCoordinador)
        }
    }

    private fun loadCoordinador() {
        coordinadores = ArrayList()
        val client = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS) // Timeout para conectarse al server
            .readTimeout(10, TimeUnit.SECONDS) // Timeout para recibir respuesta del server
            .build()

        /* CAMBIAR URL */
        val apiUrl = "http://192.168.1.14/MyUCA2/obtenerCoordinadorFiltradoTitulo.php"
        val request: Request = Request.Builder()
            .url(apiUrl)
            .header("Connection", "close")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        val body = response.body()!!.string()
                        val jsonObject = JSONObject(body)
                        Log.wtf("RESPUESTA: ", body)
                        val dataArray = jsonObject.getJSONArray("data")
                        val leght = dataArray.length()
                        var i = 0
                        do {
                            if (leght != 0) {
                                val coordinadorJSON = dataArray.getJSONObject(i)

                                val idC: Int = coordinadorJSON.getString("idC").toInt()
                                val nombres: String = coordinadorJSON.getString("nombres")
                                val apellidos: String = coordinadorJSON.getString("apellidos")
                                val fechaNac: LocalDate = LocalDate.parse(
                                    coordinadorJSON.getString("fechaNac"),
                                    DateTimeFormatter.ISO_LOCAL_DATE
                                )
                                val titulo: String = coordinadorJSON.getString("titulo")
                                val email: String = coordinadorJSON.getString("email")
                                val facultad: String = coordinadorJSON.getString("facultad")

                                coordinadores.add(
                                    Coordinador(
                                        idC,
                                        nombres,
                                        apellidos,
                                        fechaNac,
                                        titulo,
                                        email,
                                        facultad
                                    )
                                )
                                i++
                            }
                        } while (i < leght - 1)

                        GlobalScope.launch(Dispatchers.Main) {
                            initRV()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        /* Esto evita que los datos no se carguen pero no es una muy buena solucion
                        * debido a que puede ocacionar bucles infinitos; y pues, no resuelve el
                        * problema de la exepcion que tira cuando no se reciben datos.*/
                        loadCoordinador()
                    }
                }
            }
        })
    }

    fun initRV() {
        val recyclerView = fbinding.rvCoordinacionFinal
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = CoordinadorAdapter(coordinadores, fbinding.root)
    }


}