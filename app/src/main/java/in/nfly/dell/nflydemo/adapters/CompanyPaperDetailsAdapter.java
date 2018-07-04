package in.nfly.dell.nflydemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.nfly.dell.nflydemo.R;

public class CompanyPaperDetailsAdapter extends RecyclerView.Adapter<CompanyPaperDetailsAdapter.CompanyPaperDetailsHolder>{

    private Context context;
    private ArrayList<String> nameDataSet,textDataSet,idDataSet;

    public CompanyPaperDetailsAdapter(Context context, ArrayList<String> nameDataSet, ArrayList<String> textDataSet, ArrayList<String> idDataSet) {
        this.context = context;
        this.nameDataSet = nameDataSet;
        this.textDataSet = textDataSet;
        this.idDataSet = idDataSet;
    }

    @NonNull
    @Override
    public CompanyPaperDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_company_papers_details,parent,false);
        CompanyPaperDetailsHolder holder=new CompanyPaperDetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyPaperDetailsHolder holder, final int position) {
        holder.CompanyPaperDetailsCardText.setText(textDataSet.get(position));
        holder.CompanyPaperDetailsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, idDataSet.get(position)+"\n"+nameDataSet.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return idDataSet.size();
    }

    public class CompanyPaperDetailsHolder extends RecyclerView.ViewHolder{

        public CardView CompanyPaperDetailsCardView;
        public TextView CompanyPaperDetailsCardText;
        public CompanyPaperDetailsHolder(View itemView) {
            super(itemView);
            CompanyPaperDetailsCardView=itemView.findViewById(R.id.companyPaperDetailsCardView);
            CompanyPaperDetailsCardText=itemView.findViewById(R.id.companyPaperDetailsText);
        }
    }
}
