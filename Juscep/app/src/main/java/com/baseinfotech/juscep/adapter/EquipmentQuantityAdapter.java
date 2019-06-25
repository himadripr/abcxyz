package com.baseinfotech.juscep.adapter;


import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.baseinfotech.juscep.R;
import com.baseinfotech.juscep.activity.BookingActivity;
import com.baseinfotech.juscep.model.Equipment;
import com.baseinfotech.juscep.model.EquipmentsRowData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class EquipmentQuantityAdapter extends RecyclerView
        .Adapter<EquipmentQuantityAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<EquipmentsRowData> equipmentsRowDataList;
    private static String[] equipmentNames;
    private List<Equipment> equipmentList;
    int index;
    private static WeakReference<BookingActivity> activity;
    //private static MyClickListener myClickListener;

    public List<EquipmentsRowData> getEquipmentsRowDataList(){
        return equipmentsRowDataList;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        Spinner equipmentSpinner;
        EditText daysText;
        EditText quantityText;
        public QuantityTextChangeListener quantityTextChangeListener;
        public DaysTextChangeListener daysTextChangeListener;
        public EquipmentItemSelectionListener equipmentItemSelectionListener;

        public DataObjectHolder(View itemView, QuantityTextChangeListener quantityTextChangeListener,
                                DaysTextChangeListener daysTextChangeListener, EquipmentItemSelectionListener equipmentItemSelectionListener) {
            super(itemView);
            equipmentSpinner = (Spinner) itemView.findViewById(R.id.equipment_spinner);
            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter aa = new ArrayAdapter(activity.get() ,android.R.layout.simple_spinner_item, equipmentNames);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            equipmentSpinner.setAdapter(aa);
            daysText = (EditText) itemView.findViewById(R.id.days_text);
            quantityText = (EditText) itemView.findViewById(R.id.quantity_text);
            this.quantityTextChangeListener = quantityTextChangeListener;
            this.daysTextChangeListener = daysTextChangeListener;
            this.quantityText.addTextChangedListener(quantityTextChangeListener);
            this.daysText.addTextChangedListener(daysTextChangeListener);
            this.equipmentItemSelectionListener = equipmentItemSelectionListener;
            equipmentSpinner.setOnItemSelectedListener(equipmentItemSelectionListener);

            // checkBox.setClickable(false);
            // checkBox.setFocusable(false);
//            Log.i(LOG_TAG, "Adding Listener");
//            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public EquipmentQuantityAdapter( List<Equipment> equipmentList, BookingActivity bookingActivity) {
        activity = new WeakReference<BookingActivity>(bookingActivity);
        equipmentsRowDataList = new ArrayList<>();
        this.equipmentList = equipmentList;
        this.equipmentList.add(0, new Equipment());
        equipmentNames = new String[equipmentList.size()];
        for (int i=0; i<equipmentList.size(); i++){
            equipmentNames[i] = equipmentList.get(i).getName();
        }
        equipmentsRowDataList.add(new EquipmentsRowData());
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, new QuantityTextChangeListener(), new DaysTextChangeListener(), new EquipmentItemSelectionListener());
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.quantityTextChangeListener.updatePosition(position);
        holder.daysTextChangeListener.updatePosition(position);
        holder.equipmentItemSelectionListener.updateHolderPosition(position);
        EquipmentsRowData equipmentsRowData = equipmentsRowDataList.get(position);

        holder.daysText.setText(equipmentsRowData.getDays());
        holder.quantityText.setText(equipmentsRowData.getQuantity());
        if (equipmentsRowData.getEquipment()!=null){
            int i=0;
            for (;i<equipmentNames.length; i++){
                if (equipmentsRowData.getEquipment().getName().equals(equipmentNames[i])){
                    break;
                }
            }
            if (i<equipmentNames.length){
                holder.equipmentSpinner.setSelection(i, true);
            } else {
                holder.equipmentSpinner.setSelection(0, true);
            }
        } else {
            holder.equipmentSpinner.setSelection(0, true);
        }



    }

    public void addItem(EquipmentsRowData dataObj, int index) {
        equipmentsRowDataList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        equipmentsRowDataList.remove(index);
        notifyItemRemoved(index);
    }

    public void deleteLastItem(){
        if (equipmentsRowDataList.size()>1){
            index = equipmentsRowDataList.size()-1;
            equipmentsRowDataList.remove(equipmentsRowDataList.size()-1);

            MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1000l, 500l, this, false);
            //myCountDownTimer.start();
            notifyItemRemoved(index);
        }
    }

    public void addItemToLast(){
        equipmentsRowDataList.add(new EquipmentsRowData());
        index = equipmentsRowDataList.size()-1;
        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1000l, 500l, this, true);
        //myCountDownTimer.start();
        notifyItemInserted(index);

    }

    @Override
    public int getItemCount() {
        return equipmentsRowDataList.size();
    }

    class MyCountDownTimer extends CountDownTimer {
        EquipmentQuantityAdapter equipmentQuantityAdapter;
        boolean isInsert;

        MyCountDownTimer(long startTime, long duration, EquipmentQuantityAdapter equipmentQuantityAdapter, boolean isInsert){
            super(startTime, duration);
            this.equipmentQuantityAdapter = equipmentQuantityAdapter;
            this.isInsert = isInsert;
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (isInsert){
                equipmentQuantityAdapter.notifyItemInserted(index);
            } else {
                equipmentQuantityAdapter.notifyItemRemoved(index);
            }


        }
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position QuantityTextChangeListener, reference to which is kept by ViewHolder
    private class QuantityTextChangeListener implements TextWatcher {
        private int position;


        public void updatePosition(int position) {
            this.position = position;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            EquipmentsRowData equipmentsRowData = equipmentsRowDataList.get(position);

            equipmentsRowData.setQuantity(charSequence.toString());

            equipmentsRowDataList.set(position, equipmentsRowData);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class DaysTextChangeListener implements TextWatcher {
        private int position;


        public void updatePosition(int position) {
            this.position = position;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            EquipmentsRowData equipmentsRowData = equipmentsRowDataList.get(position);

            equipmentsRowData.setDays(charSequence.toString());

            equipmentsRowDataList.set(position, equipmentsRowData);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class EquipmentItemSelectionListener implements AdapterView.OnItemSelectedListener{

        private int holderPosition;


        public void updateHolderPosition(int position) {
            this.holderPosition = position;

        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            EquipmentsRowData equipmentsRowData = equipmentsRowDataList.get(holderPosition);
            equipmentsRowData.setEquipment(equipmentList.get(position));
            equipmentsRowDataList.set(holderPosition, equipmentsRowData);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }



}

