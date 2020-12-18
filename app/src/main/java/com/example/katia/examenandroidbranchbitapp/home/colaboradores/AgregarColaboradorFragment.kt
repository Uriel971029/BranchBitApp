package com.example.katia.examenandroidbranchbitapp.home.colaboradores

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.request.dto.CoordinatesDTO
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.google.android.gms.maps.model.LatLng

class AgregarColaboradorFragment : Fragment() {

    private lateinit var btnAgregar: Button
    private lateinit var edtNombre: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var mLister: OnAgregarColaboradorInterface

    companion object {
        fun newInstance(): AgregarColaboradorFragment {
            val args = Bundle()
            val fragment = AgregarColaboradorFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnAgregarColaboradorInterface {
        fun onAgregarColaborador(employeeDTO: EmployeeDTO)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAgregarColaboradorInterface)
            mLister = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(
            R.layout.agregarcolaborador_fragment,
            container,
            false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnAgregar = view.findViewById(R.id.btnAgregar)
        btnAgregar.isEnabled = false
        edtNombre = view.findViewById(R.id.edt_name)
        edtCorreo = view.findViewById(R.id.edt_mail)
        bindListeners()
    }

    private fun bindListeners() {

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validateForm()
            }


        }

        edtNombre.addTextChangedListener(watcher)
        edtCorreo.addTextChangedListener(watcher)

        btnAgregar.setOnClickListener({
//        recuperar db local para obtener lista y generar sig. id y agregar el elemento reciente
//            val employeeDTO = EmployeeDTO()
//            employeeDTO.nombre = edtNombre.text.toString()
//            employeeDTO.correo = edtCorreo.text.toString()
//            //generar random LatLong
//            employeeDTO.location = generateCoordinates()
//            mLister.onAgregarColaborador(employeeDTO)
        })
    }

    private fun generateCoordinates(): CoordinatesDTO {
        val minLat = -90.00
        val maxLat = 90.00
        val latitude = minLat + (Math.random() * (maxLat - minLat + 1))
        val minLon = 0.00
        val maxLon = 180.00
        val longitude = minLon + (Math.random() * (maxLon - minLon + 1))
        return CoordinatesDTO(latitude, longitude)
    }

    private fun validateForm() {
        //validar completado
        when {
            edtNombre.text.isEmpty() -> {
                edtNombre.setError(getString(R.string.completar_campo))
                edtNombre.requestFocus()
            }

            edtCorreo.text.isEmpty() -> {
                edtCorreo.setError(getString(R.string.completar_campo))
                edtCorreo.requestFocus()
            }

            !Patterns.EMAIL_ADDRESS.matcher(edtCorreo.text.toString()).matches() -> {
                edtCorreo.setError(getString(R.string.correo_invalido))
                edtCorreo.requestFocus()
            }

            else -> {
                btnAgregar.isEnabled = true
            }

        }

    }
}