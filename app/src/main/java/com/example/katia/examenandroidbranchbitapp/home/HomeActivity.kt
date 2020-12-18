package com.example.katia.examenandroidbranchbitapp.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.AgregarColaboradorFragment
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.ColaboradoresFragment
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.MapsFragment
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.utils.Utils
import com.example.katia.examenandroidbranchbitapp.utils.dialogs.DialogFragmentLoader


class HomeActivity : HomeView, MenuFragment.OnHomeNavigation,
    ColaboradoresFragment.OnRequestColaboradores,
    Utils.OnMessagePressed, AppCompatActivity() {

    private val STORAGE_REQUEST_CODE = 100
    private lateinit var mView: View
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private lateinit var homePresenter: HomePresenter
    private val MENU_FRAGMENT = "menu_fragment"
    private val LISTA_FRAGMENT = "lista_fragment"
    private val AGREGAR_FRAGMENT = "agregar_fragment"
    private val MAPA_FRAGMENT = "mapa_fragment"

    private val mLoader: DialogFragmentLoader by lazy { DialogFragmentLoader.newInstance(getString(R.string.cargando)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = layoutInflater.inflate(R.layout.activity_home, null)
        setContentView(mView)
        setToolbar()
        initPresenter()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container_view,
                    MenuFragment.newInstance(),
                    MENU_FRAGMENT
                )
                .commit()
        }
    }

    private fun initPresenter() {
        homePresenter = HomePresenter(this, HomeInteractor())
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(getString(R.string.inicio))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item);
    }


    //    Metodos de la Interfaz
    override fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?) {
        var colaboradoresFragment =
            supportFragmentManager.findFragmentByTag(LISTA_FRAGMENT) as ColaboradoresFragment
        colaboradoresFragment.setEmployees(employees)
    }

    override fun onObtenerColaboresError() {
    }

    override fun showLoader() {
        mLoader.show(supportFragmentManager, "fragment_loader")

    }

    override fun hideLoader() {
        mLoader.dismiss()
    }

    override fun onNavigateListColaboradores() {
        supportActionBar?.setTitle(getString(R.string.mis_colaboradores))
        //request for user permission
        checkStoragePermissions()
    }

    private fun checkStoragePermissions() {

        when {
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                navigate2Fragment(ColaboradoresFragment.newInstance(), LISTA_FRAGMENT)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                Utils.showAlert(
                    this,
                    getString(R.string.informacion),
                    getString(R.string.permisos_explicacion),
                    getString(R.string.aceptar),
                    getString(R.string.denegar)
                )
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                Utils.showAlert(
                    this,
                    getString(R.string.informacion),
                    getString(R.string.permisos_explicacion),
                    getString(R.string.aceptar),
                    getString(R.string.denegar)
                )
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_REQUEST_CODE)
            }

        }
    }

    override fun onNavigateAgregarColaborador() {
        supportActionBar?.setTitle(getString(R.string.agregar_colaborador))
        navigate2Fragment(AgregarColaboradorFragment.newInstance(), AGREGAR_FRAGMENT)
    }

    fun navigate2Fragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment, tag).addToBackStack(null).commit()
    }

    override fun onGetColaboradores() {
        homePresenter.getColaboradores()
    }

    override fun onSearchColaboradorInMap(employee: EmployeeDTO) {
        supportActionBar?.setTitle(getString(R.string.agregar_colaborador))
        navigate2Fragment(MapsFragment.newInstance(employee), MAPA_FRAGMENT)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {

            STORAGE_REQUEST_CODE -> {

                if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // access granted
                    navigate2Fragment(ColaboradoresFragment.newInstance(), LISTA_FRAGMENT)
                } else {
                    //access denied
                    //don't show the list
                }
                return
            }
            else -> {
                // Another unknow request
            }
        }
    }

    override fun onMessagePositiveButtonPressed() {
        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_REQUEST_CODE
        )
    }
}