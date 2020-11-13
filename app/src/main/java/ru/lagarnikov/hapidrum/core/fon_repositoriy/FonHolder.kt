package ru.lagarnikov.hapidrum.core.fon_repositoriy

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.lagarnikov.hapidrum.R

class FonHolder {
    val listFon = setFonList()
    private var position = -1

    fun setFonFor(view: ImageView){
        Glide.with(view).load(getDrawableFon()).into(view)
    }

    private fun setFonList(): ArrayList<Int>{
        val list = ArrayList<Int>()
        list.add(R.drawable.fon_2)
        list.add(R.drawable.fon_3)
        list.add(R.drawable.fon_4)
        list.add(R.drawable.fon_5)
        list.add(R.drawable.sand_a)
        list.add(R.drawable.wood_pil)
        list.add(R.drawable.gray_sand)
        return  list

    }

    private fun getDrawableFon(): Int{
        position++
        if (position >= listFon.size){
            position = 0
        }
        return listFon.get(position)
    }

}