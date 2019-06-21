package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistAddAdapter;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistDialogFragment extends DialogFragment {
    private AlertDialog mDialog;
    private Track mTrack;
    private View mAddPlaylist;
    private View mCreatePlaylist;
    private Button mOkButton;
    private Button mCancelButton;
    private EditText mEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_playlist_dialog,
                null);
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
        mTrack = (Track) bundle.getSerializable("track");

        mEditText.setText(defaultPlaylistName);

        setViewPlaylist(view, mTrack.getId());

        mAddPlaylist.setOnClickListener(addPlaylistListener());

        mOkButton.setOnClickListener(okListener(mTrack));

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

        Realm realm = Realm.getDefaultInstance();
        RealmResults<PlaylistRealm> playlists = realm.where(PlaylistRealm.class).findAll().sort(
                PlaylistRealm.ORDER);
        PlaylistAddAdapter adapter = new PlaylistAddAdapter(playlists, mTrack, callback);
        playlistView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        playlistView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * プレイリスト登録
     *
     * @param track
     */
    private void addPlaylist(Track track) {
        PlaylistRegister playlistRegister = new PlaylistRegister();
        playlistRegister.create(mEditText.getText().toString(), track);
    }

    /**
     * プレイリスト追加クリック時のイベント
     *
     * @return
     */
    private View.OnClickListener addPlaylistListener() {
        return v -> showCreatePlaylist();
    }

    /**
     * OKボタンクリック時のイベント
     *
     * @param track
     * @return
     */
    private View.OnClickListener okListener(Track track) {
        return v -> {
            addPlaylist(track);
            mDialog.dismiss();
        };
    }

    /**
     * キャンセルボタンクリック時のイベント
     *
     * @return
     */
    private View.OnClickListener cancelListener() {
        return v -> showAddPlaylist();
    }
}
