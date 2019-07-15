package edu.itm.natravelapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.itm.natravelapp.Model.TravelModel;
import edu.itm.natravelapp.R;



public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {


    private List<TravelModel> dailyReportModelList;
    private Context activity;

    public int pos;

    private View.OnClickListener mOnItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView from, to, fromTime, toTime, carrier, flightNo,via, layover, clas, price ;
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
            clas = (TextView) view.findViewById(R.id.layover);
            price = (TextView) view.findViewById(R.id.price);


            cardView = (CardView) view.findViewById(R.id.card);


            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);


        }
    }


    public String getTime(long milli) {

        return DateFormat.format("dd MMM hh:mm aa", milli).toString();
    }

    public TripsAdapter(List<TravelModel> moviesList, Context activity) {
        this.dailyReportModelList = moviesList;
        this.activity = activity;
    }


    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        final TripsAdapter.MyViewHolder mViewHolder = new TripsAdapter.MyViewHolder(itemView);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TravelModel travelModel = dailyReportModelList.get(position);


        if(travelModel.getFlightList().size()==2){
            holder.via.setVisibility(View.VISIBLE);
            holder.layover.setVisibility(View.VISIBLE);

            holder.price.setText("Rs. "+travelModel.getFare());
            holder.from.setText("From "+travelModel.getFlightList().get(0).getDepartCity());
            holder.to.setText("To "+travelModel.getFlightList().get(1).getArrivalCity());
            holder.fromTime.setText("Depart Time "+travelModel.getFlightList().get(0).getDepartTime());
            holder.toTime.setText("Arrival Time "+travelModel.getFlightList().get(1).getArrivalTime());
            holder.via.setText("Via "+travelModel.getFlightList().get(0).getArrivalCity());
            holder.layover.setText("Layover Time "+travelModel.getFlightList().get(0).getFlightLayover());
            holder.carrier.setText("Carrier "+travelModel.getFlightList().get(0).getCarrierName());
            holder.flightNo.setText("Flight No. "+travelModel.getFlightList().get(0).getFlightNo() + " & " + travelModel.getFlightList().get(1).getFlightNo());

        }else if(travelModel.getFlightList().size()==1){

            holder.via.setVisibility(View.GONE);
            holder.layover.setVisibility(View.GONE);
            holder.price.setText("Rs. "+travelModel.getFare());
            holder.from.setText("From "+travelModel.getFlightList().get(0).getDepartCity());
            holder.to.setText("To "+travelModel.getFlightList().get(0).getArrivalCity());
            holder.fromTime.setText("Depart Time "+travelModel.getFlightList().get(0).getDepartTime());
            holder.toTime.setText("Arrival Time "+travelModel.getFlightList().get(0).getArrivalTime());
            // holder.via.setText("Via "+travelModel.getFlightList().get(0).getArrivalCity());
            //holder.layover.setText("Layover Time "+travelModel.getFlightList().get(0).getFlightLayover());
            holder.carrier.setText("Carrier "+travelModel.getFlightList().get(0).getCarrierName());
            holder.flightNo.setText("Flight No. "+travelModel.getFlightList().get(0).getFlightNo());

        }


    }

    @Override
    public int getItemCount() {
        return dailyReportModelList.size();
    }

}
