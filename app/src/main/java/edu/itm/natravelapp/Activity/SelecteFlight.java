package edu.itm.natravelapp.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import dev.jai.genericdialog2.GenericDialog;
import dev.jai.genericdialog2.GenericDialogOnClickListener;
import edu.itm.natravelapp.Adapter.TripsAdapter;
import edu.itm.natravelapp.FirebaseExtra.FirebaseInit;
import edu.itm.natravelapp.Model.TravelModel;
import edu.itm.natravelapp.Model.TripRequestModel;
import edu.itm.natravelapp.Model.UsersModel;
import edu.itm.natravelapp.Network.GetDataService;
import edu.itm.natravelapp.Network.RetrofitClientInstance;
import edu.itm.natravelapp.R;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelecteFlight extends AppCompatActivity {

    String date,to,from;

    RecyclerView recyclerViewForTrips;

    String accommadate,cab, reason;
    int pos;


    TripsAdapter tripsAdapter;
    List<TravelModel> travelModels = new ArrayList<>();
    private String cabtime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecte_flight);


        recyclerViewForTrips = (RecyclerView) findViewById(R.id.recycler_view);


        Bundle bundle = getIntent().getExtras();

        date = bundle.getString("date");
        to = bundle.getString("to");
        from = bundle.getString("from");

        Log.d("JSR", date + to + from);

        putRequest();


        tripsAdapter = new TripsAdapter(travelModels, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewForTrips.setLayoutManager(mLayoutManager);

        recyclerViewForTrips.setItemAnimator(new DefaultItemAnimator());
        recyclerViewForTrips.setAdapter(tripsAdapter);

        tripsAdapter.setOnItemClickListener(onItemClickListener);

        recyclerViewForTrips.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
//                    fab.hide();
//
//                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
//                    fab.show();
//                }

            }
        });

    }


    public void putRequest(){

        //---- Create a data request ------

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("source", from);
        jsonObject.addProperty("distination", to);
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("adult", "1");



        Call<List<TravelModel>> call = service.savePost(jsonObject);


        call.enqueue(new Callback<List<TravelModel>>() {
            @Override
            public void onResponse(Call<List<TravelModel>> call, Response<List<TravelModel>> response) {


                //Log.d("JSR",response.body().toString());
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<TravelModel>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.d("JSR",t.toString());

            }
        });

//       --------- Data Request Ends --------
    }

    private void generateDataList(List<TravelModel> tripList) {

        try{


            for( TravelModel item : tripList){
                travelModels.add(item);
                tripsAdapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Something went wrng, we are on it",Toast.LENGTH_SHORT).show();
        }

    }


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
//            Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
            pos =position;
            if(position == 0){
                askAccommadate();
            }else{
                askReason();
            }


        }
    };


    public void askReason(){

        final EditText taskEditText = new EditText(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);

        taskEditText.setLayoutParams(lp);


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("selected higher priced flight !")
                .setMessage("Please specify the reason for higher priced flight ")
                .setView(taskEditText)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reason = String.valueOf(taskEditText.getText());

                        askAccommadate();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }


    public void askAccommadate(){

        new GenericDialog.Builder(this)
                .setTitle("Accommodation ?").setTitleAppearance(R.color.colorPrimaryDark, 16)
                .setMessage("Would you like to have Accommodation in Hotel, Guest House or No Stay support needed ?")
                .addNewButton(R.style.CustomButton, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accommadate = "Service Apartment";
                        askCab();
                    }
                })
                .addNewButton(R.style.PositiveButton, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accommadate = "Hotel Stay";
                        askCab();
                    }
                })
                .addNewButton(R.style.NegativeButton, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accommadate = "No Stay Required";
                        askCab();
                    }
                })
                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .setCancelable(false)
                .generate();

    }


    public void askCab(){

        new GenericDialog.Builder(this)
                .setTitle("Cab Support ?").setTitleAppearance(R.color.colorPrimaryDark, 16)
                .setMessage("Would you like to have Cab Support Yes or No ?")
                .addNewButton(R.style.CabNOButton, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cab = "No Cab Required";
                        sendRequestToFB();

                    }
                })
                .addNewButton(R.style.CabPrivateButton, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cab = "Individual Cab";
                        askCabTiming();
                    }
                })
                .addNewButton(R.style.CabShareButton, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cab = "Share Cab";
                        askCabTiming();
                    }
                })
                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .setCancelable(false)
                .generate();
    }

    public void askCabTiming(){

        new GenericDialog.Builder(this)
                .setTitle("Cab Support ?").setTitleAppearance(R.color.colorPrimaryDark, 16)
                .setMessage("Would you like to have Cab Support whole day or part time?")
                .addNewButton(R.style.CabPartTIme, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cabtime = "Drop and Pickup only";
                        sendRequestToFB();

                    }
                })
                .addNewButton(R.style.CabFullTime, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cabtime = "Whole Day Cab";
                        sendRequestToFB();
                    }
                })
                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .setCancelable(false)
                .generate();
    }



    public void sendRequestToFB(){

        UsersModel usersModel = Paper.book().read("user");

        if(usersModel.getName().equals("")){
            Intent intent = new Intent(SelecteFlight.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{

            TripRequestModel tripRequestModel = new TripRequestModel();

            tripRequestModel.setName(usersModel.getName());
            tripRequestModel.setEmail(usersModel.getEmail());
            tripRequestModel.setMobileNo(usersModel.getMobile());
            tripRequestModel.setStay(accommadate);
            tripRequestModel.setCab(cab);
            tripRequestModel.setCabTiming(cabtime);
            tripRequestModel.setReason(reason);
            tripRequestModel.setFare(travelModels.get(pos).getFare()+"");
            tripRequestModel.setApproved(false);
            tripRequestModel.setApproval(0);
            tripRequestModel.setReportingManager(usersModel.getReportingManagerEmail());


            if(travelModels.get(pos).getFlightList().size()==1){

                tripRequestModel.setArrivalTime(travelModels.get(pos).getFlightList().get(0).getArrivalTime());
                tripRequestModel.setDepartTime(travelModels.get(pos).getFlightList().get(0).getDepartTime());
                tripRequestModel.setTo(travelModels.get(pos).getFlightList().get(0).getArrivalCity());
                tripRequestModel.setFrom(travelModels.get(pos).getFlightList().get(0).getDepartCity());
                tripRequestModel.setCarrier(travelModels.get(pos).getFlightList().get(0).getCarrierName());
                tripRequestModel.setFlightNo(travelModels.get(pos).getFlightList().get(0).getFlightNo());
                tripRequestModel.setVia("");
                tripRequestModel.setLayOverTime("");

                FirebaseInit.getDatabase().getReference().child("requests").push().setValue(tripRequestModel);
                Toast.makeText(getApplicationContext(),"Travel Request Sent",Toast.LENGTH_SHORT).show();
                finish();

            }else if(travelModels.get(pos).getFlightList().size()==2){
                tripRequestModel.setDepartTime(travelModels.get(pos).getFlightList().get(0).getDepartTime());
                tripRequestModel.setArrivalTime(travelModels.get(pos).getFlightList().get(1).getArrivalTime());
                tripRequestModel.setTo(travelModels.get(pos).getFlightList().get(1).getArrivalCity());
                tripRequestModel.setFrom(travelModels.get(pos).getFlightList().get(0).getDepartCity());
                tripRequestModel.setCarrier(travelModels.get(pos).getFlightList().get(0).getCarrierName());
                tripRequestModel.setFlightNo(travelModels.get(pos).getFlightList().get(0).getFlightNo() + " & " + travelModels.get(pos).getFlightList().get(1).getFlightNo());
                tripRequestModel.setVia(travelModels.get(pos).getFlightList().get(0).getArrivalCity());
                tripRequestModel.setLayOverTime(travelModels.get(pos).getFlightList().get(0).getFlightLayover()+"");


                FirebaseInit.getDatabase().getReference().child("requests").push().setValue(tripRequestModel);
                Toast.makeText(getApplicationContext(),"Travel Request Sent",Toast.LENGTH_SHORT).show();
                finish();

            }else{
                Toast.makeText(getApplicationContext(),"Travel Request NOT Sent, Try Again ",Toast.LENGTH_SHORT).show();

            }


        }




    }


}
