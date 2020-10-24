package ru.lagarnikov.hapidrum.repositoriy

import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.soundlayer.Sounds

class LoopRepositoriy {

    fun getLoopList(): ArrayList<Int>{
        val rez = ArrayList<Int>()
        rez.add(getSoundId(Sounds.A))
        rez.add(getSoundId(Sounds.B))
        rez.add(getSoundId(Sounds.C))
        rez.add(getSoundId(Sounds.D))
        rez.add(getSoundId(Sounds.E))
        rez.add(getSoundId(Sounds.F))
        rez.add(getSoundId(Sounds.G))
        rez.add(getSoundId(Sounds.H))
        rez.add(getSoundId(Sounds.ZERO))
        return  rez
    }

     fun getSoundNumberInList(soundName: Sounds): Int{
        return when(soundName){
            Sounds.A -> 0
            Sounds.B -> 1
            Sounds.C -> 2
            Sounds.D -> 3
            Sounds.E -> 4
            Sounds.F -> 5
            Sounds.G -> 6
            Sounds.H -> 7
            Sounds.ZERO -> 8
        }
    }

    fun getSoundNameFromViewId (id: Int): Sounds{
        return when(id){
            R.id.button_a -> Sounds.A
            R.id.button_b -> Sounds.B
            R.id.button_c -> Sounds.C
            R.id.button_d -> Sounds.D
            R.id.button_e -> Sounds.E
            R.id.button_f -> Sounds.F
            R.id.button_g -> Sounds.G
            R.id.button_h -> Sounds.H
            else -> Sounds.ZERO
        }
    }

    private fun getSoundId(soundName: Sounds): Int{
        return when(soundName){
            Sounds.A -> R.raw.a
            Sounds.B -> R.raw.b
            Sounds.C -> R.raw.c
            Sounds.D -> R.raw.d
            Sounds.E -> R.raw.e
            Sounds.F -> R.raw.f
            Sounds.G -> R.raw.g
            Sounds.H -> R.raw.h
            Sounds.ZERO -> R.raw.zero
        }
    }

    fun getAnimViewId(soundName: Sounds): Int{
        return when(soundName){
            Sounds.A -> R.id.hapi_drum_a
            Sounds.B -> R.id.hapi_drum_b
            Sounds.C -> R.id.hapi_drum_c
            Sounds.D -> R.id.hapi_drum_d
            Sounds.E -> R.id.hapi_drum_e
            Sounds.F -> R.id.hapi_drum_f
            Sounds.G -> R.id.hapi_drum_g
            Sounds.H -> R.id.hapi_drum_h
            Sounds.ZERO -> R.id.hapi_drum_zero

        }
    }
}