package com.example.katia.examenandroidbranchbitapp.home

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.utils.Utils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuFragment : Fragment() {

    private lateinit var btnColaboradores: Button
    private lateinit var btnAgregar: Button
    private lateinit var btnCerrar: Button
    private lateinit var mListener: OnHomeNavigation

    companion object {
        fun newInstance(): MenuFragment {
            val args = Bundle()
            val fragment = MenuFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeNavigation) {
            mListener = context
        }
    }


    interface OnHomeNavigation {
        fun onNavigateListColaboradores()
        fun onNavigateAgregarColaborador()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_menu,
            container,
            false
        )
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnColaboradores = view.findViewById(R.id.btnColaboradores)
        btnAgregar = view.findViewById(R.id.btnAgregar)
        btnCerrar = view.findViewById(R.id.btnCerrarSesion)
        bindListeners()
    }


    private fun bindListeners() {

        btnColaboradores.setOnClickListener({
            mListener.onNavigateListColaboradores()
        })

        btnAgregar.setOnClickListener({
            mListener.onNavigateAgregarColaborador()
        })

        btnCerrar.setOnClickListener({
            Firebase.auth.signOut()
            activity?.finish()
        })
    }





}