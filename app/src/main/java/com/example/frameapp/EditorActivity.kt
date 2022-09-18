package com.example.frameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.impulsive.zoomimageview.ZoomImageView

class EditorActivity : AppCompatActivity(),OnClick  {
    private lateinit var userImage:ZoomImageView
    private lateinit var button: Button
    private lateinit var frameContainer: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var list:MutableList<FrameList>

    private val drw= arrayOf(R.drawable.frame_1,R.drawable.frame_10,R.drawable.frame_3,R.drawable.frame_4,R.drawable.frame_5,R.drawable.frame_6,R.drawable.frame_7,)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        val path=intent.getStringExtra("path")
        userImage=findViewById(R.id.userImage)
        frameContainer=findViewById(R.id.frameContainer)
        recyclerView=findViewById(R.id.frameRecyclerView)

        Glide.with(this).load(path).into(userImage)
        list=ArrayList()
        initList()
        recyclerView.adapter=FrameAdapter(this,list,this)

    }

    private fun initList() {
        for(j in drw)
        {
            list.add(FrameList(j))
        }
    }

    override fun frameClick(position: Int) {
        Glide.  with(this).load(list[position].drawab).into(frameContainer)
    }
}