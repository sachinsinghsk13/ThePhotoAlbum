package com.techjs.thephotoalbum.utils;

public enum SQLQueriesConstants {
	CREATE_USER_TABLE,
	CREATE_ALBUM_TABLE,
	CREATE_PHOTO_TABLE,
	DROP_MOBILE_UPLOAD_ALBUM_TRIGGER,
	CREATE_MOBILE_UPLOAD_ALBUM_TRIGGER,
	GET_ALL_USERS,
	GET_USER_BY_ID,
	INSERT_NEW_USER,
	UPDATE_USER,
	DELETE_USER,
	/**
	 * ?(1) = Email of User
	 * ?(2) = Password of user
	 * */
	AUTHENTICATE_USER
}
