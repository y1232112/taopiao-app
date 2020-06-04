package com.example.taopiao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taopiao.R;
import com.example.taopiao.mvp.activity.SelectSeatsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridSeatAdapter extends BaseAdapter {
    private List<Integer> ids=new ArrayList<>();
    private List<String>  name=new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    public static Integer selected_nums=0;
    private double scaleTag=1;
   private int seatSize=50;
    Bitmap _sale, _sold, _selected;
    Bitmap sale, sold, selected;
    public GridSeatAdapter(SelectSeatsActivity context, List<Map<String,String>> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context=context;
        sale = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_sale);
        sold = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_sold);
        selected = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_seat_selected);
        sale=Bitmap.createScaledBitmap(sale,seatSize,seatSize,false);
        sold=Bitmap.createScaledBitmap(sold,seatSize,seatSize,false);
        selected=Bitmap.createScaledBitmap(selected,seatSize,seatSize,false);

    }
    public void setScale(double scaleTag){
        this.scaleTag=scaleTag;
    }
    @Override
    public int getCount() {

        return mDatas.size();


    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public List<String> getName() {
        return name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridSeatAdapter.ViewHolder holder=null;
        if (mDatas==null){
            return null;
        }else if (mDatas.size()==0){
            return null;
        }
        Map<String,String> map=mDatas.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_grid_seat,parent,false);
            holder = new GridSeatAdapter.ViewHolder();
            holder.ic_seat=(ImageView)convertView.findViewById(R.id.seat_ic);
            holder.seat_des=(TextView)convertView.findViewById(R.id.seat_name) ;
            ViewGroup.LayoutParams params=holder.ic_seat.getLayoutParams();
            params.height=(int)(params.height*scaleTag);
            params.width=(int)(params.width*scaleTag);
            holder.ic_seat.setLayoutParams(params);
            convertView.setTag(holder);
        }else {
            //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (GridSeatAdapter.ViewHolder) convertView.getTag();

        }

        if (map.get("active").equals("0")){
//            holder.ic_seat.setImageBitmap(sold);
        }else {
            holder.seat_des.setText(map.get("row")+"/"+map.get("column"));
            int t=Integer.parseInt(map.get("active2"));
            if (t==0){
                holder.ic_seat.setImageBitmap(sale);
            }else if (t>0){
                holder.ic_seat.setImageBitmap(sold);
                holder.ic_seat.setEnabled(false);
            }

        }


        Log.d("----GridView——————addapter","");
        holder.ic_seat.setOnClickListener(new View.OnClickListener() {
                boolean isSelect=false;

            @Override
            public void onClick(View v) {

                ImageView imageView=v.findViewById(R.id.seat_ic);
                if (!map.get("active").equals(0)){
                    if (map.get("active").equals("1")){
                        if (isSelect){
                            selected_nums--;
                            Log.d("你选择了",selected_nums+"件产品");
                            List<Integer> temp=new ArrayList<>();
                            List<String> temp1=new ArrayList<>();
                            if (ids.size()>0){
                                for (int h=0;h<ids.size();h++){

                                    if (ids.get(h)!=Integer.parseInt(map.get("seat_id"))){
                                        temp.add(ids.get(h));
                                        temp1.add(name.get(h));
                                    };

                                }
                                ids=temp;
                            }
                            imageView.setImageBitmap(sale);
                            isSelect=false;
                        }else {
                            selected_nums++;
                            Log.d("你选择了",selected_nums+"件产品");
                            name.add(map.get("row")+"排"+map.get("column")+"座");
                            ids.add(Integer.parseInt(map.get("seat_id")));
                            imageView.setImageBitmap(selected);
                            isSelect=true;
                        }

                    }else if (map.get("active").equals("2")){
                        Toast.makeText(context,"该座位已被选",Toast.LENGTH_LONG).show();

                    }

                }
            }
        });
        return convertView;
    }
    public class ViewHolder {
        ImageView ic_seat;
        TextView seat_des;

    }
}
