package com.endymion.common.util;

import android.util.Log;

import com.endymion.common.presenter.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava2使用封装
 * Created by Jim on 2018/07/27.
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public abstract static class SimpleObserver<T> implements Observer<T>, Disposable {
        private BasePresenter mPresenter;
        private boolean showLoadingDialog = true;
        private Disposable disposable;
        private boolean showErrorTip = true;

        public SimpleObserver(BasePresenter presenter) {
            this.mPresenter = presenter;
        }

        public SimpleObserver(BasePresenter presenter, boolean showLoadingDialog) {
            this.mPresenter = presenter;
            this.showLoadingDialog = showLoadingDialog;
        }

        /**
         * 隐藏网络异常提示
         */
        public SimpleObserver<T> hideErrorTip() {
            this.showErrorTip = false;
            return this;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.disposable = disposable;
            if (mPresenter != null) {
                mPresenter.addDisposable(this);
                if (showLoadingDialog && mPresenter.getViewBridge() != null) {
                    mPresenter.getViewBridge().showLoading();
                }
            }
        }

        @Override
        public void onComplete() {
            if (mPresenter != null) {
                mPresenter.removeDisposable(this);
                if (showLoadingDialog && mPresenter.getViewBridge() != null) {
                    mPresenter.getViewBridge().hideLoading();
                }
            } else {
                disposable.dispose();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (mPresenter != null) {
                mPresenter.removeDisposable(this);
                if (showLoadingDialog && mPresenter.getViewBridge() != null) {
                    mPresenter.getViewBridge().hideLoading();
                }
            } else {
                disposable.dispose();
            }

            String errorMsg = e.getMessage();
            onErrorMsg(errorMsg);
        }

        protected void onErrorMsg(String msg) {
            if (showErrorTip && mPresenter != null && mPresenter.getViewBridge() != null) {
                mPresenter.getViewBridge().showToast(msg);
            }
            Log.e("RxUtil", "onErrorMsg, msg = " + msg);
        }

        @Override
        public void dispose() {
            disposable.dispose();
        }

        @Override
        public boolean isDisposed() {
            return disposable.isDisposed();
        }

        @Override
        public void onNext(T t) {
            onResult(t);
        }

        abstract public void onResult(T t);
    }
}
