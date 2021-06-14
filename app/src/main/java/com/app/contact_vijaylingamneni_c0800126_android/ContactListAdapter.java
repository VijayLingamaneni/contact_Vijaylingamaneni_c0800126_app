package com.app.contact_vijaylingamneni_c0800126_android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    List<Contact> list;

    public ContactListAdapter(List<Contact> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<Contact> temp) {
        this.list = temp;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvEmail, tvPhone,tvAddress;
        ImageView imgEdit,imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindData(int position){
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            imgEdit =  itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            tvName.setText(list.get(position).getFirstName() + " "+ list.get(position).getLastName());
            tvEmail.setText(list.get(position).getEmail());
            tvAddress.setText(list.get(position).getAddress());
            tvPhone.setText(list.get(position).getPhone());

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDb.getInstance(v.getContext().getApplicationContext()).contactDao().delete(list.get(position));
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),EnterInfoActivity.class);
                    intent.putExtra("CONTACT_DATA", list.get(position));
                    v.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ArrayAdapter arrayAdapter =  new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1, new String[]{"Make a Call", "Send Email"});
                    ListPopupWindow listPopupWindow = new ListPopupWindow(v.getContext());
                    listPopupWindow.setAdapter(arrayAdapter);


                    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                            try {
                                if (index == 0) {
                                    String phone[] = list.get(position).getPhone().split(",");
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone[0], null));
                                    v.getContext().startActivity(callIntent);
                                } else {
                                    String addresses[] = list.get(position).getEmail().split(",");

                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                                    intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                                    if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                        v.getContext().startActivity(intent);
                                    }
                                }
                            }catch(Exception e){}
                            listPopupWindow.dismiss();
                        }
                    });

                    listPopupWindow.setAnchorView(itemView);
                    listPopupWindow.show();
                    return false;
                }
            });

        }
    }
}
