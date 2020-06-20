package com.example.madlevel3task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_portal.view.*

class PortalAdapter(
    private val portals: List<Portal>,
    private val onPortalClick: (Portal) -> Unit
) : RecyclerView.Adapter<PortalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_portal, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return portals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portals[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onPortalClick(portals[adapterPosition]) }
        }

        fun bind(portal: Portal) {
            itemView.tvPortalTitle.text = portal.title
            itemView.tvPortalUrl.text = portal.url
        }
    }
}