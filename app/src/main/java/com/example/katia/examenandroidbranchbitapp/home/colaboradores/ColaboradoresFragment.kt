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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ColaboradoresFragment : ColaboradorAdapter.OnColaboradorPressed, Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val employees: ArrayList<EmployeeDTO> by lazy { ArrayList() }
    private lateinit var colaboradorAdapter: ColaboradorAdapter
    private lateinit var mListener: OnRequestColaboradores

    companion object {
        fun newInstance(): ColaboradoresFragment {
            val args = Bundle()
            val fragment = ColaboradoresFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun setEmployees(list: ArrayList<EmployeeDTO>?) {
        if (list != null) {
            if(employees.size > 0)
                employees.clear()
            employees.addAll(list)
            colaboradorAdapter = ColaboradorAdapter(employees)
            colaboradorAdapter.setListener(this)
            GlobalScope.launch(Dispatchers.Main) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = colaboradorAdapter
                }
            }
        }
    }

    interface OnRequestColaboradores {
        fun onGetColaboradores()
        fun onSearchColaboradorInMap(employee: EmployeeDTO)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRequestColaboradores) {
            mListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(
            R.layout.colaboradores_fragment,
            container,
            false
        )
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_colaboradores)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListener.onGetColaboradores()
    }

    override fun onColaboradorSearched(employee: EmployeeDTO) {
        mListener.onSearchColaboradorInMap(employee)
    }
}