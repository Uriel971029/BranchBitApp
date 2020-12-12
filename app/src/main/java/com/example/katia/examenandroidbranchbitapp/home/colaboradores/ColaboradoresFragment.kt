package com.example.katia.examenandroidbranchbitapp.home.colaboradores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO

class ColaboradoresFragment : Fragment() {

    private lateinit var employees: ArrayList<EmployeeDTO>

    companion object {
        fun newInstance(): ColaboradoresFragment {
            val args = Bundle()
            val fragment = ColaboradoresFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.colaboradores_fragment,
            container,
            false
        )
        val activity = activity as Context
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_colaboradores)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ColaboradorAdapter(employees)
        return view
    }
}