package com.example.peptalkgenerator.fake

import androidx.test.espresso.core.internal.deps.guava.collect.Iterables.getFirst

object FakeDataSource {
    const val greeting = "greeting"
    const val first = "first"
    const val second = "second"
    const val ending = "ending"

    val pepTalk:String = ("$greeting $first $second $ending")
}