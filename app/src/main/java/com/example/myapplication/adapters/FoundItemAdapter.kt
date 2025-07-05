package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Filter
import android.widget.Filterable
import com.example.myapplication.R
import com.example.myapplication.models.FoundItem

class FoundItemAdapter(
    private val items: List<FoundItem>,
    private val onItemClick: ((FoundItem) -> Unit)? = null // optional click listener
) : RecyclerView.Adapter<FoundItemAdapter.ViewHolder>(), Filterable {

    private var filteredItems: MutableList<FoundItem> = items.toMutableList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewItem: ImageView = itemView.findViewById(R.id.imageViewItem)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewPlace: TextView = itemView.findViewById(R.id.textViewPlace)
        val textViewTimeFound: TextView = itemView.findViewById(R.id.textViewTimeFound)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_found, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]

        holder.imageViewItem.setImageResource(item.imageResId)
        holder.textViewName.text = item.name
        holder.textViewPlace.text = "Place Found: ${item.placeFound}"
        holder.textViewTimeFound.text = "Date/Time: ${item.dateTime}"
        holder.textViewDescription.text = item.description

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.trim()?.lowercase() ?: ""
                val results = if (query.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.name.lowercase().contains(query) ||
                                it.placeFound.lowercase().contains(query) ||
                                it.description.lowercase().contains(query)
                    }
                }
                return FilterResults().apply { values = results }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = (results?.values as? List<FoundItem>)?.toMutableList() ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }

    // Optional: for testing or external use
    fun getCurrentItems(): List<FoundItem> = filteredItems
}
