package ru.catcherry.teammobile.reviews;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("review_id")
    int review_id;

    @SerializedName("room_id")
    int room_id;

    @SerializedName("comment")
    String comment;

}

//{
//        "review_id": 31,
//        "author_id": 993,
//        "room_id": 926,
//        "rating": 0,
//        "comment": "Нет кнопки \"вывести прибыль\". Исправьте, пожалуйста",
//        "createdAt": "2020-10-31T21:19:15.703Z",
//        "updatedAt": "2020-10-31T21:19:15.703Z",
//        "user": {
//        "user_id": 993,
//        "login": "axotellix",
//        "name": "Андрей",
//        "password": "$2b$10$9lpJxdrKKlbMcrLp7dd09u4AHdIEf1b/kFlo4tQLzySq8IxrzQfWK",
//        "admin": false,
//        "createdAt": "2020-10-31T21:12:06.089Z",
//        "updatedAt": "2020-10-31T21:12:06.089Z"
//        }
//        },
