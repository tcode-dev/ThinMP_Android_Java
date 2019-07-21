package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;

import java.util.Objects;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistAddAdapter;
import tokyo.tkw.thinmp.dto.PlaylistsDto;
import tokyo.tkw.thinmp.logic.PlaylistsLogic;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.music.MusicFactory;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;

public class PlaylistDialogFragment extends DialogFragment {
    private AlertDialog dialog;
    private Music music;
    private View addPlaylistView;
    private View createPlaylistView;
    private EditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_playlist_dialog, null);
        builder.setView(view);

        dialog = builder.create();
        addPlaylistView = view.findViewById(R.id.addPlaylist);
        createPlaylistView = view.findViewById(R.id.createPlaylist);
        editText = view.findViewById(R.id.editText);
        Button okButtonView = view.findViewById(R.id.okButton);
        Button cancelButtonView = view.findViewById(R.id.cancelButton);

        Bundle bundle = getArguments();
        String id = Objects.requireNonNull(bundle).getString(Music.ID);
        int type = Objects.requireNonNull(bundle).getInt(Music.TYPE);
        Optional<Music> musicOptional = MusicFactory.createInstance(getContext(), type, id);

        musicOptional.ifPresentOrElse(music -> {
            this.music = music;
        }, () -> {
            dialog.cancel();
        });

        String defaultPlaylistName = music.getName();
        editText.setText(defaultPlaylistName);

        setViewPlaylist(view);

        addPlaylistView.setOnClickListener(createAddPlaylistListener());
        okButtonView.setOnClickListener(createOkListener());
        cancelButtonView.setOnClickListener(createCancelListener());

        return dialog;
    }

    private void showAddPlaylist() {
        addPlaylistView.setVisibility(View.VISIBLE);
        createPlaylistView.setVisibility(View.GONE);
    }

    private void showCreatePlaylist() {
        addPlaylistView.setVisibility(View.GONE);
        createPlaylistView.setVisibility(View.VISIBLE);
    }

    /**
     * プレイリスト一覧を表示
     */
    private void setViewPlaylist(View view) {
        Activity context = getActivity();

        // view
        RecyclerView playlistView = view.findViewById(R.id.playlist);

        // logic
        PlaylistsLogic logic = PlaylistsLogic.createInstance(view.getContext());

        // dto
        PlaylistsDto dto = logic.createDto();

        // adapter
        PlaylistAddAdapter adapter = new PlaylistAddAdapter(
                dto.playlists,
                createPlaylistClickListener()
        );
        playlistView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(context);
        playlistView.setLayoutManager(layout);
    }

    private void addPlaylist() {
        PlaylistRegister playlistRegister = PlaylistRegister.createInstance();
        playlistRegister.create(editText.getText().toString(), music);
    }

    private PlaylistAddAdapter.PlaylistClickListener createPlaylistClickListener() {
        return (View view, String playlistId) -> {
            PlaylistRegister playlistRegister = PlaylistRegister.createInstance();
            playlistRegister.add(playlistId, music);
            dialog.dismiss();
        };
    }

    private View.OnClickListener createAddPlaylistListener() {
        return view -> showCreatePlaylist();
    }

    private View.OnClickListener createOkListener() {
        return view -> {
            addPlaylist();
            dialog.dismiss();
        };
    }

    private View.OnClickListener createCancelListener() {
        return view -> showAddPlaylist();
    }
}
