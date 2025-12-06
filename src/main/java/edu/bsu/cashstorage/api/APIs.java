package edu.bsu.cashstorage.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class APIs {

    public static final String API_V1 = Params.API + Params.SLASH + Params.V1;
    // Config URLs
    public static final String API_V1_CURRENCIES = API_V1 + Params.SLASH + Params.CONFIGURATION + "/currencies";
    public static final String API_V1_BANKS = API_V1 + Params.SLASH + Params.CONFIGURATION + "/banks";
    public static final String API_V1_COLORS = API_V1 + Params.SLASH + Params.CONFIGURATION + "/colors";
    public static final String API_V1_ICONS = API_V1 + Params.SLASH + Params.CONFIGURATION + "/icons";
    // Entity URLs
    public static final String API_V1_AUTH = API_V1 + "/auth";
    public static final String API_V1_USERS = API_V1 + "/users";
    public static final String API_V1_ACCOUNTS = API_V1 + "/accounts";
    public static final String API_V1_OPERATIONS = API_V1 + "/operations";
    public static final String API_V1_CATEGORIES = API_V1 + "/categories";
    public static final String API_V1_PLACES = API_V1 + "/places";
    // Auth endpoints
    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_REGISTER = "/register";
    // User endpoints
    public static final String USERS_PROFILE = "/profile";

    // Parameter constants
    public static final class Params {
        public static final String SEARCH = "search";
        public static final String USER_ID = "userId";
        public static final String CONFIGURATION = "configuration";
        public static final String V1 = "v1";
        public static final String API = "api";
        public static final String ID = "id";
        public static final String ID_VARIABLE = "{" + ID + "}";
        private static final String SLASH = "/";
    }

    public static final class Paths {
        public static final String ID_PATH = Params.SLASH + Params.ID_VARIABLE;
        public static final String SEARCH_PATH = Params.SLASH + Params.SEARCH;
    }
}