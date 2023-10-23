package com.example.managerapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.R
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.models.Member
import org.json.JSONArray
import org.json.JSONObject

class MembersAdapter (var context : Context):
    RecyclerView.Adapter<MembersAdapter.ViewHolder>() {

    var itemList : List<Member> = listOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_member,parent , false
        )
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var regNo = holder.itemView.findViewById<TextView>(R.id.regnotagvalue)
        val name = holder.itemView.findViewById<TextView>(R.id.namevalue)
        val idNo = holder.itemView.findViewById<TextView>(R.id.idvalue)
        val county = holder.itemView.findViewById<TextView>(R.id.countyvalue)
        val ward = holder.itemView.findViewById<TextView>(R.id.wardvalue)
        val phone = holder.itemView.findViewById<TextView>(R.id.telvalue)
        val next = holder.itemView.findViewById<AppCompatButton>(R.id.btn_download)

        regNo.text = "REG.NO: " + itemList[position].DriverID
        name.text = "NAME : ${itemList[position].FirstName} ${itemList[position].LastName}"
        phone.text = "TEL : +254${itemList[position].MobileNumber}"
        idNo.text = "ID.NO: ${itemList[position].Idnumb}"


        county.text = "COUNTY : " + itemList[position].County
        ward.text = "WARD : " + itemList[position].Constituency

        var id = itemList[position].ID






        next.setOnClickListener {
            deleteMembers(id.toString())
        }

    }
    fun deleteMembers(id: String){
        val helper = ApiHelper(context)
        val api = Constants.BASE_URL + "remove_picker"
        val body = JSONObject()
        body.put("id",id)
        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }

            override fun onFailure(result: String?) {

            }
        })

    }

    fun filterList(filterList: List<Member>){
        itemList = filterList
        notifyDataSetChanged()
    }

    fun setListItems(data: List<Member>){
        itemList = data
        notifyDataSetChanged()
    }
}