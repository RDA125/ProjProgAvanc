package com.example.projprogavanc

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projprogavanc.databinding.ActivityMainBinding
import com.example.projprogavanc.ui.gallery.WishlistFragment
import com.example.projprogavanc.ui.home.HomeFragment
import com.example.projprogavanc.ui.slideshow.GameListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var currMenuId = R.menu.main
        get() = field
        set(value){
            if(value != field){
                field = value
                invalidateOptionsMenu()

            }
        }

    var menu: Menu? = null

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.Wishlist, R.id.GameList, R.id.StoreList
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(currMenuId, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val processedOption: Boolean

        if(fragment is HomeFragment){

            processedOption = (fragment as HomeFragment).processOptionMenu(item)

        }else if(fragment is WishlistFragment){

            processedOption = (fragment as WishlistFragment).processOptionMenu(item)

        }else if(fragment is EditGameStoreFragment){

            processedOption = (fragment as EditGameStoreFragment).processOptionMenu(item)

        }else if(fragment is DeleteGameStoreFragment){

            processedOption = (fragment as DeleteGameStoreFragment).processOptionMenu(item)

        }else if(fragment is GameListFragment){

           // processedOption = (fragment as GameListFragment).processOptionMenu(item)
            processedOption = false
        }else if(fragment is EditGameFragment){

            //processedOption = (fragment as EditGameFragment).processOptionMenu(item)
            processedOption = false
        }else if(fragment is DeleteGameFragment){

           // processedOption = (fragment as DeleteGameFragment).processOptionMenu(item)
            processedOption = false
        }else{

            processedOption = false
        }

        if(processedOption){
            return true
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun ShowEditDeleteOptions(showOption: Boolean){
        menu!!.findItem(R.id.action_edit).setVisible(showOption)
        menu!!.findItem(R.id.action_delete).setVisible(showOption)

    }

    fun updateTitle(title: String) {
        binding.appBarMain.toolbar.setTitle(title)
    }


}