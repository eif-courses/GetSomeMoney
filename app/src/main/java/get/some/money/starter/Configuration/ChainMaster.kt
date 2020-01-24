package get.some.money.starter.Configuration

import android.app.Application
import android.content.Context

class ChainMaster: Application() {

    companion object {
        private lateinit var instance: ChainMaster
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

}