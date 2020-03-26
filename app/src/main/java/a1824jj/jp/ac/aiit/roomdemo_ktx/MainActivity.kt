package a1824jj.jp.ac.aiit.roomdemo_ktx

import a1824jj.jp.ac.aiit.roomdemo_ktx.databinding.ActivityMainBinding
import a1824jj.jp.ac.aiit.roomdemo_ktx.db.Subscriber
import a1824jj.jp.ac.aiit.roomdemo_ktx.db.SubscriberDatabase
import a1824jj.jp.ac.aiit.roomdemo_ktx.db.SubscriberRepository
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)

        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)

        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        this.initRecyclerView()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MyTag",it.toString())
            binding.subscriberRecyclerView.adapter =
                MyRecyclerViewAdapter(it) { selectedItem:Subscriber->listItemClicked(selectedItem)}
        })
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        this.displaySubscriberList()
    }

    private fun listItemClicked(subscriber: Subscriber){
        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}
