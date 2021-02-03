package com.th3pl4gu3.mes.ui.main.all_services

import com.th3pl4gu3.mes.api.Service
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ServiceAdapter : ListAdapter<Service, ServiceViewHolder>(
    diffCallback
) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Service>() {
            override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem.identifier == newItem.identifier
            }

            override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder.from(
            parent
        )
    }
}