package com.example.katia.examenandroidbranchbitapp.registro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.utils.Utils
import com.example.katia.examenandroidbranchbitapp.utils.dialogs.DialogFragmentLoader
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : RegistroView, AppCompatActivity() {

    private val edtCorreo: EditText by lazy { findViewById(R.id.editTextCorreo) }
    private val edtContrasenia: EditText by lazy { findViewById(R.id.editTextPassword) }
    private val edtConfirmacion: EditText by lazy { findViewById(R.id.editTextConfirmar) }
    private val btnRegistrar: Button by lazy { findViewById(R.id.btnRegister) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private lateinit var registroPresenter: RegistroPresenter
    private lateinit var auth: FirebaseAuth
    private lateinit var mView: View
    private val mLoader: DialogFragmentLoader by lazy { DialogFragmentLoader.newInstance(getString(R.string.registrando)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = layoutInflater.inflate(R.layout.activity_registro, null)
        setContentView(mView)
        initVars()
        setListeners()
        initPresenter()
    }

    /**
     * Método ejecutado para configurar las características del Toolbar
     */
    private fun initVars() {
        Utils.setToolbar(
            this,
            toolbar,
            getString(R.string.registro),
            true
        )
        auth = Firebase.auth
        btnRegistrar.isEnabled = false
    }

    /**
     * Método ejecutado para inicializar el presentador para iniciar el flujo del Login
     */
    private fun initPresenter() {
        registroPresenter = RegistroPresenter(this, RegistroInteractor())
    }

    /**
     * Método ejecutado para asignar los eventos a sus vistas correspondientes
     */
    private fun setListeners() {

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validForm()
            }
        }
        edtCorreo.addTextChangedListener(watcher)
        edtContrasenia.addTextChangedListener(watcher)
        edtConfirmacion.addTextChangedListener(watcher)

        btnRegistrar.setOnClickListener({
            registroPresenter.registrarUsuario(
                edtCorreo.text.toString(),
                edtContrasenia.text.toString(),
                auth
            )
        })
    }

    /**
     * Método ejecutado para habilitar el botón de login una vez se han ingresado las credenciales
     */
    private fun validForm() {
        if (!edtCorreo.text.isBlank() && !edtContrasenia.text.isBlank() && !edtConfirmacion.text.isBlank()) {
            if (edtContrasenia.text.toString().equals(edtConfirmacion.text.toString())) {
                btnRegistrar.isEnabled = true
            } else {
                btnRegistrar.isEnabled = false
            }
        } else {
            btnRegistrar.isEnabled = false
        }
    }

    //Metodos de la interfaz
    override fun onRegistroExitoso() {
        Utils.showMessage(mView, getString(R.string.registro_exitoso))
        Utils.navigateHome(this)
    }

    override fun onRegistroError() {
        Utils.showMessage(mView, getString(R.string.registro_error))
    }


    override fun showLoader() {
        mLoader.show(supportFragmentManager, "fragment_loader")
    }

    override fun hideLoader() {
        mLoader.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        registroPresenter.destroy()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            Utils.navigateHome(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item);
    }
}