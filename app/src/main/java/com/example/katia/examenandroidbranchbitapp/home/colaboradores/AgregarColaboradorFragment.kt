package com.example.katia.examenandroidbranchbitapp.home.colaboradores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.katia.examenandroidbranchbitapp.R

class AgregarColaboradorFragment : Fragment() {

    companion object {
        fun newInstance(): AgregarColaboradorFragment {
            val args = Bundle()
            val fragment = AgregarColaboradorFragment()
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
            R.layout.agregarcolaborador_fragment,
            container,
            false
        )
        return view
    }
}