package com.android.tp.commongps.core;

import android.util.Log;

import com.android.tp.commongps.activity.MainActivity;
import com.android.tp.commongps.models.Friend;
import com.android.tp.commongps.models.User;
import com.vk.sdk.VKCallback;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class VkLoginCallback<VKAccessToken> implements VKCallback<VKAccessToken> {
    @Override
    public void onResult(VKAccessToken res) {
        VKRequest request1 = VKApi.users().get();
        VKRequest request2 = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name,online,last_seen"));
        VKBatchRequest batch = new VKBatchRequest(request1, request2);

        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                try {
                    // users.get - get user info
                    JSONArray respondedUsers = responses[0].json.getJSONArray("response");
                    JSONObject respondedUser = respondedUsers.getJSONObject(0);

                    Integer id = respondedUser.getInt("id");
                    String name = respondedUser.getString("first_name") + " "
                            + respondedUser.getString("last_name");

                    // friends.get - get user friends
                    JSONObject respondedFriends = responses[1].json.getJSONObject("response");
                    JSONArray friends = respondedFriends.getJSONArray("items");

                    ArrayList<Integer> friendsIdList = new ArrayList<>();
                    ArrayList<Friend> friendsList = new ArrayList<>();

                    for (int i = 0; i < friends.length(); i++) {
                        friendsIdList.add(friends.getJSONObject(i).getInt("id"));

//                        Log.i("VK_API", String.valueOf(friends.getJSONObject(i).getJSONObject("last_seen")));

                        long unixDate = -1;
                        try {
                            unixDate = friends.getJSONObject(i).getJSONObject("last_seen").getLong("time");

                            friendsList.add(new Friend(
                                            friends.getJSONObject(i).getInt("id"),
                                            friends.getJSONObject(i).getString("first_name"),
                                            friends.getJSONObject(i).getString("last_name"),
                                            friends.getJSONObject(i).getInt("online"),
                                            new Date(unixDate)
                                    )
                            );
                        }
                        catch(JSONException ex) {
                            friendsList.add(new Friend(
                                            friends.getJSONObject(i).getInt("id"),
                                            friends.getJSONObject(i).getString("first_name"),
                                            friends.getJSONObject(i).getString("last_name"),
                                            friends.getJSONObject(i).getInt("online")
                                    )
                            );
                        }
                    }

                    CoreApplication.setClient(new User(id, name, friendsIdList));
                    CoreApplication.setFriendsList(friendsList);

                    // не будем заранее класть данные клиента без координат на сервер
                    //FirebaseManager.getInstance().initUser(MainActivity.client);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onError(VKError error) {
        // TODO вывести ошибку, выйти из приложения
        Log.w("VK_ERROR", error.toString());
    }
}

