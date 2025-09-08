package com.example.annexe1

import java.util.ArrayList

object SingletonMemos {

    var liste:ArrayList<Memo> = ArrayList()

    fun ajouterMemo (memo: Memo) {

        liste.add(memo)
    }
}