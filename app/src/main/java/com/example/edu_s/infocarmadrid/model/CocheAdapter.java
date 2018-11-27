package com.example.edu_s.infocarmadrid.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu_s.infocarmadrid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgonzalez on 15/11/2018.
 */

public class CocheAdapter extends RecyclerView.Adapter<CocheAdapter.ViewHolder>{
        private List<CocheItem> mItems = new ArrayList<CocheItem>();
        Context mContext;

        public interface OnItemClickListener {
            void onItemClick(CocheItem item);     //Type of the element to be returned
        }

        private final OnItemClickListener listener;

        // Provide a suitable constructor (depends on the kind of dataset)
        public CocheAdapter(Context context, OnItemClickListener listener) {
            mContext = context;
            this.listener = listener;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CocheAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // - Inflate the View for every element
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.coche_item, parent, false);

            return new ViewHolder(mContext,v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(mItems.get(position),listener);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void add(CocheItem item) {

            mItems.add(item);
            notifyDataSetChanged();

        }

        public void clear(){

            mItems.clear();
            notifyDataSetChanged();

        }

        public void load(List<CocheItem> items){

            mItems.clear();
            mItems = items;
            notifyDataSetChanged();

        }

        public void delete(CocheItem item){
            mItems.remove( item );
            notifyDataSetChanged();
        }


        public Object getItem(int pos) { return mItems.get(pos); }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private Context mContext;

            private TextView name;
            private TextView matricula;
            private TextView distintivo;

            public ViewHolder(Context context, View itemView) {
                super(itemView);

                mContext = context;

                // - Get the references to every widget of the Item View
                name = (TextView) itemView.findViewById(R.id.nameView);
                matricula = (TextView) itemView.findViewById(R.id.matriculaView);
                distintivo = (TextView) itemView.findViewById(R.id.distintivoView);
            }

            public void bind(final CocheItem cocheItem, final OnItemClickListener listener) {

                // - Display Title in TextView
                name.setText(cocheItem.getName());

                // - Display Matricula in a TextView
                matricula.setText(cocheItem.getMatricula());

                //  - Display Distintivo.
                distintivo.setText(cocheItem.getDistintivo().toString());


                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(cocheItem);
                    }
                });
            }
        }
}
