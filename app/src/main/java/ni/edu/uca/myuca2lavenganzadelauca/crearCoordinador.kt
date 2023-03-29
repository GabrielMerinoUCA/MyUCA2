package ni.edu.uca.myuca2lavenganzadelauca

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ni.edu.uca.myuca2lavenganzadelauca.databinding.FragmentCrearCoordinadorBinding
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Creado por Merino A simple [Fragment] subclass.
 * Use the [crearCoordinador.newInstance] factory method to
 * create an instance of this fragment. No vaya a ser me roben
 * el codigo estos lacra
 */
class crearCoordinador : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentCrearCoordinadorBinding

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
        // Inflate the layout for this fragment por Merino
        fbinding = FragmentCrearCoordinadorBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.btnCrearCoordinador.setOnClickListener {
            sendPost()
            Navigation.findNavController(fbinding.root).navigate(R.id.acCrearCoordinador_Main)
        }
    }

    private fun sendPost() {
        val nombres: String = fbinding.etNombresCrear.text.toString()
        val apellidos: String = fbinding.etApellidosCrear.text.toString()
        val fechaNac: String =
            fbinding.etYearCrear.text.toString() + "-" + fbinding.etMesCrear.text.toString() + "-" + fbinding.etFechaCrear.text.toString()
        val titulo: String = fbinding.etTituloCrear.text.toString()
        val email: String = fbinding.etEmailCrear.text.toString()
        val facultad: String = fbinding.etFacultadCrear.text.toString()

        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val body = RequestBody.create(
            MediaType.parse("application/x-www-form-urlencoded"),
            "nombres=${nombres}" +
                    "&apellidos=${apellidos}" +
                    "&fechaNac=${fechaNac}" +
                    "&titulo=${titulo}" +
                    "&email=${email}" +
                    "&facultad=${facultad}"
        )

        val request = Request.Builder()
            .url("http://192.168.1.14/MyUCA2/insertarCoordinador.php")
            .method("POST", body)
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Guardado exitosamente", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Error de guardado -Merino", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })
    }


}