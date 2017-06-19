package example.haim.shoppingappfirebase.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.shoppingappfirebase.R;
import example.haim.shoppingappfirebase.models.ShoppingList;

/**
 * A Dialog fragment for adding Shopping list
 */
public class AddShoppingListDialog extends DialogFragment {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.etListTitle)
    EditText etListTitle;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_shopping_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDone)
    public void onDoneClicked() {
        String title = etListTitle.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Uri photoUrl = user.getPhotoUrl();

        String img = "https://firebasestorage.googleapis.com/v0/b/fir-database-3e08b.appspot.com/o/user-default.png?alt=media&token=aff7e73f-2034-4191-8d86-0b357d68d62f";
        if (photoUrl != null){
            img = photoUrl.toString();
        }

        ShoppingList list = new ShoppingList(title, user.getUid(), new Date().toString(), img);

        FirebaseDatabase.getInstance().getReference("ShoppingLists").
                child(user.getUid()).
                push().
                setValue(list);
        dismiss();

    }
}
