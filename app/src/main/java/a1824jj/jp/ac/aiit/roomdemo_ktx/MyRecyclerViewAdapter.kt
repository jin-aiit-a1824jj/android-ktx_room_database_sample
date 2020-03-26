package a1824jj.jp.ac.aiit.roomdemo_ktx

import a1824jj.jp.ac.aiit.roomdemo_ktx.databinding.ListItemBinding
import a1824jj.jp.ac.aiit.roomdemo_ktx.db.Subscriber
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(private val subscribersList: List<Subscriber>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(subscriber: Subscriber){
            binding.nameTextView.text = subscriber.name
            binding.emailTextView.text = subscriber.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = subscribersList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position])
    }


}