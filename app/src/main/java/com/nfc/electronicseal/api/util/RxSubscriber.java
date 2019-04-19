package com.nfc.electronicseal.api.util;

import com.nfc.electronicseal.dialog.DialogHelper;

import rx.Subscriber;

public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        DialogHelper.stopProgressDlg();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        _onError(e.toString());

    }

    @Override
    public void onNext(T t) {

        _onNext(t);
    }

  public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
