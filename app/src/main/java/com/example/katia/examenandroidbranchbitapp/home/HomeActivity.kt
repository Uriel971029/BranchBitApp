package com.example.katia.examenandroidbranchbitapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.AgregarColaboradorFragment
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.ColaboradoresFragment
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : HomeView, AppCompatActivity() {

    private val btnColaboradores: Button by lazy { findViewById(R.id.btnColaboradores) }
    private val btnAgregar: Button by lazy { findViewById(R.id.btnAgregar) }
    private val btnCerrar: Button by lazy { findViewById(R.id.btnCerrarSesion) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bindListeners()
        setToolbar()
        initPresenter()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.cl_colaboradores,
                    ColaboradoresFragment.newInstance(),
                    "colaboradores_fragment"
                )
                .commit()
        }
    }

    private fun initPresenter() {
        homePresenter = HomePresenter(this, HomeInteractor())
        homePresenter.getColaboradores()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(getString(R.string.inicio))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun bindListeners() {

        btnColaboradores.setOnClickListener({
            navigate2Fragment(ColaboradoresFragment.newInstance())
        })

        btnAgregar.setOnClickListener({
            navigate2Fragment(AgregarColaboradorFragment.newInstance())
        })

        btnCerrar.setOnClickListener({
            Firebase.auth.signOut()
            finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item);
    }

    fun navigate2Fragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

//    Metodos de la Interfaz
    override fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?) {

    }

    override fun onObtenerColaboresError() {
    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }
}