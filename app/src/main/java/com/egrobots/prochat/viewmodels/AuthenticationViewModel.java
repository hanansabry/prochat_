package com.egrobots.prochat.viewmodels;

import com.egrobots.prochat.data.DatabaseRepository;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import javax.inject.Inject;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationViewModel extends ViewModel {

    private DatabaseRepository databaseRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MediatorLiveData<Boolean> authenticateState = new MediatorLiveData<>();
    private MediatorLiveData<String> errorState = new MediatorLiveData<>();

    @Inject
    public AuthenticationViewModel(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public void signIn(String emailOrPhone, String password) {
        SingleObserver<Boolean> singleObserver = databaseRepository.signIn(emailOrPhone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean state) {
                        authenticateState.setValue(state);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof FirebaseAuthInvalidUserException || e instanceof FirebaseAuthInvalidCredentialsException) {
                            errorState.setValue("Wrong email/password");
                        } else {
                            errorState.setValue("Unknown error");
                        }
                    }
                });
    }

    public void signUp(String emailOrPhone, String userName, String password, boolean signUpWithMail) {
        SingleObserver<Boolean> singleObserver = databaseRepository.signUp(emailOrPhone, userName, password, signUpWithMail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean state) {
                        authenticateState.setValue(state);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MediatorLiveData<Boolean> observeAuthenticateState() {
        return authenticateState;
    }

    public MediatorLiveData<String> observeErrorState() {
        return errorState;
    }
}
