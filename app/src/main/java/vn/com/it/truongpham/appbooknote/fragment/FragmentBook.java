package vn.com.it.truongpham.appbooknote.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.angmarch.views.NiceSpinner;
import java.util.ArrayList;
import java.util.List;
import vn.com.it.truongpham.appbooknote.AddBookActivity;
import vn.com.it.truongpham.appbooknote.ApplicationBookNote;
import vn.com.it.truongpham.appbooknote.R;
import vn.com.it.truongpham.appbooknote.data.TypeBook;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBook extends Fragment {


    public FragmentBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        final int id=getArguments().getInt("id" ,1);

        NiceSpinner niceSpinner =  view.findViewById(R.id.nice_spinner);
        List<String> dataset = new ArrayList<>();

        List<TypeBook> listTypeBook=ApplicationBookNote.db.typeBookDAO().getListTypeBook();
        for(TypeBook name : listTypeBook){
             dataset.add(name.name);
        }
        niceSpinner.attachDataSource(dataset);
        return view;
    }



}
