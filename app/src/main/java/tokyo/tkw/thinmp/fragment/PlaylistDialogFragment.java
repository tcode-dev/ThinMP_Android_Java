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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;

import java.util.Objects;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistDialogDto;
import tokyo.tkw.thinmp.epoxy.controller.PlaylistDialogController;
import tokyo.tkw.thinmp.logic.PlaylistDialogLogic;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.music.MusicFactory;
import tokyo.tkw.thinmp.register.add.PlaylistAdder;

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
        Button cancelButtonView2 = view.findViewById(R.id.cancelButton2);

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
        cancelButtonView2.setOnClickListener(createCancel2Listener());

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
        Activity activity = getActivity();

        // view
        RecyclerView playlistView = view.findViewById(R.id.playlist);

        // logic
        PlaylistDialogLogic logic = PlaylistDialogLogic.createInstance(getContext(), music, dialog);

        // dto
        PlaylistDialogDto dto = logic.createDto();

        // controller
        PlaylistDialogController controller = new PlaylistDialogController();
        controller.setData(dto);
        playlistView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(activity);
        playlistView.setLayoutManager(layout);
    }

    private void addPlaylist() {
        PlaylistAdder playlistAdder = PlaylistAdder.createInstance();
        playlistAdder.create(editText.getText().toString(), music);
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
        return view -> dialog.dismiss();
    }

    private View.OnClickListener createCancel2Listener() {
        return view -> showAddPlaylist();
    }
}
