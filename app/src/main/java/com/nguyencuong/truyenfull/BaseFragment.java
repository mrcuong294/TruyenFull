package com.nguyencuong.truyenfull;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nguyencuong.truyenfull.ui.dialog.DialogLoading;
import com.nguyencuong.truyenfull.util.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 *
 * Created by Mr Cuong on 2/18/2017.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected P mPresenter;

    private Unbinder unbinder;

    protected DialogLoading mDialogLoading;

    protected boolean isLoadingBackPressExit = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getFragmentLayout(), container, false);
        unbinder = ButterKnife.bind(this, v);

        initPresenter();
        LogUtils.d("BaseFragment", "onCreateView");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.onResume();
    }

    @Override
    public void onPause() {
        if (mPresenter != null) mPresenter.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        if (mPresenter != null) mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mDialogLoading != null) {
            mDialogLoading.dismiss();
            mDialogLoading = null;
        }
        unbinder.unbind();
        super.onDestroyView();
    }

    protected abstract int getFragmentLayout();


    protected abstract void initPresenter();

    protected void showToast(String mes) {
        Toast.makeText(getActivity().getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int idRes) {
        Toast.makeText(getActivity().getApplicationContext(), getString(idRes), Toast.LENGTH_SHORT).show();
    }

    protected void showToastError(@StringRes int messageId) {
        showToastError(getString(messageId));
    }

    protected void showToastError(String mes) {
        Toasty.error(getActivity().getApplicationContext(), mes + "", Toast.LENGTH_LONG).show();
    }

    protected void showToastSuccess(@StringRes int messageId) {
        showToastSuccess(getString(messageId));
    }

    protected void showToastSuccess(String mes) {
        Toasty.success(getActivity().getApplicationContext(), mes + "", Toast.LENGTH_SHORT).show();
    }

    protected void showDialogLoading(boolean show) {
        if (getActivity().isFinishing()) {
            return;
        }

        if (mDialogLoading == null) {
            mDialogLoading = new DialogLoading(getActivity());
            mDialogLoading.setListener(new DialogLoading.Listener() {
                @Override
                public void onBackPressed() {
                    if (isLoadingBackPressExit) {
                        getActivity().finish();
                    }
                }
            });
        }
        mDialogLoading.show(show);
    }
}
