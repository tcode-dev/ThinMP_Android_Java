package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteListAdapter;
import tokyo.tkw.thinmp.favorite.Favorite;
import tokyo.tkw.thinmp.favorite.FavoriteList;
import tokyo.tkw.thinmp.music.Track;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteListFragment newInstance(String param1, String param2) {
        FavoriteListFragment fragment = new FavoriteListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite_list, container, false);

        Activity context = getActivity();

        RecyclerView favoriteListView = view.findViewById(R.id.favoriteList);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        favoriteListView.setLayoutManager(layout);

        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Favorite> favoriteList = realm.where(Favorite.class).findAll().sort("createdAt");
        ArrayList<Track> trackList = FavoriteList.getTrackList(favoriteList);
        FavoriteListAdapter adapter = new FavoriteListAdapter(context, favoriteList, trackList);
        favoriteListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        favoriteListView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
