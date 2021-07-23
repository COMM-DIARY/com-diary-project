package com.example.cd.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cd.R;
import com.example.cd.contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {



    private Context context;
    private List<Contact> contactList;

    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.contactName.setText(contact.getName());
        holder.mobileNumber.setText(contact.getMobile_no());
        holder.city.setText(contact.getCity());
        holder.bgroup.setText(contact.getBlood());
        holder.address.setText(contact.getAdd());
    }

    // How many items?
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView contactName;
        public TextView mobileNumber;
        public TextView bgroup;
        public TextView address;
        public TextView city;
        public ImageView call_btn;
        public ImageView sms_btn;
        public ImageView whatsapp_btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener((View.OnClickListener) this);
            contactName = itemView.findViewById(R.id.name);
            mobileNumber = itemView.findViewById(R.id.mobile_number);
            bgroup = itemView.findViewById(R.id.b_group);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            call_btn = itemView.findViewById(R.id.image_call);
            sms_btn = itemView.findViewById(R.id.image_sms);
            whatsapp_btn = itemView.findViewById(R.id.image_whatsapp);

            call_btn.setOnClickListener(this::onClick);
            sms_btn.setOnClickListener(this::onClick);
            whatsapp_btn.setOnClickListener(this::onClick);

        }

        @Override
        public void onClick(View v) {
            int position = this.getAbsoluteAdapterPosition();
            call_btn.setOnClickListener(new View.OnClickListener() {
                Contact contact = contactList.get(position);
                String phone =contact.getMobile_no();
                public void onClick(View v) {
                    callPhoneNumber(phone);
                }
            });
            sms_btn.setOnClickListener(new View.OnClickListener() {
                Contact contact = contactList.get(position);
                String phone =contact.getMobile_no();
                public void onClick(View v) {
                    smsNumber(phone);
                }
            });
            whatsapp_btn.setOnClickListener(new View.OnClickListener() {
                Contact contact = contactList.get(position);
                String phone =contact.getMobile_no();
                public void onClick(View v) {
                    boolean installed = isAppInstalled("com.whatsapp");
                    if(installed)
                    {
                        Intent intent =new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+91"+phone+"&text=Hello"));
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Whatsapp is not installed!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
    }

    public void callPhoneNumber(String phone) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity)context, new String[]{
                            Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91" + phone ));
                context.startActivity(callIntent);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91" + phone ));
                context.startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void smsNumber(String phone){
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity)context, new String[]{
                            Manifest.permission.SEND_SMS}, 101);
                    return;
                }
                Intent smsIntent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms","+91"+phone,null));

                smsIntent.putExtra("sms_body","Hello");
                //smsIntent.setType("vnd.android-dir/mms-sms");
                context.startActivity(smsIntent);
            } else {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms","+91"+phone,null));
                smsIntent.putExtra("sms body","Hello");
                //smsIntent.setType("vnd.android-dir/mms-sms");
                context.startActivity(smsIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private boolean isAppInstalled(String s)
    {
        PackageManager packageManager= context.getPackageManager();
        boolean isInstalled;
        try{
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled=false;
            e.printStackTrace();
        }
        return isInstalled;
    }
    public void setFilter(ArrayList<Contact> newList){
        contactList=new ArrayList<>();
        contactList.addAll(newList);
        notifyDataSetChanged();
    }
}



