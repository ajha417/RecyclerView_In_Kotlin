package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(private val newslist:ArrayList<News>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    /*private lateinit var mLister: onItemClickLister

    interface onItemClickLister{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickLister){
        mLister  = listener
    }

    fun deleteItem(i:Int){
        newslist.removeAt(i)
        notifyDataSetChanged()
    }

    fun addToArchieve(i:Int,news:News){
        newslist.add(i,news)
        notifyDataSetChanged()
    }*/



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newslist[position]
        holder.titleImage.setImageResource(currentItem.titleImages)
        holder.heading.text = currentItem.headings
        holder.briefNews.text = currentItem.briefNews

        val isVisible: Boolean = currentItem.visibility
        holder.relativeLayout.visibility = if(isVisible) View.VISIBLE else View.GONE

        holder.heading.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return newslist.size
    }

    /*class MyViewHolder(itemView:View,listener:onItemClickLister):RecyclerView.ViewHolder(itemView){

        val titleImage:ShapeableImageView = itemView.findViewById(R.id.title_image)
        val heading: TextView = itemView.findViewById(R.id.textView)

        val briefNews: TextView = itemView.findViewById(R.id.brief)
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relative_layout)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }*/
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titleImage:ShapeableImageView = itemView.findViewById(R.id.title_image)
        val heading: TextView = itemView.findViewById(R.id.textView)

        val briefNews: TextView = itemView.findViewById(R.id.brief)
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relative_layout)
    }

}