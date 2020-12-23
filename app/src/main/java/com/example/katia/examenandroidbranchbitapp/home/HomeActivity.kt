package com.example.katia.examenandroidbranchbitapp.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.katia.examenandroidbranchbitapp.R
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.AgregarColaboradorFragment
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.ColaboradoresFragment
import com.example.katia.examenandroidbranchbitapp.home.colaboradores.MapsFragment
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO
import com.example.katia.examenandroidbranchbitapp.utils.Utils
import com.example.katia.examenandroidbranchbitapp.utils.dialogs.DialogFragmentLoader
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeActivity : HomeView,
    MenuFragment.OnHomeNavigation,
    ColaboradoresFragment.OnRequestColaboradores,
    AgregarColaboradorFragment.OnAgregarColaboradorInterface,
    Utils.OnMessagePressed,
    AppCompatActivity() {

    private var LAST_ID : Int? = 0
    private lateinit var mView: View
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private lateinit var homePresenter: HomePresenter
    private val MENU_FRAGMENT = "menu_fragment"
    private val LISTA_FRAGMENT = "lista_fragment"
    private val AGREGAR_FRAGMENT = "agregar_fragment"
    private val MAPA_FRAGMENT = "mapa_fragment"
    private val mLoader: DialogFragmentLoader by lazy { DialogFragmentLoader.newInstance(getString(R.string.cargando)) }
    private val STORAGE_REQUEST_CODE = 100
    private var isPermissionsAccepted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = layoutInflater.inflate(R.layout.activity_home, null)
        setContentView(mView)
        setToolbar()
        initPresenter()
        if (savedInstanceState == null) {
            checkStoragePermissions()
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

    companion object {

    }


    private fun initPresenter() {
        homePresenter = HomePresenter(this, HomeInteractor())
    }

    private fun setToolbar() {
        Utils.setToolbar(
            this,
            toolbar,
            getString(R.string.inicio),
            false
        )
    }

    private fun checkStoragePermissions() {
        when {
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                isPermissionsAccepted = true

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
                requestStoragePermissions()
            }
        }
    }

    private fun requestStoragePermissions() {
        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_REQUEST_CODE)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item);
    }


    //    Metodos de la Interfaz
    override fun onColaboradoresObtenidos(employees: ArrayList<EmployeeDTO>?) {
        LAST_ID = employees?.size?.let { employees.get(it - 1).id }
        var colaboradoresFragment =
            supportFragmentManager.findFragmentByTag(LISTA_FRAGMENT) as ColaboradoresFragment
        colaboradoresFragment.setEmployees(employees)
    }

    override fun onObtenerColaboresError() {
        Utils.showAlert(
            this,
            getString(R.string.error),
            getString(R.string.error_obtener_employees),
            getString(R.string.aceptar),
            getString(R.string.cancelar)
        )
    }

    override fun onEmployeeAgregado() {
        onNavigateListColaboradores()
    }

    override fun showLoader() {
        mLoader.show(supportFragmentManager, "fragment_loader")
    }

    override fun hideLoader() {
        mLoader.dismiss()
    }

    override fun onNavigateListColaboradores() {
        if (isPermissionsAccepted) {
            supportActionBar?.setTitle(getString(R.string.mis_colaboradores))
            navigate2Fragment(ColaboradoresFragment.newInstance(), LISTA_FRAGMENT)
        } else {
            checkStoragePermissions()
        }
    }


    override fun onNavigateAgregarColaborador() {
        if (isPermissionsAccepted) {
            supportActionBar?.setTitle(getString(R.string.agregar_colaborador))
            navigate2Fragment(AgregarColaboradorFragment.newInstance(LAST_ID!!), AGREGAR_FRAGMENT)
        } else {
            checkStoragePermissions()
        }
    }

    fun navigate2Fragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment, tag).addToBackStack(null).commit()
    }

    override fun onGetColaboradores() {
        homePresenter.getColaboradores()
    }

    override fun onSearchColaboradorInMap(employee: EmployeeDTO) {
        Utils.setToolbar(
            this,
            toolbar,
            getString(R.string.mapa_colaboradores),
            true
        )
        supportActionBar?.setTitle(getString(R.string.agregar_colaborador))
        navigate2Fragment(MapsFragment.newInstance(employee), MAPA_FRAGMENT)
    }

    override fun onAgregarColaborador(employeeDTO: EmployeeDTO) {
        homePresenter.addEmployee(employeeDTO)
    }

    override fun onMessagePositiveButtonPressed() {
        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_REQUEST_CODE
        )
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
                    isPermissionsAccepted = true
                } else {
                    //access denied
                    isPermissionsAccepted = false
                }
                return
            }
            else -> {
                // Another unknow request
            }
        }
    }
}