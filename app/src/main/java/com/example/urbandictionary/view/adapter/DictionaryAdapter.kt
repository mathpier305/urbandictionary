package com.example.urbandictionary.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.urbandictionary.R
import com.example.urbandictionary.data.entities.Dfinition
import com.example.urbandictionary.databinding.SingleDefinitionBinding

class DictionaryAdapter(private val context: Context) : RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {
    var definitions: List<Dfinition> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DictionaryViewHolder {
        return DictionaryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.single_definition,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    override fun onBindViewHolder(viewHolder: DictionaryViewHolder, position: Int) {
        val definition = definitions[position]

        viewHolder.binding.thumbUp.text = definition.thumbUp.toString()
        viewHolder.binding.thumbDown.text = definition.thumbDown.toString()
        viewHolder.binding.definition.text = definition.definition

    }

    inner class DictionaryViewHolder(val binding: SingleDefinitionBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun setDefinitionList(definitions: List<Dfinition>) {
        this.definitions = definitions
        notifyDataSetChanged()
    }

}