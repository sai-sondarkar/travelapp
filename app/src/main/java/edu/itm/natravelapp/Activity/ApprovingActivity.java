package edu.itm.natravelapp.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import dev.jai.genericdialog2.GenericDialog;
import dev.jai.genericdialog2.GenericDialogOnClickListener;
import edu.itm.natravelapp.Adapter.YourTripsAdapter;
import edu.itm.natravelapp.FirebaseExtra.FirebaseInit;
import edu.itm.natravelapp.Model.TripRequestModel;
import edu.itm.natravelapp.Model.UsersModel;
import edu.itm.natravelapp.R;
import io.paperdb.Paper;

public class ApprovingActivity extends AppCompatActivity {

    RecyclerView recyclerViewForTrips;

    YourTripsAdapter yourTripsAdapter;
    List<TripRequestModel> tripRequestModels = new ArrayList<>();
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approving);

        recyclerViewForTrips = (RecyclerView) findViewById(R.id.recycler_view);


        yourTripsAdapter = new YourTripsAdapter(tripRequestModels, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewForTrips.setLayoutManager(mLayoutManager);

        recyclerViewForTrips.setItemAnimator(new DefaultItemAnimator());
        recyclerViewForTrips.setAdapter(yourTripsAdapter);

        yourTripsAdapter.setOnItemClickListener(onItemClickListener);

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

        getDataFromFB();

    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
//            Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
            pos =position;

            askApproval(tripRequestModels.get(pos));





        }
    };



    public void getDataFromFB(){

        UsersModel usersModel = Paper.book().read("user");

        FirebaseInit.getDatabase().getReference().child("requests").orderByChild("reportingManager").equalTo(usersModel.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                TripRequestModel tripRequestModel = dataSnapshot.getValue(TripRequestModel.class);
                tripRequestModel.setKey(dataSnapshot.getKey());
                tripRequestModels.add(tripRequestModel);
                yourTripsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                TripRequestModel tripRequestModel = dataSnapshot.getValue(TripRequestModel.class);
                tripRequestModel.setKey(dataSnapshot.getKey());

                if(tripRequestModels.contains(tripRequestModel)){
                    pos = tripRequestModels.indexOf(tripRequestModel);
                    tripRequestModels.remove(pos);

                    tripRequestModels.add(pos,tripRequestModel);
                    yourTripsAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"NO",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void askApproval(final TripRequestModel requestModel){

        new GenericDialog.Builder(this)
                .setTitle("Approve Trip ?").setTitleAppearance(R.color.colorPrimaryDark, 16)
                .setMessage("Would you like to Approve the Selected Trip ? - \n Rs. " + requestModel.getFare() + " \n Depart - "+requestModel.getFrom() +"\n Arrival - "+requestModel.getTo()+" \nFare - "+ requestModel.getFare() + " \nName - "+requestModel.getName())
                .addNewButton(R.style.ApproveTrip, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseInit.getDatabase().getReference().child("requests").child(requestModel.getKey()).child("isApproved").setValue(true);
                        Toast.makeText(getApplicationContext(),"Trip Approved !",Toast.LENGTH_SHORT).show();
                    }
                })
                .addNewButton(R.style.NotApproveTrip, new GenericDialogOnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })

                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .setCancelable(false)
                .generate();

    }
}
