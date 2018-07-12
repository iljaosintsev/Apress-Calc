package com.turlir.abakcalc;

import android.content.Context;

import com.turlir.converter.Visual;

import java.util.List;

public interface MainView {

    void showResult(String digit);

    void showError(String error);

    Context context();

    void setRepresentation(List<Visual> v);

    void setNotation(List<Visual> v);

    void resetToEmpty();
}
