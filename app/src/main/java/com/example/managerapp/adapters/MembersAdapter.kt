package com.example.managerapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.R
import com.example.managerapp.helpers.ApiHelper
import com.example.managerapp.helpers.Constants
import com.example.managerapp.helpers.PrefsHelper
import com.example.managerapp.models.Member
import com.squareup.picasso.Picasso
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

        val regNo = holder.itemView.findViewById<TextView>(R.id.regnotagvalue)
        val name = holder.itemView.findViewById<TextView>(R.id.namevalue)
        val idNo = holder.itemView.findViewById<TextView>(R.id.idvalue)
        val county = holder.itemView.findViewById<TextView>(R.id.countyvalue)
        val ward = holder.itemView.findViewById<TextView>(R.id.wardvalue)
        val phone = holder.itemView.findViewById<TextView>(R.id.telvalue)
        val next = holder.itemView.findViewById<AppCompatButton>(R.id.btn_delete)
        val image = holder.itemView.findViewById<ImageView>(R.id.bigImage)

        regNo.text = "REG.NO: " + itemList[position].DriverID
        name.text = "NAME : ${itemList[position].FirstName} ${itemList[position].LastName}"
        phone.text = "TEL : +254${itemList[position].MobileNumber}"
        idNo.text = "ID.NO: ${itemList[position].Idnumb}"


        county.text = "COUNTY : " + itemList[position].County
        ward.text = "WARD : " + itemList[position].Constituency

        val id = itemList[position].ID

//        next.setOnClickListener {
//            deleteMembers(id.toString())
//        }


        val api = Constants.BASE_URL + "/get_image"
        val helper = ApiHelper(context)
        val body = JSONObject()
        body.put("id" , id)
        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                // Check if the fragment is attached to a context
                    // Use requireContext() only if the fragment is attached
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()

                    // Safely access the result
                    val image_path = result?.getString("image_path")

                    // Ensure image_path is not null before saving
                    if (!image_path.isNullOrBlank()) {
                        Log.d("HAPA", image_path)

                        val IMAGE_URL = "https://pickerapp.pythonanywhere.com/api/get_photo/"
                        Picasso.get().load(IMAGE_URL + image_path).into(image)

                    } else {
                        // Handle the case where image_path is null or empty
                        Toast.makeText(context, "No image found", Toast.LENGTH_SHORT).show()
                    }
            }


            override fun onFailure(result: String?) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
            }











    })
    }


    private fun deleteMembers(id: String) {
        val helper = ApiHelper(context)
        val api = Constants.BASE_URL + "remove_picker"
        val body = JSONObject()
        body.put("id", id)
        helper.delete(api, body)

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