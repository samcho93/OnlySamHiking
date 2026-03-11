package com.onlysamhiking.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.databinding.ItemHikingRecordBinding
import com.onlysamhiking.app.util.LocationUtils
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val onClick: (HikingRecord) -> Unit,
    private val onDelete: (HikingRecord) -> Unit
) : ListAdapter<HikingRecord, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHikingRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemHikingRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: HikingRecord) {
            binding.tvMountainName.text = record.mountainName.ifEmpty { "산행 기록" }

            val dateFormat = SimpleDateFormat("yyyy.MM.dd (EEE)", Locale.KOREAN)
            binding.tvDate.text = dateFormat.format(Date(record.startTime))

            binding.tvRecordDistance.text = LocationUtils.formatDistance(record.distance)
            binding.tvRecordTime.text = record.durationFormatted
            binding.tvRecordAltitude.text = String.format("%dm", record.maxAltitude.toInt())
            binding.tvRecordCalories.text = String.format("%d", record.calories)

            binding.root.setOnClickListener { onClick(record) }
            binding.btnDelete.setOnClickListener { onDelete(record) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HikingRecord>() {
            override fun areItemsTheSame(a: HikingRecord, b: HikingRecord) = a.id == b.id
            override fun areContentsTheSame(a: HikingRecord, b: HikingRecord) = a == b
        }
    }
}
