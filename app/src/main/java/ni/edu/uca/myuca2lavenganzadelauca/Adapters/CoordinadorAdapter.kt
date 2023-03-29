package ni.edu.uca.myuca2lavenganzadelauca.Adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.myuca2lavenganzadelauca.Modelos.Coordinador
import ni.edu.uca.myuca2lavenganzadelauca.ModificarCoordinadorArgs
import ni.edu.uca.myuca2lavenganzadelauca.R
import ni.edu.uca.myuca2lavenganzadelauca.mainDirections
import java.time.format.DateTimeFormatter

class CoordinadorAdapter(var coordinadores: ArrayList<Coordinador>, val currentView: View) :
    RecyclerView.Adapter<CoordinadorAdapter.CoordinadorViewHolder>() {
    override fun getItemCount(): Int = coordinadores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoordinadorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CoordinadorViewHolder(layoutInflater.inflate(R.layout.rv_coordinador, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CoordinadorViewHolder, position: Int) {
        val coordinador: Coordinador = coordinadores[position]
        holder.load(coordinador, currentView)
    }

    inner class CoordinadorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombres = view.findViewById<TextView>(R.id.tvNombre)
        val apellidos = view.findViewById<TextView>(R.id.tvApellidos)
        val fechaNac = view.findViewById<TextView>(R.id.tvFechaNac)
        val titulo = view.findViewById<TextView>(R.id.tvTitulo)
        val email = view.findViewById<TextView>(R.id.tvEmail)
        val facultad = view.findViewById<TextView>(R.id.tvFacultad)
        val rvCoordinador = view.findViewById<ConstraintLayout>(R.id.rvCoordinacion)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun load(coordinador: Coordinador, origin: View){
            nombres.text = nombres.text.toString() + coordinador.nombres
            apellidos.text = apellidos.text.toString() + coordinador.apellidos
            fechaNac.text = fechaNac.text.toString() + coordinador.fechaNac.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            titulo.text = titulo.text.toString() + coordinador.titulo
            email.text = email.text.toString() + coordinador.email
            facultad.text = facultad.text.toString() + coordinador.facultad
            rvCoordinador.setOnClickListener {
                val action = mainDirections.acMainModificarCoordinador(coordinador)
                Navigation.findNavController(origin).navigate(action)
            }

        }

    }
}