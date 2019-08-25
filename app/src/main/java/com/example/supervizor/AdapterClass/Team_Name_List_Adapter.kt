package com.example.supervizor.AdapterClass

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supervizor.R
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import com.example.supervizor.Fragment.Employee.Team_Member_List_F
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass
import kotlinx.android.synthetic.main.item_team_name_list.view.*


class Team_Name_List_Adapter(var team_name_list: MutableList<String>) : RecyclerView.Adapter<Team_Name_List_Adapter.ViewHolderClass>() {


    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team_name_list, parent, false);
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {

        return team_name_list.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        return holder.setData(team_name_list[position])
    }


    class ViewHolderClass(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun setData(team_name: String) {


            itemView.team_name_TV_Item_ID.text = team_name

            itemView.setOnClickListener {

                var bundle=Bundle()
                bundle.putString("team_name",team_name)

                load_Team_Member_List_Fragment(bundle)


            }

        }


        private fun load_Team_Member_List_Fragment(bundle: Bundle) {


            var fragment: Fragment?
            fragment = Team_Member_List_F()

            if (fragment != null) {

                fragment.arguments=bundle
                val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment!!)
                fragmentTransaction.commit()
            }
        }


    }

}
