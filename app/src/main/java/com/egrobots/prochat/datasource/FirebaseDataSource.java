package com.egrobots.prochat.datasource;

import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.Group;
import com.egrobots.prochat.model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;

public class FirebaseDataSource {

    private final FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseDataSource(FirebaseAuth firebaseAuth, FirebaseDatabase firebaseDatabase) {
        this.firebaseAuth = firebaseAuth;
        this.firebaseDatabase = firebaseDatabase;
    }

    public Single<Boolean> signInWithEmail(String emailOrPhone, String password) {
        return Single.create(emitter -> {
            firebaseAuth.signInWithEmailAndPassword(emailOrPhone, password)
                    .addOnSuccessListener(authResult -> emitter.onSuccess(true))
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<Boolean> signInWithPhone(String phone, String password) {
        return Single.create(emitter -> {
            firebaseDatabase.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        HashMap<String, String> user = (HashMap<String, String>) userSnapshot.getValue();
                        String userPhone = user.get("phone");
                        String userPassword = user.get("password");
                        if (!userPhone.isEmpty() && userPhone.equals(phone)
                                && !password.isEmpty() && userPassword.equals(password)) {
                            emitter.onSuccess(true);
                            return;
                        }
                    }
                    emitter.onSuccess(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    public Single<Boolean> signUpWithEmail(String email, String userName, String password) {
        return Single.create(emitter -> {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        //send verification mail to the user
                        FirebaseUser firebaseUser = authResult.getUser();
                        firebaseUser.sendEmailVerification().addOnFailureListener(emitter::onError);
                        saveUserToDatabase(email, "", "", userName, password, emitter);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<Boolean> signUpWithPhone(PhoneAuthCredential credential, String phone, String phoneVerificationId, String username, String password) {
        return Single.create(emitter -> {
            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            emitter.onSuccess(true);
                            //save user to database
                            saveUserToDatabase("", phone, phoneVerificationId, username, password, emitter);
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<Boolean> sendPasswordResetEmail(String email) {
        return Single.create(emitter -> {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(aVoid -> emitter.onSuccess(true))
                    .addOnFailureListener(emitter::onError);
        });
    }

    private void saveUserToDatabase(String email, String phone, String phoneVerificationId, String username, String password, SingleEmitter<Boolean> emitter) {
        HashMap<String, Object> userValues = new HashMap<>();
        userValues.put("email", email);
        userValues.put("phone", phone);
        userValues.put("phone_verification_id", phoneVerificationId);
        userValues.put("username", username);
        userValues.put("password", password);
        firebaseDatabase.getReference("users").push().updateChildren(userValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onSuccess(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
    }


    //user methods
    public Single<Boolean> isUserHasGroups(String userId) {
        return Single.create(emitter -> {
            emitter.onSuccess(true);
        });
    }

    public Flowable<Group> getUserGroups(String userId) {
        return Flowable.create(emitter -> {
            List<Group> groupList = new ArrayList<>();
            Group g0 = new Group();
            g0.setGroupId("0");
            g0.setGroupName("All");
            Group g1 = new Group();
            g1.setGroupId("1");
            g1.setGroupName("General Group");
            Group g2 = new Group();
            g2.setGroupId("2");
            g2.setGroupName("Company Clients");
            Group g3 = new Group();
            g3.setGroupId("3");
            g3.setGroupName("Group Name");
            Group g4 = new Group();
            g4.setGroupId("4");
            g4.setGroupName("Need A Freelancing Project?");
            Group g5 = new Group();
            g5.setGroupId("5");
            g5.setGroupName("Ask Me");
            groupList.add(g0);
            groupList.add(g1);
            groupList.add(g2);
            groupList.add(g3);
            groupList.add(g4);
            groupList.add(g5);

            for (Group group : groupList) {
                emitter.onNext(group);
            }
            emitter.onComplete();

        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<Chat> getGroupChats(String groupId) {
        return Flowable.create(emitter -> {
            List<Chat> groupChats = new ArrayList<>();
            Chat c1 = new Chat();
            List<Message> chatMessages = new ArrayList<>();
            Message m1 = new Message("hi", 1663984832928L, false, true);
            Message m2 = new Message("hi, how are you?", Calendar.getInstance().getTimeInMillis(), false, true);
            Message m3 = new Message("I'm Fine", Calendar.getInstance().getTimeInMillis(), true, true);
            Message m4 = new Message("I want to ask you about something, Please?", Calendar.getInstance().getTimeInMillis(), true, false);
            Message m5 = new Message("yes sure!!", Calendar.getInstance().getTimeInMillis(), false, false);
            Message m6 = new Message("yes sure Again!!", Calendar.getInstance().getTimeInMillis(), true, false);

            chatMessages.add(m1);
            chatMessages.add(m2);
            chatMessages.add(m3);
            chatMessages.add(m5);
            chatMessages.add(m6);
            chatMessages.add(m4);
            chatMessages.add(m4);
            chatMessages.add(m4);
            chatMessages.add(m4);
            c1.setMessages(chatMessages);

            if (groupId.equals("0")) {
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
                groupChats.add(c1);
            } else if (groupId.equals("1")) {
                groupChats.add(c1);
            } else if (groupId.equals("2")) {
                groupChats.add(c1);
            } else if (groupId.equals("3")) {
                groupChats.add(c1);
            } else if (groupId.equals("4")) {
                groupChats.add(c1);
            } else if (groupId.equals("5")) {
                groupChats.add(c1);
            }
            for (Chat chat : groupChats) {
                emitter.onNext(chat);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

}
