package com.android.settingslib.suggestions;

import android.content.Context;
import android.service.settings.suggestions.Suggestion;
import com.android.settingslib.utils.AsyncLoader;
import java.util.List;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/settingslib/suggestions/SuggestionLoader.class */
public class SuggestionLoader extends AsyncLoader<List<Suggestion>> {
    public SuggestionLoader(Context context, SuggestionController suggestionController) {
        super(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // android.content.AsyncTaskLoader
    public List<Suggestion> loadInBackground() {
        throw null;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.settingslib.utils.AsyncLoader
    public void onDiscardResult(List<Suggestion> list) {
    }
}