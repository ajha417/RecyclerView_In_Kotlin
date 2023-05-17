package com.example.recyclerviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newsArrayList:ArrayList<News>
    private lateinit var tempArrayList:ArrayList<News>
    private lateinit var imageId:Array<Int>
    private lateinit var headings:Array<String>

    private lateinit var news:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageId = arrayOf(
            R.drawable.back,
            R.drawable.backgroundimg,
            R.drawable.backi,
            R.drawable.facebook,
            R.drawable.email,
            R.drawable.logo
        )

        news  = arrayOf(
            getString(R.string.a),
            getString(R.string.b),
            getString(R.string.c),
            getString(R.string.d),
            getString(R.string.e),
            getString(R.string.f),
        )

        headings = arrayOf(
            "This is heading of first ",
            "This is heading of second ",
            "This is heading of third ",
            "This is heading of fourth ",
            "This is heading of fifth ",
            "This is heading of sixth ",
        )

        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newsArrayList = ArrayList<News>()
        tempArrayList = ArrayList<News>()
        
        getUserData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    newsArrayList.forEach {
                        if(it.headings.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }
                else{
                    tempArrayList.clear()
                    tempArrayList.addAll(newsArrayList)
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getUserData(){
        for(i in imageId.indices){
            val news = News(imageId[i],headings[i])
            newsArrayList.add(news)
        }
        tempArrayList.addAll(newsArrayList)

//        it sorts the recyclerview in ascending order
        tempArrayList.sortBy {
            it.headings
        }
        var adapter = MyAdapter(tempArrayList)


        val swipegesture = object : SwipeGestures(this){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from_pos = viewHolder.adapterPosition
                val to_pos = target.adapterPosition

                Collections.swap(newsArrayList,from_pos,to_pos)
                adapter.notifyItemMoved(from_pos,to_pos)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.LEFT->{
                        adapter.deleteItem(viewHolder.adapterPosition)
                    }
                    ItemTouchHelper.RIGHT ->{
                        val archieveItem = newsArrayList[viewHolder.adapterPosition]
                        adapter.deleteItem(viewHolder.adapterPosition)
                        adapter.addToArchieve(newsArrayList.size,archieveItem)
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipegesture)
        touchHelper.attachToRecyclerView(newRecyclerView)
        newRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter.onItemClickLister{
            override fun onItemClick(position: Int) {
//                 Toast.makeText(this@MainActivity,"You clicked on item no. $position",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity,NewsActivity::class.java)
                intent.putExtra("heading",newsArrayList[position].headings)
                intent.putExtra("imageId",newsArrayList[position].titleImages)
                intent.putExtra("news",news[position])
                startActivity(intent)
            }

        })
    }
}