package com.volodymyr.shykun.androidfinaltest.entity


data class SearchUrl(val url: String, val status: Status)

sealed class Status {
    object Loading : Status()
    object Error : Status()
    object Stopped : Status()
    sealed class Complete : Status() {
        object Found : Complete()
        object NotFound : Complete()
    }

    override fun equals(other: Any?): Boolean {

        if (this === other)
            return true
        if (other !is Status)
            return false

        if (this is Loading && other is Loading)
            return true
        if (this is Error && other is Error)
            return true

        if (this is Complete.Found && other is Complete.Found)
            return true

        if (this is Complete.NotFound && other is Complete.NotFound)
            return true

        return false
    }
}