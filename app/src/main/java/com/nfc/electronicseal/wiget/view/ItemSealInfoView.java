package com.nfc.electronicseal.wiget.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.PictureShowActivity;
import com.nfc.electronicseal.activity.search.SealInfoActivity;
import com.nfc.electronicseal.node.SealItemNode;
import com.nfc.electronicseal.util.DateUtil;

public class ItemSealInfoView extends LinearLayout {
    private Context context;
    private SealItemNode node;
    private TextView titleSealTimeTV;
    private TextView titleSealPerTV;
    private TextView titleSealAddrTV;
    private TextView titleSealDescTV;
    private TextView sealTimeTV;
    private TextView sealPerTV;
    private TextView sealAddrTV;
    private TextView sealDescTV;
    private LinearLayout sealPicsLL;
    private LinearLayout repeatLL;

    public ItemSealInfoView(Context context, SealItemNode node){
        super(context);
        this.context = context;
        this.node = node;
        initViews();

    }

    public void initViews(){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_seal_info, this);
//        statusTV = findViewById(R.id.status_tv);
        titleSealTimeTV = findViewById(R.id.title_seal_time_tv);
        titleSealPerTV = findViewById(R.id.title_seal_person_tv);
        titleSealAddrTV = findViewById(R.id.title_seal_addr_tv);
        titleSealDescTV = findViewById(R.id.title_seal_desc_tv);
        sealTimeTV = findViewById(R.id.seal_time_tv);
        sealPerTV = findViewById(R.id.seal_person_tv);
        sealAddrTV = findViewById(R.id.seal_addr_tv);
        sealDescTV = findViewById(R.id.seal_desc_tv);
        sealPicsLL = findViewById(R.id.seal_pics_ll);
        repeatLL = findViewById(R.id.repeat_ll);

        initDatas(node);
    }

    private void initDatas(SealItemNode node){
        if(node.getSealStatus()==1){
            //已施封
            titleSealTimeTV.setText("施封");
            titleSealPerTV.setText("施封员");
            titleSealAddrTV.setText("施封地点");
            titleSealDescTV.setText("施封描述");
        }else if(node.getSealStatus()==2){
            //已巡检
            titleSealTimeTV.setText("巡检");
            titleSealPerTV.setText("巡检员");
            titleSealAddrTV.setText("巡检地点");
            titleSealDescTV.setText("巡检描述");
        }else if(node.getSealStatus()==3){
            //已完成
            titleSealTimeTV.setText("拆封");
            titleSealPerTV.setText("拆封员");
            titleSealAddrTV.setText("拆封地点");
            titleSealDescTV.setText("拆封描述");
            repeatLL.setVisibility(INVISIBLE);
        }

        sealTimeTV.setText(DateUtil.timeStamp2Date(node.getSealDate()));
        sealPerTV.setText(node.getSealOperName());
        sealAddrTV.setText(node.getSealLoca());
        sealDescTV.setText(node.getDescs());

        String pics = node.getSealPic();
        if(!TextUtils.isEmpty(pics)){
            String[] picItems = pics.split(",");

            for (int i=0; i<picItems.length; i++)
                addPictureItem(sealPicsLL, picItems[i], i, picItems);
        }
    }

    private void addPictureItem(LinearLayout linearLayout, String picUrl, final int position, final String[] picUrlList){
        if(TextUtils.isEmpty(picUrl))
            return;
        View view = View.inflate(context, R.layout.item_picture_s, null);
        ImageView itemIV = view.findViewById(R.id.picture_iv);
        itemIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureShowActivity.class);
                intent.putExtra("Index", position);
                intent.putExtra("Pictures", picUrlList);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(picUrl).into(itemIV);
        linearLayout.addView(view);
    }
}
