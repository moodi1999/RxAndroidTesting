package com.aloni.marketers.data.service.csObserver;

import android.app.Activity;

import com.aloni.marketers.R;
import com.aloni.marketers.act.actBase;
import com.aloni.marketers.core.eHandler.csException;
import com.aloni.marketers.core.eHandler.errorHandlingCntrl;
import com.aloni.marketers.utils.buttonPlus;
import com.crashlytics.android.Crashlytics;
import com.jpp.dialog.ICDialogTripleEvents;
import com.jpp.dialog.cDialog;
import com.jpp.dialog.cDialogMessage;

import rx.Observable;
import rx.Observer;

public abstract class aObserver<T> implements Observer<T> {

    Observable<T> _observable = null ;
    Activity _act = null;
    buttonPlus _btnSubmit;
    public aObserver(){

    }
    public aObserver(Observable<T> _observable , Activity _act){
        this._observable = _observable;
        this._act = _act;
    }

    public aObserver(Activity _act){
        this._act = _act;
    }
    @Override
    public void onCompleted() {
        if(_btnSubmit != null)
            _btnSubmit.setLoading(false);
    }

    @Override
    public void onError(Throwable e) {
        try {
            Crashlytics.logException(e);
            stopLoadingDlg();
            String _msg  = errorHandlingCntrl.handleError(e);
            if (errorHandlingCntrl.errorCode(e) == 401) {
                try {
                    //Hawk.deleteAll();
/*                    Intent intent = new Intent(RayInvApplication.get_instance().getApplicationContext(), newBuyDocActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    RayInvApplication.get_instance().getApplicationContext().startActivity(intent);*/
                    showMessageAndCompleted(_msg);
                } catch (Exception ignored) {
                }
            } else {
                if (e != null && e.getMessage() != null && e.getMessage().contains("Failed to connect")) {
                    String _disErrorMsg = "خطایی در ارتباط با سرور رخ داده است،  پس از بررسی اتصال اینترنت مجددا تلاش کنید";
                    if(!showReTryDlg(e,_disErrorMsg)) {
                        showMessageAndCompleted(_disErrorMsg);
                        onDisconnect();
                    }
                } else if(!showReTryDlg(e,_msg)) {
                    showMessageAndCompleted(_msg);
                }
            }
        }catch (Exception err){
            onErrorMsg (err == null || err.getMessage() == null ? "can't access to message" : err.getMessage() ) ;
            onCompleted();
        }
    }

    private void showMessageAndCompleted(String _msg){
        onErrorMsg(_msg);
        onCompleted();
    }

    private void showMessageAndCompleted(Throwable e){
        onErrorMsg(errorHandlingCntrl.handleError(e));
        onCompleted();
    }
    public void reTry(){
        if(_observable != null)
            _observable.subscribe(this);
        else
            onError(new csException("امکان ارسال مجدد درخواست وجود ندارد"));
    }

    public void onErrorMsg(String msg){
        try{
            if(_act != null && _act instanceof actBase && _observable == null)
                ((actBase) _act).showError(msg);
        }catch (Exception ignored){}
    }

    public void onDisconnect(){}

    @Override
    public void onNext(T t) {
        dismissDlg();
    }


    private boolean _dontShowReTryDlg = false;
    cDialog _dialog = null;
    public boolean showReTryDlg(final Throwable e , final String errorMsg) {
        if (_dontShowReTryDlg) {
            _dontShowReTryDlg = false;
            return false;
        }
        if (_act == null || _observable == null) return false;
        try {
            if(_dialog == null)
                _dialog = new cDialog(_act);
            _dialog.tripleAction(new cDialogMessage("با عرض پوزش عملیات ناموفق بود", errorMsg, "تلاش مجدد", "تماس با پشتیبانی", "انصراف", R.drawable.oops), false, R.color.accent,
                    new ICDialogTripleEvents() {
                        @Override
                        public void positive() {
                            _dialog.setLoading(true);
                            reTry();
                        }

                        @Override
                        public void negative() {
                            _dialog.dismiss();
                            //Cheetap.openCallCenterIntent(_act);
                        }

                        @Override
                        public void neutral() {
                            _dialog.dismiss();
                            _dontShowReTryDlg = true;
                            onError(new csException(errorMsg));
                        }
                    });

        }catch (Exception ignored){
            return false;
        }
        return true;
    }

    public void dismissDlg(){
        if(_dialog != null)
            _dialog.dismiss();
    }
    public void stopLoadingDlg(){
        if(_dialog != null)
            _dialog.setLoading(false);
    }

    public aObserver<T> setBtnSubmit(buttonPlus _btnSubmit) {
        this._btnSubmit = _btnSubmit;
        _btnSubmit.setLoading(true);
        return this;
    }
}