package get.some.money.starter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
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
import get.some.money.starter.Adapters.IntroSliderAdapter
import get.some.money.starter.Models.IntroSlide
import get.some.money.starter.Models.User
import get.some.money.starter.Util.Language
import get.some.money.starter.ViewModels.LevelViewModel
import get.some.money.starter.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener {

  private lateinit var appBarConfiguration: AppBarConfiguration
  private val RC_SIGN_IN = 123
  val userModel: UserViewModel by viewModels()
  val levelModel: LevelViewModel by viewModels()



  var uuid = FirebaseAuth.getInstance().currentUser?.uid
  val providers = arrayListOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build()
  )


  override fun onStart() {
    super.onStart()

    // Create and launch sign-in intent


//    val pref = SharedPreference(this)
//
//    pref.save("FIRST_RUN", true)
//
//    val isFirstRun = pref.getValueBoolien("FIRST_RUN", true)
//
//    if(isFirstRun) {
//      startActivityForResult(
//        AuthUI.getInstance()
//          .createSignInIntentBuilder()
//          .setAvailableProviders(providers)
//          .build(),
//        RC_SIGN_IN
//      )
//      pref.save("FIRST_RUN", false)

    //pref.clearSharedPreference()
    //}
    if (uuid == null) {
      startActivityForResult(
        AuthUI.getInstance()
          .createSignInIntentBuilder()
          .setAvailableProviders(providers)
          .build(),
        RC_SIGN_IN
      )

    } else {


      userModel.getUser(uuid!!).observe(this, Observer {

        if (it != null) {
          val navigationView = findViewById<NavigationView>(R.id.nav_view)
          val header = navigationView.getHeaderView(0)
          val profileName = header.findViewById<TextView>(R.id.Nickname)
          val editName = header.findViewById<ImageView>(R.id.edit_text_header)
          profileName.text = it.name

          val _categoryName = EditText(this)


          var title = "Change your name or nickname!"
          var message = "Enter your name shorter than 30 characters."
          var yes = "Apply"
          var no = "Cancel"
          var success = "Your name was changed successfully!"
          var toolong = "Your name is too long! Maximum 30 characters!"
          var hint = "Enter your name or nickname:"
          if (Language.getCurrentLanguage().equals("lt")) {
            title = "Pakeisti slapyvardį arba vardą!"
            message = "Įveskite vardą neilgesnį nei 30 simbolių."
            yes = "Patvirtinti"
            no = "Atšaukti"
            success = "Jūsų vardas sėkmingai pakeistas!"
            toolong = "Jūsų vardas per ilgas max 30 simbolių!"
            hint = "Įveskite savo vardą arba slapyvardį:"
          }

          //  _categoryName.hint = hint


          editName.setOnClickListener {


            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.change_name_alert_dialog, null)

            val name = dialogView.findViewById<EditText>(R.id.your_name_edittext)
            val cancel = dialogView.findViewById<Button>(R.id.cancel_edit_name)
            val accept = dialogView.findViewById<Button>(R.id.accept_your_name_edittext)



            name.hint = hint

            dialogBuilder.setView(dialogView)
            val alertDialog: AlertDialog = dialogBuilder.create()

            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setCancelable(false)

            alertDialog.show()

            cancel.setOnClickListener {
              alertDialog.dismiss()
            }

            accept.setOnClickListener {
              userModel.updateName(
                name.text.toString(),
                FirebaseAuth.getInstance().currentUser!!.uid
              )
              Toast.makeText(applicationContext, success, Toast.LENGTH_LONG).show()
              alertDialog.dismiss()

            }


//            val _dialogBuilder = AlertDialog.Builder(this)
//            _dialogBuilder.setMessage(message)
//              .setTitle(title)
//              .setPositiveButton(yes) {
//                  dialog: DialogInterface?, which: Int ->
//                if(_categoryName.text.length <=30){
//                  //profileName.text = _categoryName.text
//                  userModel.updateName(_categoryName.text.toString(), FirebaseAuth.getInstance().currentUser!!.uid)
//
//                  Toast.makeText(applicationContext, success, Toast.LENGTH_LONG).show()
//                }else{
//                  Toast.makeText(applicationContext, toolong, Toast.LENGTH_LONG).show()
//                }
//              }
//              .setView(_categoryName)
//            _dialogBuilder.setCancelable(false)
//
//            _dialogBuilder.show()


          }
        }
      })

    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)





    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar) //set the toolbar

    val host: NavHostFragment = supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
    val navController = host.navController
    appBarConfiguration = AppBarConfiguration(navController.graph) //configure nav controller
    setupNavigation(navController) //setup navigation
    setupActionBar(navController, appBarConfiguration) // setup action bar


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
      setOf(
        R.id.homeFragment,
        R.id.shopFragment,
        R.id.highScoresFragment,
        R.id.privacyPolicyFragment
      ),
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

        userModel.getUsers()
          .observe(this, Observer<List<User>> { profiles ->

            var exists = false

            for (i in profiles.indices) {
              if (profiles[i].uuid.equals(FirebaseAuth.getInstance().currentUser!!.uid)) {
                //Toast.makeText(this, "USERIS EGZISTUOJA JUNGIAMES", Toast.LENGTH_LONG).show()
                exists = true

              } else {
                println("Not exists!")
              }
            }
            if (!exists) {
              userModel.saveUser(
                User(
                  "Puzzle solver",
                  0,
                  FirebaseAuth.getInstance().currentUser!!.uid,
                  0
                )
              )
            }

          })

      } else {
        println(response?.error)
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

