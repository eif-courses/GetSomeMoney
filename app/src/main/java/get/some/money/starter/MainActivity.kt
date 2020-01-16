package get.some.money.starter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import get.some.money.starter.Models.User
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_layout.*

class MainActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener{

  private lateinit var appBarConfiguration: AppBarConfiguration;
  private val RC_SIGN_IN = 123
  lateinit var userModel: UserViewModel
  private val uuid = FirebaseAuth.getInstance().currentUser?.uid
  val providers = arrayListOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build()
  )




  override fun onStart() {
    super.onStart()
    // Create and launch sign-in intent
    if (uuid == null) {
      startActivityForResult(
        AuthUI.getInstance()
          .createSignInIntentBuilder()
          .setAvailableProviders(providers)
          .build(),
        RC_SIGN_IN
      )
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    userModel = ViewModelProviders.of(this)[UserViewModel::class.java]

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar) //set the toolbar
    val host: NavHostFragment = supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
    val navController = host.navController
    appBarConfiguration = AppBarConfiguration(navController.graph) //configure nav controller
    setupNavigation(navController) //setup navigation
    setupActionBar(navController, appBarConfiguration) // setup action bar
    //hear for event changes
//    navController.addOnDestinationChangedListener { _, destination, _ ->
//      val dest: String = try {
//        resources.getResourceName(destination.id)
//      } catch (e: Resources.NotFoundException) {
//        Integer.toString(destination.id)
//      }
//      Toast.makeText(
//        this@MainActivity, "Navigated to $dest",
//        Toast.LENGTH_SHORT
//      ).show()
//      Log.d("NavigationActivity", "Navigated to $dest")
//    }
  }

  private fun setupActionBar(
    navController: NavController,
    appBarConfig: AppBarConfiguration
  ) {
    setupActionBarWithNavController(navController, appBarConfig)
  }

  private fun setupNavigation(navController: NavController) {
    val sideNavView = findViewById<NavigationView>(R.id.nav_view)
    sideNavView?.setupWithNavController(navController)
    val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)

    //fragments load from here but how ?
    appBarConfiguration = AppBarConfiguration(
      setOf(R.id.homeFragment, R.id.shopFragment),
      drawerLayout
    )
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val retValue = super.onCreateOptionsMenu(menu)
    val navigationView = findViewById<NavigationView>(R.id.nav_view)
    if (navigationView == null) {
      //android needs to know what menu I need
      menuInflater.inflate(R.menu.activity_main_drawer, menu)
      return true
    }
    return retValue
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    //I need to open the drawer onClick
    when (item!!.itemId) {
      android.R.id.home ->
        drawer_layout.openDrawer(GravityCompat.START)
    }
    return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
        || super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == RC_SIGN_IN) {
      val response = IdpResponse.fromResultIntent(data)

      if (resultCode == Activity.RESULT_OK) {
        // Successfully signed in
        val us = FirebaseAuth.getInstance().currentUser
        userModel.getUser(us?.uid.toString()).observe(this, Observer {

          profileEmail.text = "Your total score: ${it.score.toString()}"
          //  your_score.text = it.score.toString()
          if (us?.uid.equals(it.uuid)){
            Toast.makeText(this, "SVEIKI SUGRIZE $it", Toast.LENGTH_LONG).show()
          }else{
            userModel.saveUser(User("Marius", 0, us?.uid.toString(), 500, 555, 30))
          }


          println("=====================================$it")
        }
        )
        Picasso.get().load(us?.photoUrl).into(profileImage)
        profileName.text = us?.displayName


        // ...
      } else {
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
      }
    }
  }

  override fun onBackPressed() {
    //the code is beautiful enough without comments
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }
}

