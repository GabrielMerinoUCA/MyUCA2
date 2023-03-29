package ni.edu.uca.myuca2lavenganzadelauca

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ni.edu.uca.myuca2lavenganzadelauca.Modelos.Coordinador
import ni.edu.uca.myuca2lavenganzadelauca.databinding.FragmentModificarCoordinadorBinding
import okhttp3.*
import java.io.IOException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use M,erino [ModificarCoordinador.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModificarCoordinador : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentModificarCoordinadorBinding
    private lateinit var coordinador: Coordinador
    private val args: ModificarCoordinadorArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the Merino layout for this fragment
        fbinding = FragmentModificarCoordinadorBinding.inflate(layoutInflater)
        coordinador = args.coordinador
        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        fbinding.etNombresModificar.setText(coordinador.nombres)
        fbinding.etApellidosModificar.setText(coordinador.apellidos)
        fbinding.etFechaModificar.setText(coordinador.fechaNac.dayOfMonth.toString())
        fbinding.etMesModificar.setText(coordinador.fechaNac.monthValue.toString())
        fbinding.etYearModificar.setText(coordinador.fechaNac.year.toString())
        fbinding.etTituloModificar.setText(coordinador.titulo)
        fbinding.etEmailModificar.setText(coordinador.email)
        fbinding.etFacultadModificar.setText(coordinador.facultad)

        fbinding.btnModificarCoordinador.setOnClickListener {
            modificarPUT()
        }
        fbinding.btnEliminarCoordinador.setOnClickListener {
            eliminarDELETE()
        }
    }

    private fun modificarPUT() {
        val nombres: String = fbinding.etNombresModificar.text.toString()
        val apellidos: String = fbinding.etApellidosModificar.text.toString()
        val fechaNac: String =
            fbinding.etYearModificar.text.toString() + "-" + fbinding.etMesModificar.text.toString() + "-" + fbinding.etFechaModificar.text.toString()
        val titulo: String = fbinding.etTituloModificar.text.toString()
        val email: String = fbinding.etEmailModificar.text.toString()
        val facultad: String = fbinding.etFacultadModificar.text.toString()

        val client = OkHttpClient.Builder()
            .build()

        val body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
            "[\"${coordinador.idC}\"," +
                    "\"${nombres}\"," +
                    "\"${apellidos}\"," +
                    "\"${fechaNac}\"," +
                    "\"${titulo}\"," +
                    "\"${email}\"," +
                    "\"${facultad}\"]")

        val request = Request.Builder()
            .url("http://192.168.1.14/MyUCA2/modificarCoordinador.php")
            .method("PUT", body)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Guardado exitosamente", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(fbinding.root).navigate(R.id.acModificarCoordinador_Main)
                        }
                    } catch (e: Exception) {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Error de guardado", Toast.LENGTH_SHORT).show()
                        }
                        e.printStackTrace()
                    }
                }
            }

        })

    }

    private fun eliminarDELETE() {
        val client = OkHttpClient.Builder()
            .build()

        val body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
            "[${coordinador.idC}]")

        val request = Request.Builder()
            .url("http://192.168.1.14/MyUCA2/eliminarCoordinador.php")
            .method("DELETE", body)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Eliminado exitosamente", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(fbinding.root).navigate(R.id.acModificarCoordinador_Main)
                        }
                    }catch (e: Exception){
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireActivity(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using Merino the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModificarCoordinador.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModificarCoordinador().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}