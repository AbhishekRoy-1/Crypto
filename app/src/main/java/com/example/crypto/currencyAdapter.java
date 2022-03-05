package com.example.crypto;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class currencyAdapter extends RecyclerView.Adapter<currencyAdapter.ViewHolder> {
   private  Context context;
   private ArrayList<CurrencyModel> currencyModelArrayList;
   private  static DecimalFormat df2= new DecimalFormat("#.###");

    public currencyAdapter(Context context, ArrayList<CurrencyModel> currencyModelArrayList) {
        this.context = context;
        this.currencyModelArrayList = currencyModelArrayList;
    }






    @NonNull
    @Override
    public currencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.currency_rv_item,parent,false);
        return new currencyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull currencyAdapter.ViewHolder holder, int position) {
            CurrencyModel currencyModel= currencyModelArrayList.get(position);
            holder.name.setText(currencyModel.getName());
            holder.symbol.setText(currencyModel.getSymbol());
            holder.rate.setText("$" +df2.format(currencyModel.getPrice()));
            holder.rank.setText(Integer.toString(currencyModel.getCmc_rank()));
            holder.time.setText(currencyModel.getLast_updated());
    }

    @Override
    public int getItemCount() {
        return currencyModelArrayList.size();
    }

    public void filterList(ArrayList<CurrencyModel> filteredList) {
        currencyModelArrayList= filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, symbol, rate,rank,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            symbol=itemView.findViewById(R.id.symbol);
            rate=itemView.findViewById(R.id.rate);
            rank=itemView.findViewById(R.id.rank);
            time=itemView.findViewById(R.id.time);
        }
    }
}
