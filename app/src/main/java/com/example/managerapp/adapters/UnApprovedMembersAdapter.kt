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
import com.example.managerapp.models.ApprovedMemberItem
import org.json.JSONArray
import org.json.JSONObject

class UnApprovedMembersAdapter (var context : Context):
    RecyclerView.Adapter<UnApprovedMembersAdapter.ViewHolder>() {
    var regNo : Int = 0
    var itemList : List<ApprovedMemberItem> = listOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_approvals,parent , false
        )
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textMessage = holder.itemView.findViewById<TextView>(R.id.textMessage)
        val tvSender = holder.itemView.findViewById<TextView>(R.id.tvSender)
        val phone = holder.itemView.findViewById<TextView>(R.id.Phone)
        val next = holder.itemView.findViewById<AppCompatButton>(R.id.next3)

        regNo = itemList[position].regNo

        textMessage.text = "First Name : ${itemList[position].firstName}"
        tvSender.text = "Last Name : ${itemList[position].lastName}"
        phone.text = "Phone: +254${itemList[position].phoneNumber}"

        next.setOnClickListener {
            approveMembers()
        }

    }
    fun approveMembers(){
        val helper = ApiHelper(context)
        val api = Constants.BASE_URL + "approve_registration"
        val body = JSONObject()
        body.put("regNo",regNo)
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

    fun setListItems(data: List<ApprovedMemberItem>){
        itemList = data
        notifyDataSetChanged()
    }
}