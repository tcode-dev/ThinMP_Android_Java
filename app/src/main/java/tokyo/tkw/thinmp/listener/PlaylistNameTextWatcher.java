package tokyo.tkw.thinmp.listener;

import android.text.Editable;
import android.text.TextWatcher;

public class PlaylistNameTextWatcher implements TextWatcher {
    private StringBuffer playlistName;

    public PlaylistNameTextWatcher(StringBuffer playlistName) {
        this.playlistName = playlistName;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        playlistName.delete(0, playlistName.length());
        playlistName.append(s.toString());
    }
}
