package com.pawka.trellocloneapp.domain.board

import android.os.Parcel
import android.os.Parcelable
import com.pawka.trellocloneapp.domain.task.Task

data class Board(
    val name: String = "",
    val image: String = "",
    val createdBy: String = "",
    val assignedTo: ArrayList<String> = ArrayList(),
    var documentID: String = "",
    var taskList: ArrayList<Task> = ArrayList()
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.createStringArrayList()!!,
        source.readString()!!,
        source.createTypedArrayList(Task.CREATOR)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(image)
        writeString(createdBy)
        writeStringList(assignedTo)
        writeString(documentID)
        writeTypedList(taskList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Board> = object : Parcelable.Creator<Board> {
            override fun createFromParcel(source: Parcel): Board = Board(source)
            override fun newArray(size: Int): Array<Board?> = arrayOfNulls(size)
        }
    }
}
