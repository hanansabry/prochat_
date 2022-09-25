package com.egrobots.prochat.presentation.viewmodels;

import com.egrobots.prochat.data.DatabaseRepository;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.PhoneAuthCredential;

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
    private MediatorLiveData<Boolean> resetPasswordSentState = new MediatorLiveData<>();
    private MediatorLiveData<String> errorState = new MediatorLiveData<>();

    @Inject
    public AuthenticationViewModel(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public void signInWithEmail(String emailOrPhone, String password) {
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

    public void signInWithPhone(String phone, String password) {
        SingleObserver<Boolean> singleObserver = databaseRepository.signInWithPhone(phone, password)
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
                        errorState.setValue("Wrong phone/password");
                    }
                });
    }

    public void signUpWithEmail(String emailOrPhone, String userName, String password) {
        SingleObserver<Boolean> singleObserver = databaseRepository.signUpWithEmail(emailOrPhone, userName, password)
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

    public void signUpWithPhone(PhoneAuthCredential credential, String phone, String phoneVerificationId, String username, String password) {
        SingleObserver<Boolean> singleObserver = databaseRepository.signUpWithPhone(credential, phone, phoneVerificationId, username, password)
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

    public void sendPasswordResetEmail(String email) {
        SingleObserver<Boolean> singleObserver = databaseRepository.sendPasswordResetEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean state) {
                        resetPasswordSentState.setValue(state);
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

    public MediatorLiveData<Boolean> observeResetPasswordSentState() {
        return resetPasswordSentState;
    }

    public MediatorLiveData<String> observeErrorState() {
        return errorState;
    }
}
