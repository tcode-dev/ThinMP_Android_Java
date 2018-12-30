package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistAdapter;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;

public class PlaylistDialogFragment extends DialogFragment {
    private AlertDialog mDialog;
    private View mAddPlaylist;
    private View mCreatePlaylist;
    private Button mOkButton;
    private Button mCancelButton;
    private EditText mEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_playlist_dialog, null);
        builder.setView(view);

        mDialog = builder.create();
        mAddPlaylist = view.findViewById(R.id.addPlaylist);
        mCreatePlaylist = view.findViewById(R.id.createPlaylist);
        mEditText = view.findViewById(R.id.editText);
        mOkButton = view.findViewById(R.id.okButton);
        mCancelButton = view.findViewById(R.id.cancelButton);

        // データ取り出し
        Bundle bundle = getArguments();
        String defaultPlaylistName = bundle.getString("defaultPlaylistName");
        String trackId = bundle.getString("trackId");

        mEditText.setText(defaultPlaylistName);

        setViewPlaylist(view, trackId);

        mAddPlaylist.setOnClickListener(addPlaylistListener());

        mOkButton.setOnClickListener(okListener(trackId));

        mCancelButton.setOnClickListener(cancelListener());

        return mDialog;
    }

    private void showAddPlaylist() {
        mAddPlaylist.setVisibility(View.VISIBLE);
        mCreatePlaylist.setVisibility(View.GONE);
    }

    private void showCreatePlaylist() {
        mAddPlaylist.setVisibility(View.GONE);
        mCreatePlaylist.setVisibility(View.VISIBLE);
    }

    /**
     * プレイリスト一覧を表示
     *
     * @param view
     * @param trackId
     */
    private void setViewPlaylist(View view, String trackId) {
        Activity context = getActivity();
        Runnable callback = () -> {
            mDialog.dismiss();
        };

        RecyclerView playlistView = view.findViewById(R.id.playlist);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        playlistView.setLayoutManager(layout);

        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Playlist> playlists = realm.where(Playlist.class).findAll().sort("order");
        PlaylistAdapter adapter = new PlaylistAdapter(context, playlists, trackId, callback);
        playlistView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        playlistView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * プレイリスト登録
     *
     * @param trackId
     */
    private void addPlaylist(String trackId) {
        Track track = MusicList.getTrack(trackId);
        PlaylistRegister playlistRegister = new PlaylistRegister();
        playlistRegister.create(mEditText.getText().toString(), track);
    }

    /**
     * プレイリスト追加クリック時のイベント
     *
     * @return
     */
    private View.OnClickListener addPlaylistListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreatePlaylist();
            }
        };
    }

    /**
     * OKボタンクリック時のイベント
     *
     * @param trackId
     * @return
     */
    private View.OnClickListener okListener(String trackId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlaylist(trackId);
                mDialog.dismiss();
            }
        };
    }

    /**
     * キャンセルボタンクリック時のイベント
     *
     * @return
     */
    private View.OnClickListener cancelListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPlaylist();
            }
        };
    }
}
