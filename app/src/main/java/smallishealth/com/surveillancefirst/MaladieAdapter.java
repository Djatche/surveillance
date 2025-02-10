package smallishealth.com.surveillancefirst;

import android.content.Context;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smallishealth.com.surveillancefirst.model.Maladie;

/**
 * Created by hp on 10/04/2018.
 */

public class MaladieAdapter extends RecyclerView.Adapter<MaladieAdapter.Viewholder>  {


    private final ListItemClickListener onClickListener;
    //private Context context;
    private List<Maladie>maladies;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public MaladieAdapter( /*Context context, */ List<Maladie> maladies, ListItemClickListener listener) {
        //this.context = context;
        this.maladies = maladies;
        onClickListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Maladie maladie = maladies.get(position);

        holder.nameFrView.setText(maladie.getName_fr());
        holder.nameEnView.setText(maladie.getName_en());
        holder.imageView.setImageResource(R.drawable.ic_ver_de_guinee);


    }

    @Override
    public int getItemCount() {
        return maladies.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nameFrView;
        TextView nameEnView;
        ImageView imageView;


        public Viewholder(View itemView) {
            super(itemView);

            nameFrView = (TextView) itemView.findViewById(R.id.name_fr);
            nameEnView = (TextView) itemView.findViewById(R.id.name_en);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }

}
