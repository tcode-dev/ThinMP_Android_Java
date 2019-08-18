package tokyo.tkw.thinmp.listener;

import android.view.View;
import android.widget.AdapterView;

import tokyo.tkw.thinmp.dto.MainEditDto;

public class RecentlyAddedCountSelectedListener implements AdapterView.OnItemSelectedListener {
    private MainEditDto dto;

    public RecentlyAddedCountSelectedListener(MainEditDto dto) {
        this.dto = dto;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dto.recentlyAddedCount = Integer.parseInt(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
