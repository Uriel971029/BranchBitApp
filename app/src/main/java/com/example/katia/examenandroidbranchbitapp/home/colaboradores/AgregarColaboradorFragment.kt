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
import androidx.lifecycle.lifecycleScope
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.data.AppDatabase
import com.example.katia.examenandroidbranchbitapp.request.dto.CoordinatesDTO
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.utils.ContextApplication
import com.squareup.okhttp.Dispatcher
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.agregarcolaborador_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarColaboradorFragment : Fragment() {

    private lateinit var btnAgregar: Button
    private lateinit var edtNombre: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var mLister: OnAgregarColaboradorInterface

    private var campoNombre: Boolean = false
    private var campoCorreo: Boolean = false

    companion object {
        private const val LAST_ID = "last_id"
        fun newInstance(lastId: Int): AgregarColaboradorFragment {
            val args = Bundle()
            args.putInt(LAST_ID, lastId)
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

            override fun afterTextChanged(editable: Editable?) {
                validateForm(activity?.currentFocus)
            }

        }

        edtNombre.addTextChangedListener(watcher)
        edtCorreo.addTextChangedListener(watcher)

        btnAgregar.setOnClickListener({
            val employeeDTO = EmployeeDTO(
                arguments?.getInt(LAST_ID)?.plus(1),
                generateRandomCoordinates(),
                edtNombre.text.toString(),
                edtCorreo.text.toString()
            )
            mLister.onAgregarColaborador(employeeDTO)
        })
    }

    /**
     * Método para validar el completado adecuado de los datos
     */
    private fun validateForm(campo: View?) {

        when {
            campo == edtNombre -> {
                when {
                    edtNombre.text.isEmpty() -> {
                        edtNombre.setError(getString(R.string.completar_campo))
                        edtNombre.requestFocus()
                        campoNombre = false
                    }
                    else -> {
                        campoNombre = true
                    }
                }
            }

            campo == edtCorreo -> {
                when {
                    edtCorreo.text.isEmpty() -> {
                        edtCorreo.setError(getString(R.string.completar_campo))
                        edtCorreo.requestFocus()
                        campoCorreo = false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(edtCorreo.text.toString()).matches() -> {
                        edtCorreo.setError(getString(R.string.correo_invalido))
                        edtCorreo.requestFocus()
                        campoCorreo = false
                    }
                    else -> {
                        campoCorreo = true
                    }
                }
            }
        }

        if (campoNombre && campoCorreo)
            btnAgregar.isEnabled = true
    }

    /**
     * Método ejecutado para generar coordenas aleatorias
     */
    private fun generateRandomCoordinates(): CoordinatesDTO {
        val minLat = -90.00
        val maxLat = 90.00
        val latitude = minLat + (Math.random() * (maxLat - minLat + 1))
        val minLon = 0.00
        val maxLon = 180.00
        val longitude = minLon + (Math.random() * (maxLon - minLon + 1))
        return CoordinatesDTO(latitude, longitude)
    }

}