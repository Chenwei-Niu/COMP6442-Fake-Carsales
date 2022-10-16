package com.example.buy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.User;
import com.example.buy.parser.Parser;
import com.example.buy.parser.SearchTokenizer;
import com.example.buy.utils.KeyBoardUtils;
import com.example.buy.utils.ToastUtils;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;

public class SearchActivity extends MyBaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private EditText searchBar;
    private Parser parser;
    private SearchTokenizer tokenizer;
    private User user;
    private ArrayList<Car> searchResultList = new ArrayList<>();
    ArrayList<CarView> carViewArrayList = new ArrayList<>();
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.search_listView);
        searchBar = findViewById(R.id.search_bar);
        user = (User) getIntent().getExtras().getSerializable("user");
        findViewById(R.id.search_button).setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_button:
                carViewArrayList.clear();
                String searchContent = searchBar.getText().toString();
                try{
                    tokenizer = new SearchTokenizer(searchContent);
                    parser = new Parser(tokenizer);
                    parser.parseExp();
                    searchResultList = parser.executeQuery(); // get the search result array


                    if (searchResultList.size()!=0) {

                        for (int i = 0; i < searchResultList.size(); i++) {
                            CarView carView = new CarView( searchResultList.get(i));
                            carViewArrayList.add(carView);
                        }

                        // create the instance of the CarViewAdapter and pass the carArray into it
                        CarViewAdapter carViewAdapter = new CarViewAdapter(this, carViewArrayList);

                        // get the instance of the listView in this activity, and set the Adapter for listview
                        listView.setAdapter(carViewAdapter);
                        listView.requestFocus();
                        KeyBoardUtils.hideKeyBoard(SearchActivity.this);
                    } else {
                        listView.requestFocus();
                        KeyBoardUtils.hideKeyBoard(SearchActivity.this);
                        ToastUtils.showShortToast(SearchActivity.this, "Sorry! \nThere is no cars satisfying your search");
                    }

                } catch (SearchTokenizer.IllegalTokenException e){
                    ToastUtils.showShortToast(SearchActivity.this, "The invalid token '"+e.getMessage()+"' typed, \n Please follow the grammar");
                }
                break;
            case R.id.search_listView:
                listView.requestFocus();
                KeyBoardUtils.hideKeyBoard(SearchActivity.this);
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        KeyBoardUtils.hideKeyBoard(SearchActivity.this);
        Object listItem = listView.getItemAtPosition(position);
    }
}
