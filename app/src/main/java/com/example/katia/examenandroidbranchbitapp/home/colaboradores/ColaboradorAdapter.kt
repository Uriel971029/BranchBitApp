package com.example.katia.examenandroidbranchbitapp.home.colaboradores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO

class ColaboradorAdapter(private val employees: ArrayList<EmployeeDTO>) :
    RecyclerView.Adapter<ColaboradorAdapter.MyViewHolder>() {

    private lateinit var mListener: OnColaboradorPressed

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var nombre = view.findViewById<TextView>(R.id.textNombre)
        var contacto = view.findViewById<TextView>(R.id.textContacto)
    }

    interface OnColaboradorPressed {
        fun onColaboradorSearched(employee: EmployeeDTO)
    }

    fun setListener(listener: OnColaboradorPressed) {
            mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.colaborador_adapater, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nombre.text = employees.get(position).nombre
        holder.contacto.text = employees.get(position).correo

        holder.view.setOnClickListener({
            mListener.onColaboradorSearched(employees.get(position))
        })
    }

    override fun getItemCount(): Int = employees.size

}