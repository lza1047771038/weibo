package com.open.core_base.adapter

import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

abstract class CommonPagingAdapter<T : Any, VH : CommonViewHolder<*, T>>(diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VH>(diffUtil){

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

abstract class CommonAdapter<T> : RecyclerView.Adapter<CommonViewHolder<*, Any>>() {

    private val displayList: MutableList<T> = ArrayList()

    override fun onBindViewHolder(holder: CommonViewHolder<*, Any>, position: Int) {
        val item = displayList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = displayList.size

}

abstract class CommonViewHolder<B : ViewDataBinding, T : Any>(protected val binding: B) :
    RecyclerView.ViewHolder(binding.root), ViewHolderBinder<T>

interface ViewHolderBinder<T> {
    fun bind(item: T?) {
        throw IllegalStateException("You must override bind() method to make viewHolder instance make sense")
    }
}

interface ViewHolderInflater<T> {
    fun newInstance(): T
}