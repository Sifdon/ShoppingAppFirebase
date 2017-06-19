package example.haim.shoppingappfirebase.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.shoppingappfirebase.AppManager;
import example.haim.shoppingappfirebase.R;
import example.haim.shoppingappfirebase.models.ShoppingList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListsFragment extends Fragment {

    FirebaseUser mUser;
    FirebaseDatabase mDataBase;


    @BindView(R.id.rvShoppingLists)
    RecyclerView rvShoppingLists;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        unbinder = ButterKnife.bind(this, view);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance();

        rvShoppingLists.setLayoutManager(new LinearLayoutManager(getActivity()));
        DatabaseReference ref = mDataBase.getReference("ShoppingLists").
                child(mUser.getUid());

        rvShoppingLists.setAdapter(new ShoppingAdapter(ref));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabAdd)
    public void onFabClicked() {
//        mDataBase.getReference("UserLists").child(mUser.getUid()).push().setValue("new Title");
        AddShoppingListDialog dialog = new AddShoppingListDialog();
        dialog.show(getFragmentManager(), "AddListDialog");

    }


    public static class ShoppingAdapter extends FirebaseRecyclerAdapter<ShoppingList, ShoppingAdapter.ShoppingListViewHolder>{


        //Constructor (takes only the query)
        public ShoppingAdapter(Query ref) {
            super(ShoppingList.class, R.layout.shopping_list_names, ShoppingListViewHolder.class, ref);
        }

        @Override
        protected void populateViewHolder(final ShoppingListViewHolder viewHolder, ShoppingList list, int position) {
            viewHolder.tvTitle.setText(list.getTitle() + " " + list.getUpdateTime());
            Glide.with(viewHolder.tvTitle.getContext()).
                    load(list.getProfileImage()).
                    into(viewHolder.ivProfile);

        }

        public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            ImageView ivProfile;

            public ShoppingListViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                ivProfile = (ImageView) itemView.findViewById(R.id.ivOwner);
                tvTitle.setTypeface(AppManager.bold);
            }
        }
    }

}
