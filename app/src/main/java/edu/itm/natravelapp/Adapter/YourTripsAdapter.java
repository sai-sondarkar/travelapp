package edu.itm.natravelapp.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.itm.natravelapp.Model.TravelModel;
import edu.itm.natravelapp.Model.TripRequestModel;
import edu.itm.natravelapp.R;

public class YourTripsAdapter extends RecyclerView.Adapter<YourTripsAdapter.MyViewHolder> {


private List<TripRequestModel> dailyReportModelList;
private Context activity;

public int pos;

private View.OnClickListener mOnItemClickListener;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView from, to, fromTime, toTime, carrier, flightNo,via, layover, clas, price, cab, stay, colorText ;
    public CardView cardView;



    public MyViewHolder(View view) {
        super(view);

        from = (TextView) view.findViewById(R.id.from);
        fromTime = (TextView)  view.findViewById(R.id.fromTime);
        to = (TextView) view.findViewById(R.id.to);
        toTime = (TextView) view.findViewById(R.id.toTime);
        carrier = (TextView) view.findViewById(R.id.carrier);
        flightNo = (TextView) view.findViewById(R.id.flightNo);
        via = (TextView) view.findViewById(R.id.via);
        layover = (TextView) view.findViewById(R.id.layover);
        clas = (TextView) view.findViewById(R.id.clas);
        price = (TextView) view.findViewById(R.id.price);
        cab = (TextView) view.findViewById(R.id.cab);
        stay = (TextView) view.findViewById(R.id.stay);
        colorText = (TextView) view.findViewById(R.id.colorText);
        cardView = (CardView) view.findViewById(R.id.card1);



        itemView.setTag(this);
        itemView.setOnClickListener(mOnItemClickListener);


    }
}


    public String getTime(long milli) {

        return DateFormat.format("dd MMM hh:mm aa", milli).toString();
    }

    public YourTripsAdapter(List<TripRequestModel> moviesList, Context activity) {
        this.dailyReportModelList = moviesList;
        this.activity = activity;
    }


    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    @Override
    public YourTripsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_your_trips, parent, false);

        final YourTripsAdapter.MyViewHolder mViewHolder = new YourTripsAdapter.MyViewHolder(itemView);


        return new YourTripsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(YourTripsAdapter.MyViewHolder holder, final int position) {

        TripRequestModel travelModel = dailyReportModelList.get(position);



            holder.via.setVisibility(View.VISIBLE);
            holder.layover.setVisibility(View.VISIBLE);

            holder.price.setText("Rs. "+travelModel.getFare());
            holder.from.setText("From "+travelModel.getFrom());
            holder.to.setText("To "+travelModel.getTo());
            holder.fromTime.setText("Depart Time "+travelModel.getDepartTime());
            holder.toTime.setText("Arrival Time "+travelModel.getArrivalTime());
            if(travelModel.getVia().equals("")){
                holder.via.setVisibility(View.GONE);

            }
            holder.via.setText("Via "+travelModel.getVia());

            if(travelModel.getLayOverTime().equals("")){
                holder.layover.setVisibility(View.GONE);
            }



            holder.layover.setText("Layover Time "+travelModel.getLayOverTime());

            holder.carrier.setText("Carrier "+travelModel.getCarrier());

            holder.flightNo.setText("Flight No. "+travelModel.getFlightNo());
            holder.cab.setText("CAB - "+travelModel.getCab());
            holder.stay.setText("Stay - "+travelModel.getStay());


            if(travelModel.isApproved()){
                holder.clas.setText("APPROVED");
                holder.colorText.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                holder.clas.setTextColor(ContextCompat.getColor(context, R.color.green));
            }else{
                holder.colorText.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                holder.clas.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.clas.setText("NOT APPROVED");
            }




    }

    @Override
    public int getItemCount() {
        return dailyReportModelList.size();
    }

}
