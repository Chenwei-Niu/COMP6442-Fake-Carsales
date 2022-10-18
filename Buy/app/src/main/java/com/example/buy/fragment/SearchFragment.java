package com.example.buy.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.parser.Parser;
import com.example.buy.parser.SearchTokenizer;
import com.example.buy.sqlite.DAOService;
import com.example.buy.utils.KeyBoardUtils;
import com.example.buy.utils.ToastUtils;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Objects;


public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private EditText searchBar;
    private Parser parser;
    private SearchTokenizer tokenizer;
    private User user;
    private ArrayList<Car> searchResultList = new ArrayList<>();
    ArrayList<CarView> carViewArrayList = new ArrayList<>();
    private ListView listView;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.search_listView);
        searchBar = view.findViewById(R.id.search_bar);
        user = DAOService.getInstance().getUser();
        view.findViewById(R.id.search_button).setOnClickListener(this);
        listView.setOnItemClickListener(this);
        // create the instance of the CarViewAdapter and pass the carArray into it
        CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()), carViewArrayList);

        // get the instance of the listView in this activity, and set the Adapter for listview
        listView.setAdapter(carViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Car car = carViewAdapter.getItem(position).getCar();
                if(!car.favoriteUsers.contains(user)){
                    car.favoriteUsers.add(user);
                    carViewArrayList.get(position).setLikeImage(R.drawable.red_heart);
                } else {
                    car.favoriteUsers.remove(user);
                    carViewArrayList.get(position).setLikeImage(R.drawable.black_hollow_heart);
                }


                // Jump to the message conversation page
                refreshFragment(view);
            }
        });
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
                            if ( searchResultList.get(i).favoriteUsers.contains(user)) {
                                carView.setLikeImage(R.drawable.red_heart);
                            } else {
                                carView.setLikeImage(R.drawable.black_hollow_heart);
                            }
                            carViewArrayList.add(carView);
                        }

                        // create the instance of the CarViewAdapter and pass the carArray into it
                        CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()), carViewArrayList);

                        // get the instance of the listView in this activity, and set the Adapter for listview
                        listView.setAdapter(carViewAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Car car = carViewAdapter.getItem(position).getCar();
                                if(!car.favoriteUsers.contains(user)){
                                    car.favoriteUsers.add(user);
                                    carViewArrayList.get(position).setLikeImage(R.drawable.red_heart);
                                } else {
                                    car.favoriteUsers.remove(user);
                                    carViewArrayList.get(position).setLikeImage(R.drawable.black_hollow_heart);
                                }


                                // Jump to the message conversation page
                                refreshFragment(view);
                            }
                        });
                        listView.requestFocus();
                        KeyBoardUtils.hideKeyBoard(Objects.requireNonNull(getActivity()));
                    } else {
                        listView.requestFocus();
                        KeyBoardUtils.hideKeyBoard(Objects.requireNonNull(getActivity()));
                        ToastUtils.showShortToast(getContext(), "Sorry! \nThere is no cars satisfying your search");
                    }

                } catch (SearchTokenizer.IllegalTokenException e){
                    KeyBoardUtils.hideKeyBoard(Objects.requireNonNull(getActivity()));
                    System.out.println(e.getMessage());
                    ToastUtils.showShortToast(getContext(), "The invalid token '"+e.getMessage()+"' typed, \n Please follow the grammar");
                }
                break;
            case R.id.search_listView:
                listView.requestFocus();
                KeyBoardUtils.hideKeyBoard(Objects.requireNonNull(getActivity()));
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        KeyBoardUtils.hideKeyBoard(Objects.requireNonNull(getActivity()));
        Car car =  ( (CarView) listView.getItemAtPosition(position)).getCar();

    }
    public void refreshFragment(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT>=26){
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.detach(this).attach(this).commit();

    }
}