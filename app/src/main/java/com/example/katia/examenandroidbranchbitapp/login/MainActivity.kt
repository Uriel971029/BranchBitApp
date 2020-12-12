package com.example.katia.examenandroidbranchbitapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.utils.Utils
import com.example.katia.examenandroidbranchbitapp.registro.RegistroActivity
import com.example.katia.examenandroidbranchbitapp.utils.dialogs.DialogFragmentLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : LoginView, AppCompatActivity() {

    private lateinit var mView: View
    private val textRegister: TextView by lazy { findViewById(R.id.txtRegister) }
    private val btnLogin: Button by lazy { findViewById(R.id.btnLogin) }
    private val edtCorreo: EditText by lazy { findViewById(R.id.editTextTextEmailAddress) }
    private val edtContrasenia: EditText by lazy { findViewById(R.id.editTextPassword) }
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var auth: FirebaseAuth
    private val mLoader: DialogFragmentLoader by lazy { DialogFragmentLoader.newInstance(getString(R.string.registrando)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = layoutInflater.inflate(R.layout.activity_main, null)
        setContentView(mView)
        bindListeners()
        initPresenter()
        initVars()
    }

    private fun initVars() {
        auth = Firebase.auth
        btnLogin.isEnabled = false
    }

    /**
     * Método ejecutado para inicializar el presentador para iniciar el flujo del Login
     */
    private fun initPresenter() {
        loginPresenter = LoginPresenter(this, LoginInteractor())
    }

    /**
     * Método ejecutado para asignar los eventos a sus vistas correspondientes
     */
    fun bindListeners() {

        textRegister.setOnClickListener({

            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        })

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

        btnLogin.setOnClickListener({
            loginPresenter.login(edtCorreo.text.toString(), edtContrasenia.text.toString(), auth)
        })
    }

    /**
     * Método ejecutado para habilitar el botón de login una vez se han ingresado las credenciales
     */
    private fun validForm() {
        if (!edtCorreo.text.isEmpty() && !edtContrasenia.text.isEmpty()) {
            btnLogin.isEnabled = true
        } else {
            btnLogin.isEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            Utils.navigateHome(this)
    }


    //    Metodos de la interfaz
    override fun onLoginExitoso() {
        Utils.navigateHome(this)
    }

    override fun onLoginError() {
        Utils.showMessage(mView, getString(R.string.login_error))
    }

    override fun showLoader() {
        mLoader.show(supportFragmentManager, "fragment_loader")
    }

    override fun hideLoader() {
        mLoader.dismiss()
    }

}