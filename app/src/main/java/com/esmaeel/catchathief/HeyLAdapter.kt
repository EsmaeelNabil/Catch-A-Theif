package com.esmaeel.catchathief


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esmaeel.catchathief.Utils.ViewEventsContract
import com.esmaeel.catchathief.databinding.ViewOneBinding
import javax.inject.Inject

class HeyLAdapter @Inject constructor() : ListAdapter<String, HeyLAdapter.HeyLItem>(itemsDiffUtil) {

    val clickEvent: MutableLiveData<ViewEventsContract<String>> = MutableLiveData()


    private object itemsDiffUtil :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ) = /*oldItem == newItem*/ false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeyLItem {
        return HeyLItem(
            ViewOneBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeyLItem, position: Int) {
        holder.bindViews(getItem(position))
    }


    inner class HeyLItem(val binder: ViewOneBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bindViews(item: String) {
            binder.text.text = item
        }
    }


}