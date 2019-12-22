package com.techjs.thephotoalbum.utils;

public enum SQLQueriesConstants {
	CREATE_USER_TABLE,
	CREATE_ALBUM_TABLE,
	CREATE_PHOTO_TABLE,
	DROP_MOBILE_UPLOAD_ALBUM_TRIGGER,
	CREATE_MOBILE_UPLOAD_ALBUM_TRIGGER,
	GET_ALL_USERS,
	GET_USER_BY_ID,
	GET_USER_BY_EMAIL,
	INSERT_NEW_USER,
	UPDATE_USER,
	DELETE_USER,
	AUTHENTICATE_USER,
	GET_ALL_ALBUM_OF_USER,
	GET_ALL_ALBUM_OF_USER_LIMIT,
	GET_TOTAL_NO_OF_ALBUMS_OF_USER,
	GET_ALBUM_OF_USER,
	UPDATE_ALBUM,
	INSERT_ALBUM,
	DELETE_ALBUM,
	GET_ALL_PHOTOS_OF_ALBUM,
	GET_ALL_PHOTOS_OF_ALBUM_IN_LIMIT,
	GET_TOTAL_NO_OF_PHOTOS_IN_ALBUM,
	GET_PHOTO,
	INSERT_PHOTO,
	UPDATE_PHOTO,
	DELETE_PHOTO,
	GET_ALBUM_THUMB,
	GET_RECENT_PHOTOS,
	GET_TOTAL_NO_OF_PHOTOS_OF_USER,
	GET_FAVOURITE_PHOTOS,
	GET_TOTAL_NO_OF_FAVOURITE_PHOTOS,
	MARK_PHOTO_AS_FAVOURITE
}
