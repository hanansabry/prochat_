package com.egrobots.prochat.presentation.viewmodels;

import com.egrobots.prochat.data.DatabaseRepository;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.Group;

import javax.inject.Inject;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserProfileViewModel extends ViewModel {

    private DatabaseRepository databaseRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MediatorLiveData<Boolean> hasGroups = new MediatorLiveData<>();
    private MediatorLiveData<Group> groupsLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> finishRetrievingGroups = new MediatorLiveData<>();

    private MediatorLiveData<Chat> groupChatsLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> finishRetrievingGroupChats = new MediatorLiveData<>();

    private MediatorLiveData<String> errorState = new MediatorLiveData<>();

    @Inject
    public UserProfileViewModel(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public void isUserHasGroups(String userId) {
        SingleObserver<Boolean> singleObserver = databaseRepository.isUserHasGroups(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean state) {
                        hasGroups.setValue(state);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void getUserGroups(String userId) {
        databaseRepository.getUserGroups(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<Group>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Group group) {
                        groupsLiveData.setValue(group);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        finishRetrievingGroups.setValue(true);
                    }
                });
    }

    public void getGroupChats(String groupId) {
        databaseRepository.getGroupChats(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<Chat>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Chat chat) {
                        groupChatsLiveData.setValue(chat);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        finishRetrievingGroupChats.setValue(true);
                    }
                });
    }

    public MediatorLiveData<Boolean> observeUserHasGroups() {
        return hasGroups;
    }

    public MediatorLiveData<Group> observeGroups() {
        return groupsLiveData;
    }

    public MediatorLiveData<Boolean> isGroupRetrievingFinished() {
        return finishRetrievingGroups;
    }

    public MediatorLiveData<Chat> observeGroupChats() {
        return groupChatsLiveData;
    }

    public MediatorLiveData<Boolean> isGroupChatsRetrievingFinished() {
        return finishRetrievingGroupChats;
    }

    public MediatorLiveData<String> observeError() {
        return errorState;
    }
}
