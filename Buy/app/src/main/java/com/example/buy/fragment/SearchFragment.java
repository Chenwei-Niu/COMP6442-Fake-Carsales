package com.example.buy.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.User;
import com.example.buy.parser.Parser;
import com.example.buy.parser.SearchTokenizer;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.KeyBoardUtils;
import com.example.buy.utils.ToastUtils;
import com.example.buy.utils.Utils;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Chenwei Niu
 */
public class SearchFragment extends Fragment implements View.OnClickListener, ListenerFragment{

    private EditText searchBar;
    private Parser parser;
    private SearchTokenizer tokenizer;
    private User user;
    private ArrayList<Car> searchResultList = new ArrayList<>();
    private ArrayList<CarView> carViewArrayList = new ArrayList<>();
    private ListView listView;
    private FragmentTransaction fragmentTransaction;
    private Spinner priceSpinner = null;
    private String prePriceSpinnerContent = "Default";
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();

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
        user = sqLiteDAO.getUser();
        priceSpinner = view.findViewById(R.id.price_spinner); // get the price spinner

        // set on listeners
        view.findViewById(R.id.search_button).setOnClickListener(this);

        // create the instance of the CarViewAdapter and pass the carArray into it
        CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()), carViewArrayList,this);

        // get the instance of the listView in this activity, and set the Adapter for listview
        listView.setAdapter(carViewAdapter);
        for (CarView carView: carViewArrayList){
            if(carView.getCar().favoriteUsers.contains(user) && user.getFavoriteCars().contains(carView.getCar())){
                carView.setLikeImage(R.drawable.red_heart);
            } else {
                carView.setLikeImage(R.drawable.black_hollow_heart);
            }
        }


        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String content = adapterView.getItemAtPosition(position).toString();

                if (content.equals("Low To High") && !prePriceSpinnerContent.equals("Low To High") && !carViewArrayList.isEmpty()){
                    ToastUtils.showShortToast(getContext(),"Your are sorting search result\n by "+ content);
                    carViewArrayList.sort(new Comparator<CarView>() {
                        @Override
                        public int compare(CarView carView1, CarView carView2) {
                            if(carView1.getCarPrice()<carView2.getCarPrice()){
                                return -1;
                            } else if (carView1.getCarPrice()>carView2.getCarPrice()){
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    prePriceSpinnerContent = content;
                    refreshFragment();
                } else if (content.equals("High To Low") && !prePriceSpinnerContent.equals("High To Low") && !carViewArrayList.isEmpty()){
                    ToastUtils.showShortToast(getContext(),"Your are sorting search result\n by "+ content);
                    carViewArrayList.sort(new Comparator<CarView>() {
                        @Override
                        public int compare(CarView carView1, CarView carView2) {
                            if(carView1.getCarPrice()<carView2.getCarPrice()){
                                return 1;
                            } else if (carView1.getCarPrice()>carView2.getCarPrice()){
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    prePriceSpinnerContent = content;
                    refreshFragment();
                } else {
                    priceSpinner.setSelection(0);
                    return;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
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
                            carViewArrayList.add(carView);
                        }

                        refreshFragment();
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


    /**
     * refresh the current fragment
     * @author Chenwei Niu
     */
    public void refreshFragment(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT>=26){
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.detach(this).attach(this).commit();

    }

    /**
     * Action when the heart icon is clicked
     * @param position
     * @author Chenwei Niu
     */
    @Override
    public void dealWithEvent(int position) {
        Car car = carViewArrayList.get(position).getCar();
        if(!car.favoriteUsers.contains(user) && !user.getFavoriteCars().contains(car)){
            // check whether this car is uploaded by the current user
            // User are only allowed to follow cars uploaded by others
            System.out.println(user.getOnSaleCars());
            if (user.getOnSaleCars().contains(car)){
                ToastUtils.showShortToast(getContext(),"You are not allow to follow your on sale car");
                return;
            }
            car.favoriteUsers.add(user);
            carViewArrayList.get(position).setLikeImage(R.drawable.red_heart);
            user.getFavoriteCars().add(car);
        } else {
            car.favoriteUsers.remove(user);
            carViewArrayList.get(position).setLikeImage(R.drawable.black_hollow_heart);
            user.getFavoriteCars().remove(car);
        }


        // Jump to the message conversation page
        refreshFragment();
    }
}