package com.nitish.demos.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nitish.demos.multilanguagedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitish Sharma on 25 Dec, 2019
 */
public class LanguagesDialog {
    ArrayList<LanguageModel> items;
    Activity context;
    String dTitle, closeTitle = "Close";
    OnLanguageClick onGradientClick;
    AlertDialog alertDialog;
    int pos;
    int style;
    boolean cancellable = false;
    boolean showKeyboard = false;
    boolean useContainsFilter = false;
    int titleColor,searchIconColor,searchTextColor,itemColor,itemDividerColor,closeColor;


    private RecyclerView recyclerView;
    private LanguageAdapter mAdapter;

    private ArrayList<LanguageModel> modelList = new ArrayList<>();

    private void initColor(Context context){
        this.titleColor=context.getResources().getColor(R.color.blue_black_light);
        this.searchIconColor=context.getResources().getColor(R.color.blue_black_light);
        this.searchTextColor=context.getResources().getColor(R.color.blue_black_light);
        this.itemColor=context.getResources().getColor(R.color.blue_black_light);
        this.closeColor=context.getResources().getColor(R.color.blue_black_light);
        this.itemDividerColor=context.getResources().getColor(R.color.light_gray);
    }

    public LanguagesDialog(Activity activity, ArrayList<LanguageModel> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        initColor(context);
    }

    public LanguagesDialog(Activity activity, ArrayList<LanguageModel> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle = closeTitle;
        initColor(context);
    }

    public LanguagesDialog(Activity activity, ArrayList<LanguageModel> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        initColor(context);
    }

    public LanguagesDialog(Activity activity, ArrayList<LanguageModel> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle = closeTitle;
        initColor(context);
    }

    public void bindOnSpinerListener(OnLanguageClick onGradientClick1) {
        this.onGradientClick = onGradientClick1;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.language_layout, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        ImageView searchIcon=(ImageView) v.findViewById(R.id.searchIcon);
        rippleViewClose.setText(closeTitle);

        recyclerView = v.findViewById(R.id.recycler_view);
        title.setText(dTitle);

        setAdapter();

        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        if (isShowKeyboard()) {
            showKeyboard(searchBox);
        }

        title.setTextColor(titleColor);
        searchBox.setTextColor(searchTextColor);
        rippleViewClose.setTextColor(closeColor);
        searchIcon.setColorFilter(searchIconColor);


        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSpinerDialog();
            }
        });
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();
    }

    void filter(String text){
        List<LanguageModel> temp = new ArrayList<>();
        for(LanguageModel d: modelList){
            if(d.getName().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        mAdapter.updateList((ArrayList<LanguageModel>)temp);
    }

    public void closeSpinerDialog() {
        hideKeyboard();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    private void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext, 0);
                               }
                           }
                , 200);
    }

    private boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    private boolean isShowKeyboard() {
        return showKeyboard;
    }

    private boolean isUseContainsFilter() {
        return useContainsFilter;
    }


    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    public void setUseContainsFilter(boolean useContainsFilter) {
        this.useContainsFilter = useContainsFilter;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setSearchIconColor(int searchIconColor) {
        this.searchIconColor = searchIconColor;
    }

    public void setSearchTextColor(int searchTextColor) {
        this.searchTextColor = searchTextColor;
    }

    public void setItemColor(int itemColor) {
        this.itemColor = itemColor;
    }

    public void setCloseColor(int closeColor) {
        this.closeColor = closeColor;
    }

    public void setItemDividerColor(int itemDividerColor) {
        this.itemDividerColor = itemDividerColor;
    }

    private void setAdapter() {
        List<LanguageModel> list =   AppUtils.getListOfLanguageCode(context);
        for (int i = 0;i<list.size();i++){
            modelList.add(list.get(i));
        }
        mAdapter = new LanguageAdapter(context, modelList);
        recyclerView.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new LanguageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, LanguageModel model) {
                Toast.makeText(context, "Hey " + model.getName(), Toast.LENGTH_SHORT).show();
                onGradientClick.onClick(model, position);
                closeSpinerDialog();

            }
        });

    }
}
