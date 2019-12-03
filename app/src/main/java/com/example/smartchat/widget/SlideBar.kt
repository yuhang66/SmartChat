package com.example.smartchat.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import org.jetbrains.anko.sp
import android.R.attr.bottom
import android.R.attr.top
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R
import android.graphics.Color
import android.view.MotionEvent


class SlideBar(context: Context?, attrs: AttributeSet?=null) : View(context, attrs) {
     var singleHeight = 0f
     var baseLine = 0f
     val paint = Paint()

    var onSelectionChangedListener:OnSectionChangedListener?= null
    companion object{
        private val SELECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z")
    }
      init {
          paint.apply {
             // setAntiAlias(true)
              color = resources.getColor(com.example.smartchat.R.color.gray)
              textSize = sp(12).toFloat()
              textAlign = Paint.Align.CENTER
          }
      }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        singleHeight = h *1.0f/SELECTIONS.size

      //  val fontMetrics = paint.fontMetrics  //拿到绘制的文本高度
//        //计算绘制文本高度
//        val textHeight = fontMetrics.descent - fontMetrics.ascent
//        baseLine = singleHeight/2 +(textHeight/2 - fontMetrics.descent)

    }


    override fun onDraw(canvas: Canvas) {
        //绘制所有的字母
        val x = width *1.0f/2
        var y = singleHeight
        SELECTIONS.forEach {
            canvas.drawText(it,x,y,paint)
            y += singleHeight

        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                setBackgroundResource(com.example.smartchat.R.drawable.bg_slide_bar)
                //找到点击的字母
                val index = getTouchIndex(event)
                val firstLetters = SELECTIONS[index]   //拿到当前字母
                onSelectionChangedListener?.onSectionChanged(firstLetters)

            }
            MotionEvent.ACTION_MOVE->{
                val index = getTouchIndex(event)
                val firstLetters = SELECTIONS[index]   //拿到当前字母
                onSelectionChangedListener?.onSectionChanged(firstLetters)
            }
            MotionEvent.ACTION_UP->{
                setBackgroundColor(Color.TRANSPARENT)
                onSelectionChangedListener?.onSlideFinish()
            }
        }
        return true
    }

    private fun getTouchIndex(event: MotionEvent): Int {  //获取当前下标
       var index:Int = (event.y/singleHeight).toInt()
        //范围控制
        if(index<0){
            index=0
        }else if(index>= SELECTIONS.size){
            index = SELECTIONS.size-1
        }
        return index
    }

    interface OnSectionChangedListener{
        fun onSectionChanged(fiestLetters:String)
        fun onSlideFinish()
    }

}