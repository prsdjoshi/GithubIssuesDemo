package com.sample.githubissuesdemo.view.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.githubissuesdemo.R
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.util.loadUrl
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.description)
    private val username: TextView = view.findViewById(R.id.username)
    private val date: TextView = view.findViewById(R.id.date)
    private val image: ImageView = view.findViewById(R.id.profile_image)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())

    @SuppressLint("NewApi")
    fun bind(item: GithubIssue, onItemClick: (GithubIssue) -> Unit){
//        val calDate = SimpleDateFormat("mm-dd-yyyy", Locale.getDefault()).format(simpleDateFormat.parse(item.updatedAt)!!)
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        title.text = item.title
        description.text =  item.body?.substring(0, Math.min(item?.body!!.length, 200)) + "..."
        username.text = item.user.login
        date.text = convertDateToAnotherFormat(item.updatedAt)
        image.loadUrl(item.user.avatarUrl!!)
        view.setOnClickListener { onItemClick(item) }
    }
    override fun toString(): String {
        return super.toString() + " '" + description.text + "'"
    }

    fun getFormattedDate(originalFormat: SimpleDateFormat, targetFormat: SimpleDateFormat, inputDate: String): String {
        return targetFormat.format(originalFormat.parse(inputDate))
    }

    fun convertDateToAnotherFormat(inputString : String): String{
       return getFormattedDate(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()),
            SimpleDateFormat("mm-dd-yyyy", Locale.getDefault()),
            inputString
        )
    }
}